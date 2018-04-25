package ru.mirea.weather;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
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
        assertEquals(wr.weather,"Moscow weather 0");

        ws.AddTask(100, "Fryazino");

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wr = ws.output.remove();
        assertEquals(wr.weather,"Fryazino weather 1");


        int numTasks = 64;
        for (int i=0; i < numTasks; i++) {
            ws.AddTask(i, "Whatever");
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(ws.output.size(), numTasks);

    }
}
