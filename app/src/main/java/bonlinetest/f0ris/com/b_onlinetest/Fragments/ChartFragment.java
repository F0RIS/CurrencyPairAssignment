package bonlinetest.f0ris.com.b_onlinetest.Fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import bonlinetest.f0ris.com.b_onlinetest.AppController;
import bonlinetest.f0ris.com.b_onlinetest.Models.Active;
import bonlinetest.f0ris.com.b_onlinetest.R;


public class ChartFragment extends Fragment {

    private static final float STROKE_WIDTH = 3;
    private XYPlot dynamicPlot;
    private MyPlotUpdater plotUpdater;
    DynamicDataSource data;
    private Thread myThread;


    private class MyPlotUpdater implements Observer {
        Plot plot;
        public MyPlotUpdater(Plot plot) {
            this.plot = plot;
        }
        @Override
        public void update(Observable o, Object arg) {
            plot.redraw();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        dynamicPlot = (XYPlot) view.findViewById(R.id.dynamicXYPlot);
        plotUpdater = new MyPlotUpdater(dynamicPlot);


        data = new DynamicDataSource();
        DynamicSeries sine1Series = new DynamicSeries(data, 0, "Bid");
        DynamicSeries sine2Series = new DynamicSeries(data, 1, "Offer");

        LineAndPointFormatter formatter1 = new LineAndPointFormatter(
                Color.rgb(200, 0, 0), null, null, null);
        formatter1.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
        formatter1.getLinePaint().setStrokeWidth(STROKE_WIDTH);
        dynamicPlot.addSeries(sine1Series,
                formatter1);

        LineAndPointFormatter formatter2 = new LineAndPointFormatter(
                Color.rgb(0, 0, 200), null, null, null);
        formatter2.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
        formatter2.getLinePaint().setStrokeWidth(STROKE_WIDTH);
        dynamicPlot.addSeries(sine2Series,
                formatter2);

        data.addObserver(plotUpdater);

        dynamicPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.0001);
        dynamicPlot.setRangeValueFormat(new DecimalFormat("#.######"));

        dynamicPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1000*60);//one minute
        //HH:mm format for X axis
        dynamicPlot.setDomainValueFormat(new Format() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                Number num = (Number) obj;

                toAppendTo.append(simpleDateFormat.format(num.longValue()));
                return toAppendTo;
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        dynamicPlot.setRangeBoundaries(0, 2, BoundaryMode.AUTO);
        return view;
    }

    @Override
    public void onResume() {
        // kick off the data getting thread:
        myThread = new Thread(data);
        myThread.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        data.stopThread();
        super.onPause();
    }

    class DynamicDataSource implements Runnable {
        // encapsulates management of the observers watching this dataSource for update events:
        class MyObservable extends Observable {
            @Override
            public void notifyObservers() {
                setChanged();
                super.notifyObservers();
            }
        }

        private MyObservable notifier;
        private boolean keepRunning = false;

        {
            notifier = new MyObservable();
        }

        public void stopThread() {
            keepRunning = false;
        }

        @Override
        public void run() {
            try {
                keepRunning = true;
                while (keepRunning) {
                    Thread.sleep(1000); // decrease or remove to speed up the refresh rate.
//                    Thread.sleep(1000);
                    notifier.notifyObservers();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void addObserver(Observer observer) {
            notifier.addObserver(observer);
        }

        public void removeObserver(Observer observer) {
            notifier.deleteObserver(observer);
        }
    }


    class DynamicSeries implements XYSeries {
        private DynamicDataSource dataSource;
        private ArrayList<Active> data = AppController.data;
        private int seriesIndex;
        private String title;

        public DynamicSeries(DynamicDataSource datasource, int seriesIndex, String title) {
            this.dataSource = datasource;
            this.seriesIndex = seriesIndex;
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public Number getX(int index) {
            return data.get(index).date.getTime();
        }

        @Override
        public Number getY(int index) {
            return data.get(index).prices[seriesIndex];
        }
    }
}
