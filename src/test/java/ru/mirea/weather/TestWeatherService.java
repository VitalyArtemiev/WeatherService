package ru.mirea.weather;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static ru.mirea.weather.Main.ws;

import org.junit.Test;

public class TestWeatherService {
    @Test
    public void testWeather(){
        WeatherService ws = new WeatherService();

        ws.AddTask(100, "Moscow");

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WeatherResult wr = ws.output.remove();
        assertEquals("Moscow weather 0", wr.weather);

        ws.AddTask(100, "Fryazino");

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wr = ws.output.remove();
        assertEquals("Fryazino weather 1", wr.weather);

        int numTasks = 64;
        for (int i=0; i < numTasks; i++) {
            ws.AddTask(i, "Whatever");
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(numTasks, ws.output.size());
        ws.output.clear();

        ws.active = false;

        ws.AddTask(0, "Nothing");
        boolean b = false;
        try {
            ws.output.remove();

        } catch (Exception e) {
            b = true;
        }

        assert(b);
    }
    @Test
    public void testQueue() {
        final ThreadSafeQueue<Integer> q = new ThreadSafeQueue<>();
        q.add(5);

        Runnable r = new Runnable() {
            boolean active;
            boolean start = false;
            @Override
            public void run() {
                while (!start) {

                }

                while (active) {
                    q.remove();
                }
            }
        }

        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();

         r.start = true;

        assertTrue("WS is running", ws.active);
    }
}
