/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.jsf;

import com.acme.acmepools.entity.Customer;
import com.acme.acmepools.session.CustomerFacade;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

@Named(value = "jsonController")
@RequestScoped
public class JsonController {

    @EJB
    CustomerFacade customerFacade;
    private String customerJson;
    
    @PostConstruct
    public void init(){
        buildCustomers();
    }

    public void buildCustomers() {
        List<Customer> customers = customerFacade.findAll();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        StringBuilder json = new StringBuilder();
        try (StringWriter sw = new StringWriter();) {
            for (Customer customer : customers) {
                System.out.println("customer" + customer.getName());
                builder.add("customer", Json.createObjectBuilder()
                        .add("customerId", customer.getCustomerId())
                        .add("email", customer.getEmail()));

            }
            JsonObject result = builder.build();

            try (JsonWriter writer = Json.createWriter(sw)) {
                writer.writeObject(result);
            }
            json.append(sw.toString());
            setCustomerJson(json.toString());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public JsonObject buildCustomersJson() {
        List<Customer> customers = customerFacade.findAll();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject result = null;
        StringBuilder json = new StringBuilder();
        try (StringWriter sw = new StringWriter();) {
            for (Customer customer : customers) {
                System.out.println("customer" + customer.getName());
                builder.add("customer", Json.createObjectBuilder()
                        .add("customerId", customer.getCustomerId())
                        .add("email", customer.getEmail()));

            }
            result = builder.build();

            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public void writeJson() {
        try {
            JsonObject jsonObject = buildCustomersJson();

            javax.json.JsonWriter jsonWriter = Json.createWriter(new FileWriter("Customers.json"));
            
            jsonWriter.writeObject(jsonObject);
            jsonWriter.close();


            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "JSON Built",
                "JSON Built"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public String buildAndReturnCustomers() {
        List<Customer> customers = customerFacade.findAll();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        StringBuilder json = new StringBuilder();
        JsonObjectBuilder object = null;
        for (Customer customer : customers) {
            object = builder.add("customerId", customer.getCustomerId())
                    .add("name", customer.getName())
                    .add("email", customer.getEmail());
        }
        
        builder.add("customer", object);
        JsonObject result = builder.build();
        StringWriter sw = new StringWriter();
        try (JsonWriter writer = Json.createWriter(sw)) {
            writer.writeObject(result);
        }
        json.append(sw.toString());
        return json.toString();
    }

    public String readObject() {
        InputStream in = new ByteArrayInputStream(buildAndReturnCustomers().getBytes());
         // or
        //Reader fileReader = new InputStreamReader(getClass().getResourceAsStream("CustomerObject.json"));
        //JsonReader reader = Json.createReader(fileReader);
        JsonReader reader = Json.createReader(in);
        JsonObject obj = reader.readObject();
        return obj.toString();
              
    }
    
    /**
     * @return the customerJson
     */
    public String getCustomerJson() {
        return customerJson;
    }

    /**
     * @param authorJson the customerJson to set
     */
    public void setCustomerJson(String customerJson) {
        this.customerJson = customerJson;
    }
}
