package bonlinetest.f0ris.com.b_onlinetest.chart;

import com.androidplot.xy.XYSeries;


public class DynamicSeries implements XYSeries {
    private ActiveDataSource dataSource;
    private int seriesIndex;
    private String title;

    public DynamicSeries(ActiveDataSource datasource, int seriesIndex, String title) {
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
        return dataSource.getItemCount(seriesIndex);
    }

    @Override
    public Number getX(int index) {
        return dataSource.getX(seriesIndex, index);
    }

    @Override
    public Number getY(int index) {
        return dataSource.getY(seriesIndex, index);
    }
}