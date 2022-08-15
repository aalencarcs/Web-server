import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        try {
            Socket clientSocket = new Socket(hostName, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ExecutorService fixedPool = Executors.newFixedThreadPool(2);
            fixedPool.submit(new ClientRunnable(clientSocket));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message = "";

            while (clientSocket.isConnected()) {
                message = in.readLine();
                out.println(message);
                out.flush();
            }





        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    public static class ClientRunnable implements Runnable{
    Socket socket ;

        public ClientRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (socket.isConnected()) {
                    System.out.println(breader.readLine());


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }



}
