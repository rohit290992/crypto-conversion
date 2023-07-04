package org.crypto.conversion.dto;

public class Conversion {
    private String currency;

    public Conversion(String currency, double localizedPrice) {
        this.currency = currency;
        this.localizedPrice = localizedPrice;
    }

    private double localizedPrice;

    // Below methods are not used

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLocalizedPrice() {
        return localizedPrice;
    }

    public void setLocalizedPrice(double localizedPrice) {
        this.localizedPrice = localizedPrice;
    }
}
