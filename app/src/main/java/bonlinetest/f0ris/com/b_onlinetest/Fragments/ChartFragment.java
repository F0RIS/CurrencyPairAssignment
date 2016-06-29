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
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import bonlinetest.f0ris.com.b_onlinetest.AppController;
import bonlinetest.f0ris.com.b_onlinetest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private static final float STROKE_WIDTH = 3;
    private XYPlot plot;


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

    private XYPlot dynamicPlot;
    private MyPlotUpdater plotUpdater;
    SampleDynamicXYDatasource data;
    private Thread myThread;

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);


        dynamicPlot = (XYPlot) view.findViewById(R.id.dynamicXYPlot);
        plotUpdater = new MyPlotUpdater(dynamicPlot);

        // only display whole numbers in domain labels
        dynamicPlot.getGraphWidget().setDomainValueFormat(new DecimalFormat("0"));

        // getInstance and position datasets:
        data = new SampleDynamicXYDatasource();
        SampleDynamicSeries sine1Series = new SampleDynamicSeries(data, 0, "Bid");
        SampleDynamicSeries sine2Series = new SampleDynamicSeries(data, 1, "Offer");


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


        dynamicPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1000*60);//one minute

        dynamicPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.0001);

        dynamicPlot.setRangeValueFormat(new DecimalFormat("#.######"));

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

        //TODO
        dynamicPlot.setRangeBoundaries(0, 2, BoundaryMode.AUTO);

        return view;
    }

    @Override
    public void onResume() {
        // kick off the data generating thread:
        myThread = new Thread(data);
        myThread.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        data.stopThread();
        super.onPause();
    }


    class SampleDynamicXYDatasource implements Runnable {

        // encapsulates management of the observers watching this datasource for update events:
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

        //@Override
        public void run() {
            try {
                keepRunning = true;
                boolean isRising = true;
                while (keepRunning) {
                    Thread.sleep(10); // decrease or remove to speed up the refresh rate.
                    notifier.notifyObservers();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public int getItemCount(int series) {
            return AppController.datas.size();
        }

        public Number getX(int series, int index) {
            if (index >= 1) {
                throw new IllegalArgumentException();
            }
            return index;
        }

        public Number getY(int series, int index) {
            if (index >= AppController.datas.size()) {
                throw new IllegalArgumentException();
            }

            return AppController.datas.get(index).prices[series];
        }

        public void addObserver(Observer observer) {
            notifier.addObserver(observer);
        }

        public void removeObserver(Observer observer) {
            notifier.deleteObserver(observer);
        }

    }


    class SampleDynamicSeries implements XYSeries {
        private SampleDynamicXYDatasource datasource;
        private int seriesIndex;
        private String title;

        public SampleDynamicSeries(SampleDynamicXYDatasource datasource, int seriesIndex, String title) {
            this.datasource = datasource;
            this.seriesIndex = seriesIndex;
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public int size() {
            return AppController.datas.size();
        }

        @Override
        public Number getX(int index) {
//            if (AppController.active == null)
//                return 0;
            return AppController.datas.get(index).date.getTime();
        }

        @Override
        public Number getY(int index) {
//            if (AppController.active == null)
//                return 0;
            return AppController.datas.get(index).prices[seriesIndex];
        }
    }
}
