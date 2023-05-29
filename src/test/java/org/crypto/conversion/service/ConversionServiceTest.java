package org.crypto.conversion.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.crypto.conversion.client.CryptoProviderClient;
import org.crypto.conversion.dto.ConversionResponse;
import org.crypto.conversion.dto.CryptoCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConversionServiceTest {

    @Mock
    private ConversionHistoryService conversionHistoryService;
    @Mock
    private CryptoProviderClient cryptoProviderClient;

    private ConversionService conversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conversionService = new ConversionService(conversionHistoryService, cryptoProviderClient);
    }

    @Test
    void getCurrencies_ShouldReturnCryptoListFromProviderClient() {
        // Given
        List<CryptoCurrency> expectedCurrencies = Collections.singletonList(new CryptoCurrency("bitcoin", "symbol", "Bitcoin"));
        when(cryptoProviderClient.getCryptoList()).thenReturn(expectedCurrencies);

        // When
        List<CryptoCurrency> actualCurrencies = conversionService.getCurrencies();

        // Then
        assertEquals(expectedCurrencies, actualCurrencies);
    }

    @Test
    void cryptoToLocalCurrency_ShouldReturnConversionResponse() {
        // Given
        String cryptoCurrency = "bitcoin";
        String ipAddress = "127.0.0.1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String username = "testUser";
        String currencyString = "USD";
        Currency currency = Currency.getInstance(currencyString);
        BigDecimal cryptoToLocalCurrencyValue = BigDecimal.valueOf(24659);
        LocalDateTime currentDateTime = LocalDateTime.now();

        ResponseEntity<Map<String, Map<String, BigDecimal>>> responseEntity = ResponseEntity.ok(Map.of(
                cryptoCurrency, Map.of(currencyString.toLowerCase(), cryptoToLocalCurrencyValue)));

        when(request.getRemoteAddr()).thenReturn(ipAddress);
        when(session.getAttribute("username")).thenReturn(username);
        when(cryptoProviderClient.fetchCurrencyFromIp(ipAddress)).thenReturn(currencyString);
        when(cryptoProviderClient.cryptoToLocalCurrencyValue(cryptoCurrency, currency))
                .thenReturn(responseEntity);

        // When
        ConversionResponse conversionResponse = conversionService.cryptoToLocalCurrency(cryptoCurrency, ipAddress, request, session);

        // Then
        assertEquals(cryptoToLocalCurrencyValue, conversionResponse.getPrice());
        assertEquals(currency.getSymbol(Locale.getDefault()), conversionResponse.getCurrencySymbol());
        assertEquals(cryptoCurrency, conversionResponse.getCryptoCurrency());
        assertEquals(currentDateTime.getYear(), conversionResponse.getLocalDateTime().getYear());
        assertEquals(currentDateTime.getMonth(), conversionResponse.getLocalDateTime().getMonth());
        assertEquals(currentDateTime.getDayOfMonth(), conversionResponse.getLocalDateTime().getDayOfMonth());
        assertEquals(currentDateTime.getHour(), conversionResponse.getLocalDateTime().getHour());
        assertEquals(currentDateTime.getMinute(), conversionResponse.getLocalDateTime().getMinute());
        assertEquals(currentDateTime.getSecond(), conversionResponse.getLocalDateTime().getSecond());

        verify(conversionHistoryService).addConversionToHistory(username, conversionResponse);
    }
}

