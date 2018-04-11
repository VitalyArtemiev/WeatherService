package ru.mirea;

import java.util.concurrent.ConcurrentLinkedQueue;

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

    public static Object sync;

    int taskID;
    public synchronized void Notifyclient() {
        notifyAll();
        System.out.println("notifying");
    }

    class ServiceThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (!input.isEmpty()) {
                    WeatherTask wt = input.remove();
                    WeatherResult wr = new WeatherResult();
                    //System.out.println("wtf");
                    wr.ID = wt.ID;
                    wr.weather = wt.city + " weather " + Integer.toString(wr.ID);
                    output.add(wr);
                    Notifyclient();
                }
            }
        }
    }

    public WeatherService() {
        input = new ConcurrentLinkedQueue<>();
        output = new ConcurrentLinkedQueue<>();
        //System.out.println("ws");
        ServiceThread st = new ServiceThread();
        (new Thread(st)).start();
    }

    public void AddTask(float t, String c) {
        //System.out.println("add");
        input.add(new WeatherTask(t, c, taskID));
        taskID++;
    }
}
