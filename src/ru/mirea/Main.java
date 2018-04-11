package ru.mirea;
import java.util.concurrent.TimeUnit;

class ClientThread implements Runnable{

    synchronized void WaitService(){
        try {
            WeatherService.sync.wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

            if (!Main.ws.output.isEmpty()) {
                System.out.println(Main.ws.output.remove().weather);
            }
            else
                WaitService();
        }
    }
}

public class Main {
    static WeatherService ws;

        public static void main(String[] args) {
        ws = new WeatherService();

        ClientThread ct = new ClientThread();
        (new Thread(ct)).start();

        while (true) {
            int j = (int) Math.round(Math.random()*10);
            System.out.println("Adding " + Integer.toString(j) + " tasks");
            for (int i = 0; i<j; i++){
                ws.AddTask(i,"Moscow");
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
