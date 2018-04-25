package ru.mirea.weather;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

class WeatherTask {
    float time;
    String city;
    int ID;

    WeatherTask(float t, String c, int tid) {
        time = t;
        city = c;
        ID = tid;
    }
}

class WeatherResult {
    int ID;
    String weather;
}

public class WeatherService {
    ConcurrentLinkedQueue<WeatherTask> input;
    ConcurrentLinkedQueue<WeatherResult> output;

    boolean active;
    int taskID;

    class ServiceThread implements Runnable {
        @Override
        public void run() {
            while (active) {
                WeatherTask wt;
                try {
                    wt = input.remove();
                }
                catch (NoSuchElementException e) {
                    continue;
                }

                WeatherResult wr = new WeatherResult();
                wr.ID = wt.ID;
                wr.weather = wt.city + " weather " + Integer.toString(wr.ID);

                //pretending to fetch weather
                try {
                    sleep(100);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                output.add(wr);
            }
        }
    }

    public WeatherService() {
        input = new ConcurrentLinkedQueue<>();
        output = new ConcurrentLinkedQueue<>();
        //System.out.println("ws");
        active = true;
        ServiceThread st = new ServiceThread();
        new Thread(st).start();
        new Thread(st).start();
        new Thread(st).start();
        new Thread(st).start();
    }

    public void AddTask(float t, String c) {
        //System.out.println("add");
        input.add(new WeatherTask(t, c, taskID));
        taskID++;
    }
}
