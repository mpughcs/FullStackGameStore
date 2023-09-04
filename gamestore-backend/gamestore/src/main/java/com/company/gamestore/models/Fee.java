package com.company.gamestore.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;

@Entity
public class Fee {

    @Id
    @Column(name = "product_type", length = 50)
    private String productType;

    @Column(name = "fee", precision = 8, scale = 2, nullable = false)
    private BigDecimal fee;

    // Constructors, getters, setters, and other methods

    // Default constructor
    public Fee() {
    }

    // Constructor with parameters
    public Fee(String productType, BigDecimal fee) {
        this.productType = productType;
        this.fee = fee;
    }

    // Getters and setters
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}