/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juneau
 */
@Entity
@Table(name = "POOL_SALE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoolSale.findAll", query = "SELECT p FROM PoolSale p"),
    @NamedQuery(name = "PoolSale.findById", query = "SELECT p FROM PoolSale p WHERE p.id = :id"),
    @NamedQuery(name = "PoolSale.findBySaleDate", query = "SELECT p FROM PoolSale p WHERE p.saleDate = :saleDate")})
public class PoolSale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SALE_DATE")
    private LocalDate saleDate;
    @JoinColumn(name = "POOL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Pool poolId;

    public PoolSale() {
    }

    public PoolSale(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Pool getPoolId() {
        return poolId;
    }

    public void setPoolId(Pool poolId) {
        this.poolId = poolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoolSale)) {
            return false;
        }
        PoolSale other = (PoolSale) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.acmepools.entity.PoolSale[ id=" + id + " ]";
    }
    
}
