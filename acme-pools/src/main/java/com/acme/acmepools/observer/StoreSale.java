/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.observer;

import com.acme.acmepools.entity.PoolSale;
import com.acme.acmepools.event.SaleEvent;
import com.acme.acmepools.session.PoolSaleFacade;
import java.io.Serializable;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juneau
 */
public class StoreSale implements Serializable {
    
    @EJB
    private PoolSaleFacade poolSaleFacade;
    
    private static Logger log = LogManager.getLogger();
    
    public void standardStoreSale(@Observes @com.acme.acmepools.qualifier.StoreSale SaleEvent saleEvent){
        log.info("Standard Store Sale: " + saleEvent.getStoreName());
        System.out.println("Standard Store Sale: " + saleEvent.getStoreName());
        PoolSale poolSale = new PoolSale();
        poolSale.setPoolId(saleEvent.getPool());
        poolSale.setSaleDate(LocalDate.now());
        poolSaleFacade.create(poolSale);
    }
    
    public void asyncStoreSale(@ObservesAsync @com.acme.acmepools.qualifier.StoreSale SaleEvent saleEvent){
        log.info("Async Standard Store Sale: " + saleEvent.getStoreName());
        System.out.println("Async Standard Store Sale: " + saleEvent.getStoreName());
    }
    
}
