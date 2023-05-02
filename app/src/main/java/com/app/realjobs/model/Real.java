package com.app.realjobs.model;

public class Real {
    String description;
    String amount;

    public String getDescription() {
        return description;
    }

    public Real(String description, String amount) {
        this.description = description;
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
