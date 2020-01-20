package com.example.portfolio;

public class TransactionFluent {
    private String id;
    private double lot;
    private double price;
    private String symbol;

    private TransactionFluent() {
    }

    public static TransactionFluent transaction(String id) {
        TransactionFluent transaction = new TransactionFluent();
        transaction.id = id;

        return transaction;
    }

    public TransactionFluent lot(double lot) {
        this.lot = lot;
        return this;
    }

    public TransactionFluent price(double price) {
        this.price = price;
        return this;
    }

    public TransactionFluent symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getId() {
        return id;
    }

    public double getLot() {
        return lot;
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }
}
