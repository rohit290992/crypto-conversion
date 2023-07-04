package org.crypto.conversion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Some unused methods
// Also, you can use Lombok to avoid lots of boilerplate code
public class ConversionResponse {
    private BigDecimal price;
    private String currencySymbol;

    private String cryptoCurrency;

    private LocalDateTime localDateTime;

    public ConversionResponse(BigDecimal price, String currencySymbol, String cryptoCurrency, LocalDateTime localDateTime) {
        this.price = price;
        this.currencySymbol = currencySymbol;
        this.cryptoCurrency = cryptoCurrency;
        this.localDateTime = localDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

