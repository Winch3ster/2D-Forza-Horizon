import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connectedClients;
    private ServerSocket server;
    private boolean serverIsActive;

    private ExecutorService threadPool;
    public Server(){
        serverIsActive = true;
        connectedClients = new ArrayList<>();
    }

    @Override
    public void run() {
        //main idea
        //Server will be constantly listening to incoming request to connect and open connection handler for each request
        //This server will only:
        //    1) Listen to incoming request
        //    2) Create a connection handler which will deal with further in and outgoing messages

        try {
            //Create a server that listen to connection
            server = new ServerSocket(9999);

            threadPool = Executors.newCachedThreadPool();
            //For now, only 2 clients can be connected to the server
            while(serverIsActive && connectedClients.size() < 3){

                //Responsible to accept client request
                Socket client = server.accept();

                ConnectionHandler newClient = new ConnectionHandler(client);
                connectedClients.add(newClient);

                threadPool.execute(newClient); //will automatically call .run() for this instance
                System.out.println(connectedClients.size());
            }


        } catch (IOException e) {
            shutdown();
        }


    }

    public void broadcast(VehicleDataObject message){
        for(ConnectionHandler client : connectedClients){
            if(client != null){
                client.sendMessageToClient(message);
            }
        }
    }


    public void shutdown(){
        if(!server.isClosed()) {
            try {
                serverIsActive = false;
                server.close();

                //close each individual connection
                for(ConnectionHandler client : connectedClients){
                    client.closeConnection();
                }
            } catch (IOException e) {
                //TODO: Ignore
            }
        }

    }

    class ConnectionHandler implements Runnable{
        private Socket client;
        private ObjectInputStream in; //get stream or message from socket
        private ObjectOutputStream out; //Write the stream or message into the socket to the client
        private String nickname; //Store the name for this instance
        public ConnectionHandler(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try{
                out =  new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

                //out.println("Hello client");   --> Send message to client
                //in.readLine();     --> get message from client

                VehicleDataObject receivedObj = (VehicleDataObject) in.readObject();

                VehicleDataObject response = new VehicleDataObject(receivedObj.getPlayerNumber(), 0,0, 0, true, false, false);

                broadcast(response);
                VehicleDataObject data;

                while((data = (VehicleDataObject) in.readObject()) != null){
                    broadcast(data);
                }
            }catch (IOException e){
                shutdown();
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException: " + e.getMessage());
            }
        }
        public void sendMessageToClient(VehicleDataObject message){
            try{
                out.writeObject(message);
                out.flush();
            }catch (Exception e){

            }
        }


        //public void sendMessageToClient(String message){
            //out.println(message);
        //}


        public void closeConnection(){
            if(!client.isClosed()){
                try {
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }



    }

    public static void main(String[] args) {
        Server s = new Server();
        s.run();
    }
}





