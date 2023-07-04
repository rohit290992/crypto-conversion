package org.crypto.conversion.dto;

import java.math.BigDecimal;

// According to IntelliJ, this class is not used
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

// Two methods in this class are unused
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
