package bonlinetest.f0ris.com.b_onlinetest.Chart;

import java.util.Observable;
import java.util.Observer;

import bonlinetest.f0ris.com.b_onlinetest.AppController;


public class DynamicDataSource implements Runnable {

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

    public int getItemCount(int seriesIndex) {
        return AppController.data.size();
    }

    public Number getX(int seriesIndex, int index) {
        return AppController.data.get(index).date.getTime();
    }

    public Number getY(int seriesIndex, int index) {
        return AppController.data.get(index).prices[seriesIndex];
    }
}