package ru.mirea.weather;
import java.util.concurrent.TimeUnit;


class InputThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            int j = (int) Math.round(Math.random()*10);
            System.out.println("Adding " + Integer.toString(j) + " tasks");
            for (int i = 0; i<j; i++){
                Main.ws.AddTask(i,"Moscow");
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    static WeatherService ws;

    public static void main(String[] args) {
        ws = new WeatherService();

        InputThread it1 = new InputThread();
        new Thread(it1).start();

        while (true) {
            if (!ws.output.isEmpty()) {
                System.out.println(ws.output.remove().weather);
            }
        }
    }
}
