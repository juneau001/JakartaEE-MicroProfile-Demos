/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Juneau
 */
public class SaleEvent {

    private BigDecimal pool;
    private String storeName;
    private BigDecimal price;
    private LocalDate date;
    private List<String> notifyList;
    
    /**
     * @return the pool
     */
    public BigDecimal getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(BigDecimal pool) {
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
}
