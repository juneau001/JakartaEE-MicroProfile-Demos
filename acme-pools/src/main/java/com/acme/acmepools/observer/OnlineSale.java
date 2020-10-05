/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.observer;

import com.acme.acmepools.event.SaleEvent;
import java.io.Serializable;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juneau
 */
public class OnlineSale implements Serializable {
    private static Logger log = LogManager.getLogger();
    
    public void standardStoreSale(@Observes @com.acme.acmepools.qualifier.OnlineSale SaleEvent saleEvent){
        log.info("Online Store Sale: " + saleEvent.getStoreName());
        System.out.println("Online Store Sale: " + saleEvent.getStoreName());
    }
    
    public void asyncStoreSale(@ObservesAsync @com.acme.acmepools.qualifier.OnlineSale SaleEvent saleEvent){
        log.info("Async Online Store Sale: " + saleEvent.getStoreName());
        System.out.println("Async Online Store Sale: " + saleEvent.getStoreName());
    }
}
