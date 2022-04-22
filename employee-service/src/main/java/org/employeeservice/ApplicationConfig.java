/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.employeeservice;


import java.util.Set;
import jakarta.ws.rs.core.Application;

/**
 *
 * @author Juneau
 */
@jakarta.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(org.employeeservice.AcmeEmployeeFacadeREST.class);
        //addRestResourceClasses(resources);
        return resources;
    }
}