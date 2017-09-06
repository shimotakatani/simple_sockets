package server.server;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Сокет для сервера
 * Created by Никита on 28.08.2017.
 */
public class Server {
    public static void main(String[] ar)    {
        int port = 8000; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            ExecutorService tp = Executors.newFixedThreadPool(2);

            Semaphore semaphore = new Semaphore(2);
            while(true) {

                try {
                    semaphore.acquire();
                Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером

                tp.execute(new ServerForOne(socket));

                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }
        } catch(IOException x) { x.printStackTrace(); }
    }


}