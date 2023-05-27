package data_handling;

import java.util.Date;

public class Timer {

    private long initTime;

    public void start() {
        initTime = System.nanoTime();
    }

    public long getElapsed() {
        return System.nanoTime() - initTime;
    }

}
