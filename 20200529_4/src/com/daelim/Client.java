package com.daelim;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client{
    WebSocketClient ws;
    URI uri;
    MesssageHandler handler;

    public Client(String s, MesssageHandler mh){
        try {
            uri = new URI(s);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        handler = mh;
    }
    //서버에 연결하는 기능
    public void start(){
        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("onOpen");
            }

            @Override
            public void onMessage(String s) {
                try {
                    System.out.println("onMessage s :: "+s);
                    JSONObject msg = (JSONObject) (new JSONParser()).parse(s);
                    String str = getTime()+"["+msg.get("name")+"] "+msg.get("data");
                    handler.handlemessage(str);
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println(s);
                System.exit(0);
            }

            @Override
            public void onError(Exception e) {

            }
        };
        ws.connect();
    }
    // 서버로 메세지를 보내는 기능
    public void sendMSG(String str){
        ws.send(str);
    }
    public void sendMSG(JSONObject json){
        ws.send(json.toString());
    }
    // 서버에 연결을 끊는 기능
    public void end(){
        ws.close();
    }
    private String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return format.format(new Date());
    }

}
