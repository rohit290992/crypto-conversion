package org.crypto.conversion.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.crypto.conversion.client.CryptoProviderClient;
import org.crypto.conversion.dto.ConversionResponse;
import org.crypto.conversion.dto.CryptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Service
public class ConversionService {

    private final ConversionHistoryService conversionHistoryService;

    private final CryptoProviderClient cryptoProviderClient;

    @Autowired
    public ConversionService(ConversionHistoryService conversionHistoryService, CryptoProviderClient cryptoProviderClient) {
        this.conversionHistoryService = conversionHistoryService;
        this.cryptoProviderClient = cryptoProviderClient;
    }

    public List<CryptoCurrency> getCurrencies() {
        return cryptoProviderClient.getCryptoList();
    }


    public ConversionResponse cryptoToLocalCurrency(String cryptoId, String ipAddress, HttpServletRequest request, HttpSession session) {
        String parsedIpAddress = ipAddress != null ? ipAddress : request.getRemoteAddr();
        String username = (String) session.getAttribute("username");
        String currencyString = cryptoProviderClient.fetchCurrencyFromIp(parsedIpAddress);
        Currency currency = Currency.getInstance(currencyString);
        // Make the API request to CoinGecko to fetch the current price
        ResponseEntity<Map<String, Map<String, BigDecimal>>> response = cryptoProviderClient.cryptoToLocalCurrencyValue(cryptoId, currency);
        ConversionResponse conversionResponse = new ConversionResponse(
                Objects.requireNonNull(response.getBody()).get(cryptoId).get(currencyString.toLowerCase()),
                currency.getSymbol(Locale.getDefault()), cryptoId, LocalDateTime.now());
        conversionHistoryService.addConversionToHistory(username,conversionResponse);
        return conversionResponse;

    }

}
