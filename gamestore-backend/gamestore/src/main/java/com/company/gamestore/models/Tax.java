package com.company.gamestore.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;

@Entity
public class Tax {

    @Id
    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "rate", precision = 8, scale = 2, nullable = false)
    private BigDecimal rate;

    // Constructors, getters, setters, and other methods

    // Default constructor
    public Tax() {
    }

    // Constructor with parameters
    public Tax(String state, BigDecimal rate) {
        this.state = state;
        this.rate = rate;
    }

    // Getters and setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}