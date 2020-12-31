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


        String sId = text.substring(1, 3);
        String sDisconnect = null;
        if(text.length()>12) {
            sDisconnect  = text.substring(1, 11);
        }
        if (sId.equals("id")) {
            int id = Integer.parseInt(text.substring(3, text.length() - 1));
            sockets2.put(id, tempSocket);

        }
        else if(sDisconnect.equals("disconnect")){
            int id = Integer.parseInt(text.substring(11, text.length() - 1));
            sockets.remove(id);
        } else {

            try {
                JSONObject json = new JSONObject((String) new JSONParser().parse(text));

                Object patientId = json.get("patientId");
                Object content = json.get("content");
//                Object medicineId = json.get("medicineId");
//                Object startDate = json.get("startDate");
//                Object endDate = json.get("endDate");
//                Management medicineData = new Management((int)patientId, (int)medicineId, (String) startDate, (String) endDate);
//                System.out.println(patientId);
//                boolean isAdded = persistenceController.addMedicineToPatient(medicineData);
//
//                if(isAdded) {
                    synchronized (sockets2) {
                        if (sockets2.containsKey(patientId)) {
                            WebSocket main = sockets2.get(patientId);
                            main.isConnected();
                            main.send(text);
                        }
                    }
                    persistenceController.setNotification((int) patientId, (String) content);
//                }



        } catch(ParseException e){
            e.printStackTrace();
        }
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

    @Override
    public void onMessage(WebSocket socket, byte[] bytes) {
        socket.send(bytes);
    }

    @Override
    public void onClose(WebSocket socket, DataFrame dataFrame) {

        sockets.remove(socket);
    }
}
