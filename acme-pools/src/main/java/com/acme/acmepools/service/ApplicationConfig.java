/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.service;

import java.util.Set;
import javax.ws.rs.core.Application;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Juneau
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.acme.acmepools.service.ColumnModelFacadeREST.class);
        resources.add(com.acme.acmepools.service.CustomerFacadeREST.class);
        resources.add(com.acme.acmepools.service.DiscountCodeFacadeREST.class);
        resources.add(com.acme.acmepools.service.MicroMarketFacadeREST.class);
        resources.add(com.acme.acmepools.service.SseEventBroadcaster.class);
        resources.add(com.acme.acmepools.service.SseEventResource.class);
    }
    
}

