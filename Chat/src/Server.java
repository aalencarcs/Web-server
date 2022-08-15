import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
        public static  LinkedList<Connections> queue ;

    public static void main(String[] args) {

        int port = 8080;
        queue = new LinkedList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            ExecutorService fixedPool = Executors.newFixedThreadPool(20);






            while(true) {
                Socket clientSocket = serverSocket.accept();
                Connections newClients = new Connections(clientSocket);
                queue.add(newClients);
                fixedPool.submit(new Connections(clientSocket));
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    public static class Connections implements Runnable {
        Socket socket;

        public Connections(Socket socket) {
            this.socket = socket;
        }

        BufferedReader in;
        PrintWriter out;


        public Socket getSocket() {
            return socket;
        }



        @Override
        public void run() {
            while(true){
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = in.readLine();


                    for(Connections t : queue){
                        if(t.getSocket().equals(this.socket)) {
                            continue;



                        }
                            out = new PrintWriter(t.getSocket().getOutputStream(), true);
                            out.println(message);

                    }






                } catch (IOException e) {
                    throw new RuntimeException(e);
                }










            }




        }
    }

}
