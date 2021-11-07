/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Juneau
 */
public class MessageEncoder implements Encoder.Text<Message> {

  @Override
  public String encode(Message message) throws EncodeException {

    JsonObject jsonObject = Json.createObjectBuilder()
        .add("username", message.getUsername())
        .add("message", message.getMessage()).build();
    return jsonObject.toString();

  }

  @Override
  public void init(EndpointConfig ec) {
    System.out.println("Initializing message encoder");
  }

    @Override
    public void destroy() {
        System.out.println("Destroying encoder...");
    }
    
}
