package org.crypto.conversion.dto;

import java.math.BigDecimal;

public class CryptoPriceResponse {
    private CryptoCode cryptoCode;
    // Constructors, getters, and setters

    public CryptoPriceResponse() {
    }

    public CryptoPriceResponse(CryptoCode currency) {
        this.cryptoCode = currency;
    }

    public CryptoCode getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(CryptoCode cryptoCode) {
        this.cryptoCode = cryptoCode;
    }


}

class CryptoCode {
    private BigDecimal localCurrency;

    public CryptoCode() {
    }

    public BigDecimal getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(BigDecimal localCurrency) {
        this.localCurrency = localCurrency;
    }
}
