package bonlinetest.f0ris.com.b_onlinetest.fragment;

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
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import bonlinetest.f0ris.com.b_onlinetest.chart.ActiveDataSource;
import bonlinetest.f0ris.com.b_onlinetest.chart.DynamicSeries;
import bonlinetest.f0ris.com.b_onlinetest.model.Active;
import bonlinetest.f0ris.com.b_onlinetest.R;


public class ChartFragment extends Fragment {

    private XYPlot dynamicPlot;
    private MyPlotUpdater plotUpdater;
    private ActiveDataSource data;
    private static Active active = new Active("EUR/USD");

    private class MyPlotUpdater implements Observer {
        Plot plot;

        public MyPlotUpdater(Plot plot) {
            this.plot = plot;
        }

        @Override
        public void update(Observable o, Object arg) {
            //auto step calculating
            float range = dynamicPlot.getCalculatedMaxY().floatValue() - dynamicPlot.getCalculatedMinY().floatValue();
            dynamicPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, range / 10.0);
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
        data = new ActiveDataSource(active);
        data.addObserver(plotUpdater);

        DynamicSeries sine1Series = new DynamicSeries(data, 0, active.name);

        LineAndPointFormatter formatter1 = new LineAndPointFormatter(
                Color.rgb(0, 0, 200), null, null, null);
        formatter1.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
        formatter1.getLinePaint().setStrokeWidth(3);
        dynamicPlot.addSeries(sine1Series,
                formatter1);


        dynamicPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.0001);
        dynamicPlot.setRangeValueFormat(new DecimalFormat("#.######"));

        dynamicPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1000 * 60);//one minute interval

        //HH:mm format for X axis
        dynamicPlot.setDomainValueFormat(new Format() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                toAppendTo.append(simpleDateFormat.format(((Number) obj).longValue()));
                return toAppendTo;
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        dynamicPlot.setRangeBoundaries(0, 0, BoundaryMode.AUTO);
        return view;
    }

    @Override
    public void onResume() {
        // kick off the data getting thread:
        Thread myThread = new Thread(data);
        myThread.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        data.stopThread();
        super.onPause();
    }

}