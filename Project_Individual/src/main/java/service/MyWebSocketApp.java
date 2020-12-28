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
    private static final Map<Integer, Set<WebSocket>> sockets2 = Collections.synchronizedMap(new HashMap<>());
    private Object uid;
    private WebSocket tempSocket;

    @Context
    Request request;


    @Override
    public void onConnect(WebSocket socket) {


        sockets.add(socket);

        System.out.println("onConnect");
        super.onConnect(socket);

        System.out.println("Scokets");
        System.out.println(sockets.size());
        System.out.println(socket.hashCode());

//        request.getSession(true);
//        request.setAttribute("id", 5);
////        uid = request.getAttribute("email");
//
//        System.out.println("this is from websocket");
//        System.out.println(request.getAttribute("id"));
//        sockets2.put(uid, socket);

    }
    public void AddOnConnect(WebSocket socket, int id){
        onConnect(socket);
        sockets2.put(id, sockets);
    }

    @Override
    public void onMessage(WebSocket current, String text) {
        ManagementController persistenceController = new ManagementController();

        try {
            JSONObject json = new JSONObject((String) new JSONParser().parse(text));

            Object patientId =  json.get("patientId");
            Object content = json.get("content");
            System.out.println(patientId);
            synchronized (sockets) {
                sockets.forEach(socket -> {
//                    WebSocket receiver = (WebSocket) sockets2.get(patientId);
                    if (socket.isConnected()) {
                        socket.send(text);
                    }
                });
            }

            persistenceController.setNotification((int)patientId, (String) content);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        JSONObject jsonObject = new JSONObject(text);
//        Object patientId =  jsonObject.get("patientId");
//        System.out.println("onMessage");
//        System.out.println("Patient Id below");
//        System.out.println(patientId);
////        JSONObject object = new JSONObject (text);
//        JSONArray keys = jsonObject.names ();

//        JSONParser parser = new JSONParser();
//        try {
////            JSONObject json = (JSONObject) parser.parse(text);
//            json.keys();
//            System.out.println(json.keys());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
    public void onMessage(WebSocket current, Management m) {
        System.out.println("onMessage");

        Set<WebSocket> receiver = sockets2.get("1");

        synchronized (sockets) {
            sockets.forEach(socket -> {
                if (socket.isConnected()) {
                    socket.send("You have a new medicine prescribed"+m.getMedicine().getMedName());

                }
            });
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
