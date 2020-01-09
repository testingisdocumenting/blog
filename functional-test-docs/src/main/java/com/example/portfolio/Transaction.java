package com.example.portfolio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Transaction {
    private String id;
    private double lot;
    private double price;
    private String symbol;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
