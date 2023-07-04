package org.crypto.conversion.client;

import org.crypto.conversion.dto.CryptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

@Component
public class CryptoProviderClient {

    // currencyListCache can be final
    // also, you can use more advanced data structures like Guava Cache
    private static List<CryptoCurrency> currencyListCache = new ArrayList<>();
    private final RestTemplate restTemplate;

    @Autowired
    public CryptoProviderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Try using a DTO class instead of Map<String, Map<String, BigDecimal>>
    public ResponseEntity<Map<String, Map<String, BigDecimal>>> cryptoToLocalCurrencyValue(String cryptoId, Currency currency) {
        // Do not hard-code constants
        String urlForCrypto = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptoId + "&vs_currencies=" + currency + "&precision=2";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // This can be a static field of the class
        ParameterizedTypeReference<Map<String, Map<String, BigDecimal>>> responseType =
                new ParameterizedTypeReference<>() {
                };

        // This can be simplified to "return restTemplate.exchange(...)
        ResponseEntity<Map<String, Map<String, BigDecimal>>> response =
                restTemplate.exchange(urlForCrypto, HttpMethod.GET, entity, responseType);
        return response;
    }

    public String fetchCurrencyFromIp(String parsedIpAddress) {
        String url = "https://ipapi.co/" + parsedIpAddress + "/currency/";
        String currencyString = restTemplate.getForObject(url, String.class);
        return currencyString;
    }

    public  List<CryptoCurrency> getCryptoList() {
        if (!currencyListCache.isEmpty()) {
            return currencyListCache;
        }
        ParameterizedTypeReference<List<CryptoCurrency>> responseType =
                new ParameterizedTypeReference<>() {
                };

        String url = "https://api.coingecko.com/api/v3/coins/list";
        ResponseEntity<List<CryptoCurrency>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        // getBody() can be null, but addAll() accepts a non-null param
        // As such, it can result in NPE
        currencyListCache.addAll(response.getBody());
        return response.getBody();
    }
}
