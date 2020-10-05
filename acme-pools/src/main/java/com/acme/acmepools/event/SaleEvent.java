/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.event;

import com.acme.acmepools.entity.Pool;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Juneau
 */
public class SaleEvent implements Serializable {

    private Pool pool;
    private String storeName;
    private BigDecimal price;
    private LocalDate date;
    private List<String> notifyList;
    
    public SaleEvent(){
        
    }
    
    public SaleEvent(String storeName,
                     LocalDate date,
                     Pool pool){
        this.storeName = storeName;
        this.date = date;
        this.pool = pool;
    }
    
    /**
     * @return the pool
     */
    public Pool getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(Pool pool) {
        this.pool = pool;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the notifyList
     */
    public List<String> getNotifyList() {
        return notifyList;
    }

    /**
     * @param notifyList the notifyList to set
     */
    public void setNotifyList(List<String> notifyList) {
        this.notifyList = notifyList;
    }
    
    @Override
    public String toString() {
        return this.storeName + this.pool.getStyle();
    }
}
