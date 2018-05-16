package ru.mirea.weather;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.NoSuchElementException;

public class TestWeatherService {
    @Test
    public void testWeather(){
        WeatherService ws = new WeatherService();

        assertTrue("WS is running", ws.active);

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

        assertTrue(b);
    }

    class QTestThread implements Runnable {

        boolean active;
        ThreadSafeQueue q;
        public int count;

        public QTestThread(ThreadSafeQueue queue){
            q = queue;
            count = 0;
        }

        public void start() {
            active = true;
        }

        @Override
        public void run() {
            while (!active) {

            }

            while (active) {
                try {
                    q.remove();
                    count++;
                }
                catch (NoSuchElementException e) {
                    if (count < 10) {
                        assertTrue("Missing task", false);
                    }
                }
            }
        }
    }

    @Test
    public void testQueue() {
        final ThreadSafeQueue<Integer> q = new ThreadSafeQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        q.add(5);
        q.add(6);
        q.add(7);
        q.add(8);
        q.add(9);
        q.add(10);

        QTestThread r = new QTestThread(q);

        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();

        r.start();

        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue("Error retrieving tasks", r.count == 10);

        //
    }
}
