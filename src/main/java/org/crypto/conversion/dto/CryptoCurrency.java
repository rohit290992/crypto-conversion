package org.crypto.conversion.dto;

public class CryptoCurrency {
    private String id;
    private String symbol;
    private String name;

    public CryptoCurrency() {
    }

    public CryptoCurrency(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // o can be an instance of a class that is a descendednt of this class
        // See here https://www.baeldung.com/java-equals-hashcode-contracts#1-overriding-equals
        if (o == null || getClass() != o.getClass()) return false;

        CryptoCurrency that = (CryptoCurrency) o;

        // id can be null --> NPE
        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        // id can be null --> NPE
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
