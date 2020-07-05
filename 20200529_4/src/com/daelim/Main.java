package com.daelim;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main{

    static String urlString = "ws://whitebanana2000.iptime.org:8877";
    static Client client;
    static String nick;
    static TextField tf_nick;
    static Panel chatPanel;
    static TextArea ta;
    static  Frame frame;

    public static void main(String[] args) {

        frame = new Frame();

        frame.setTitle("객체지향 프로그래밍");

        frame.setBounds(100, 100, 400, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Panel startPanel = new Panel();
        startPanel.setBounds(0, 0, 400, 400);
        startPanel.setBackground(Color.CYAN);
        frame.add(startPanel);

        tf_nick = new TextField();
        tf_nick.setBounds(100, 150, 200, 40);
        startPanel.add(tf_nick);

        Button loginbt = new Button("Login");
        loginbt.setBounds(100, 200, 200, 40);
        startPanel.add(loginbt);
        loginbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = tf_nick.getText();
                if(!s.equals("")){
                    nick = s;
                    frame.remove(startPanel);
                    client = new Client("ws://whitebanana2000.iptime.org:8877", new MesssageHandler() {
                        @Override
                        public void handlemessage(String message) {
                            ta.append(message+"\n");
                        }
                    });
                }
                client.start();
            }
        });

        chatPanel = new Panel();
        chatPanel.setBounds(0,0,400,400);
        chatPanel.setBackground(Color.PINK);
        frame.add(chatPanel);

        ta = new TextArea();
        ta.setBounds(50, 50, 300, 200);
        chatPanel.add(ta);

        TextField tf_msg = new TextField();
        tf_msg.setBounds(50,260,200,50);
        chatPanel.add(tf_msg);

        Button btn_send = new Button("SEND");
        btn_send.setBounds(260,260,90,50);
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tf_msg.getText().equals("")){
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", nick);
                    jsono.put("data", tf_msg.getText());
                    client.sendMSG(jsono.toString());
                }
                tf_msg.setText("");
                tf_msg.requestFocus();
            }
        });
        chatPanel.add(btn_send);
    }
}
