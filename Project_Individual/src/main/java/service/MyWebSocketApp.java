package service;

import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import service.controller.ManagementController;
import service.model.Management;

import javax.ws.rs.core.Context;
import java.util.*;

public class MyWebSocketApp extends WebSocketApplication {


    private static final Set<WebSocket> sockets = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Integer, WebSocket> sockets2 = Collections.synchronizedMap(new HashMap<>());
    private Object uid;
    private WebSocket tempSocket;
    @Context
    Request request;
    @Override
    public void onConnect(WebSocket socket) {
        sockets.add(socket);
        System.out.println("onConnect");
        tempSocket = socket;
        super.onConnect(socket);
        System.out.println("Scokets");
        System.out.println(sockets.size());
        System.out.println(socket.hashCode());
    }

    @Override
    public void onMessage(WebSocket current, String text) {
        ManagementController persistenceController = new ManagementController();
//        this is to get the id of every socket, for the first time when a user will connect using any socket,
//        a message will be sent with user id to store in hashmap, below substring is being used to get the id of the user

        String sId = text.substring(1, 3);
        String sDisconnect = null;
        if(text.length()>12) {
            sDisconnect  = text.substring(1, 11);
        }
        // if the id is found, then using that id a new object will be stored in the maps list,
        if (sId.equals("id")) {
            // getting the id from the string
            int id = Integer.parseInt(text.substring(3, text.length() - 1));
//            tempSocket is created in connect method for temporarily storing the new Socket, that will be used here along with id store in the map
            sockets2.put(id, tempSocket);

        }
        else if(sDisconnect.equals("disconnect")){
            int id = Integer.parseInt(text.substring(11, text.length() - 1));
            sockets.remove(id);
        } else {

            try {
//                to convert the string to a json object,
                JSONObject json = new JSONObject((String) new JSONParser().parse(text));
//              to get the id and patient from the json object
                Object patientId = json.get("patientId");
                Object content = json.get("content");
//                looping through all the sockets
                    synchronized (sockets2) {
//                        once the id matches any socket, a notification will be sent it to that specif socket, however if a socket is not in the list then the user wont recieve any
//                        notification, because only logged in users are supposed to recieve the notification
                        if (sockets2.containsKey(patientId)) {
//                            now the notification will be only sent to the user whose id is in the sockets2 list, and below is the patientId which we get from json object which a
//                            pharmacist send
                            WebSocket main = sockets2.get(patientId);
                            main.isConnected();
                            main.send(text);
                        }
                    }
                    persistenceController.setNotification((int) patientId, (String) content);
        } catch(ParseException e){
            e.printStackTrace();
        }
    }

    }

    @Override
    public void onMessage(WebSocket socket, byte[] bytes) {
        socket.send(bytes);
    }

    @Override
    public void onClose(WebSocket socket, DataFrame dataFrame) {

        sockets.remove(socket);
    }
}
