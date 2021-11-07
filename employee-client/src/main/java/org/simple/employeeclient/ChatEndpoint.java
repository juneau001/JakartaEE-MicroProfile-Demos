/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Juneau
 */
@ServerEndpoint(value = "/chatEndpoint/{username}",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
public class ChatEndpoint {

    private static Session session;
    private static Set<Session> chatters = new CopyOnWriteArraySet<>();
    private static Map<String, String> users = new HashMap<String, String>();

    @OnOpen
    public void messageOpen(Session session,
            @PathParam("username") String username) throws IOException,
            EncodeException {
        this.session = session;
        users.put(session.getId(), username);
        chatters.add(session);
        Message message = new Message();
        message.setUsername(username);
        message.setMessage("Welcome " + username);
        broadcast(message);
    }

    @OnMessage
    public void messageReceiver(Session session,
            Message message) throws IOException, EncodeException {
        message.setUsername(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void close(Session session) {
        chatters.remove(session);
        Message message = new Message();
        message.setUsername(users.get(session.getId()));
        message.setMessage("Disconnected from server");

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("There has been an error with session " + session.getId());
    }

    private static void broadcast(Message message)
            throws IOException, EncodeException {
       
        chatters.forEach(session -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
