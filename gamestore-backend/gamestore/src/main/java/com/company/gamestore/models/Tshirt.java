package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "tshirt")
public class Tshirt {
    @Id
    @Column(name = "tshirt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Tshirt requires a size")
    private String size;
    @NotEmpty(message = "Tshirt requires a color")
    private String color;
    @NotEmpty(message = "Tshirt requires a description")
    private String description;
    @NotNull(message = "Tshirt requires a price")
    @DecimalMin(value = "0.00", inclusive = true, message = "Tshirt price cannot be below $0.00")
    @DecimalMax(value = "999.99", inclusive = true, message = "Tshirt price cannot be above $999.99")
    private BigDecimal price;
    @NotNull(message = "Tshirt quantity must be 0 or above")
    private int quantity;

    public Tshirt(String size, String color, String description, BigDecimal price, int quantity){
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Tshirt(){}

    public int getId(){
        return id;
    }

    public int getQuantity(){
        return quantity;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public String getSize(){
        return size;
    }

    public String getDescription(){
        return description;
    }

    public String getColor(){
        return color;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setSize(String size){
        this.size = size;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setColor(String color){
        this.color = color;
    }

    @Override
    public boolean equals(Object o){
    if(this == o)return true;
    if(!(o instanceof Tshirt)) return false;
    Tshirt t = (Tshirt) o;
    return Objects.equals(getColor(), ((Tshirt) o).getColor()) && Objects.equals(getDescription(), ((Tshirt) o).getDescription())
            && Objects.equals(getSize(), ((Tshirt) o).getSize()) && Objects.equals(getId(), ((Tshirt) o).getId())
            && Objects.equals(getPrice(), ((Tshirt) o).getPrice()) && Objects.equals(getQuantity(), ((Tshirt) o).getQuantity());
    }

    @Override
    public int hashCode(){ return Objects.hash(getColor(), getDescription(), getSize(), getId(), getPrice(), getQuantity());}
}
