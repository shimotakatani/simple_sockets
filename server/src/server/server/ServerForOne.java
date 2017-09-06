package server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Никита on 06.09.2017.
 */
public class ServerForOne implements Runnable {

    private Socket socket;

    public ServerForOne(Socket socket){
        this.socket = socket;
    }

    public void run() {
        serverForOneClient(this.socket);
    }

    private static void serverForOneClient(final Socket socket){
        try {
            while (true) {
                System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
                System.out.println();

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);

                String line = null;

                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
                System.out.println();
            }
        } catch(Exception x) { System.out.println("Client turn off"); }

    }
}
