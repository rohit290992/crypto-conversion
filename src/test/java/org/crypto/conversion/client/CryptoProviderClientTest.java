package org.crypto.conversion.client;


import org.crypto.conversion.dto.CryptoCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CryptoProviderClientTest {

    @Mock
    private RestTemplate restTemplate;

    private CryptoProviderClient cryptoProviderClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cryptoProviderClient = new CryptoProviderClient(restTemplate);
    }

    @Test
    void cryptoToLocalCurrencyValue_ShouldReturnResponseEntityWithCorrectUrlAndHeaders() {
        // Given
        String cryptoId = "bitcoin";
        Currency currency = Currency.getInstance("EUR");

        String expectedUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=EUR&precision=2";
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Content-Type", "application/json");

        ResponseEntity<Map<String, Map<String, BigDecimal>>> expectedResponseEntity = ResponseEntity.ok().build();

        when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(expectedResponseEntity);

        // When
        ResponseEntity<Map<String, Map<String, BigDecimal>>> actualResponseEntity =
                cryptoProviderClient.cryptoToLocalCurrencyValue(cryptoId, currency);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void fetchCurrencyFromIp_ShouldReturnCurrencyString() {
        // Given
        String parsedIpAddress = "127.0.0.1";
        String expectedCurrencyString = "USD";

        String expectedUrl = "https://ipapi.co/127.0.0.1/currency/";

        when(restTemplate.getForObject(eq(expectedUrl), eq(String.class))).thenReturn(expectedCurrencyString);

        // When
        String actualCurrencyString = cryptoProviderClient.fetchCurrencyFromIp(parsedIpAddress);

        // Then
        assertEquals(expectedCurrencyString, actualCurrencyString);
    }

    @Test
    void getCryptoList_ShouldReturnCachedCryptoList_WhenCacheIsNotEmpty() {
        // Given
        ReflectionTestUtils.setField(cryptoProviderClient, "currencyListCache", createMockCryptoList());

        // When
        var actualCryptoList = cryptoProviderClient.getCryptoList();

        // Then
        assertEquals(createMockCryptoList(), actualCryptoList);
    }

    private List<CryptoCurrency> createMockCryptoList() {
        List<CryptoCurrency> cryptoList = new ArrayList<>();
        cryptoList.add(new CryptoCurrency("bitcoin","b_symbol","Bitcoin"));
        cryptoList.add(new CryptoCurrency("ethereum","e_symbol","Etherium"));
        return cryptoList;
    }
}
