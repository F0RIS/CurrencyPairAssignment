package bonlinetest.f0ris.com.b_onlinetest.chart;

import java.util.Observable;
import java.util.Observer;

import bonlinetest.f0ris.com.b_onlinetest.model.Active;


public class ActiveDataSource implements Runnable {

    private Active active;

    public ActiveDataSource(Active active) {
        this.active = active;
    }

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
                Thread.sleep(1000);
                active.getPositionsUpdates();
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

    public int getItemCount(int seriesIndex) {
        return active.positions.size();
    }

    public Number getX(int seriesIndex, int index) {
        return active.positions.get(index).date.getTime();
    }

    public Number getY(int seriesIndex, int index) {
        return active.positions.get(index).value;
    }
}