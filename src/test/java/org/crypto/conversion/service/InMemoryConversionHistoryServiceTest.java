package org.crypto.conversion.service;

import static org.junit.jupiter.api.Assertions.*;

import org.crypto.conversion.dto.ConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


class InMemoryConversionHistoryServiceTest {

    private InMemoryConversionHistoryService conversionHistoryService;

    @BeforeEach
    void setUp() {
        conversionHistoryService = new InMemoryConversionHistoryService();
    }

    @Test
    void getAllConversionHistory_ShouldReturnEmptyList_WhenNoConversionHistoryExists() {
        // Given
        String username = "testUser";

        // When
        List<ConversionResponse> conversionHistory = conversionHistoryService.getAllConversionHistory(username);

        // Then
        assertTrue(conversionHistory.isEmpty());
    }

    @Test
    void addConversionToHistory_ShouldAddConversionToHistory_WhenUsernameHasNoExistingConversionHistory() {
        // Given
        String username = "testUser";
        ConversionResponse conversionResponse = new ConversionResponse(BigDecimal.valueOf(100),
                "$",
                "bitcoin",
                LocalDateTime.now());

        // When
        conversionHistoryService.addConversionToHistory(username, conversionResponse);
        List<ConversionResponse> conversionHistory = conversionHistoryService.getAllConversionHistory(username);

        // Then
        assertNotNull(conversionHistory);
        assertEquals(1, conversionHistory.size());
        assertEquals(conversionResponse, conversionHistory.get(0));
    }

    @Test
    void addConversionToHistory_ShouldAddConversionToExistingHistory_WhenUsernameHasExistingConversionHistory() {
        // Given
        String username = "testUser";
        ConversionResponse existingConversion = new ConversionResponse(BigDecimal.valueOf(200), "$", "ethereum", LocalDateTime.now());
        conversionHistoryService.addConversionToHistory(username, existingConversion);

        ConversionResponse newConversion = new ConversionResponse(BigDecimal.valueOf(100), "$", "bitcoin", LocalDateTime.now());

        // When
        conversionHistoryService.addConversionToHistory(username, newConversion);
        List<ConversionResponse> conversionHistory = conversionHistoryService.getAllConversionHistory(username);

        // Then
        // You can use Hamcrest matchers: assertThat(X).hasSize(2)
        // They are known to provide much more descriptive test results in case of failures
        assertNotNull(conversionHistory);
        assertEquals(2, conversionHistory.size());
        assertTrue(conversionHistory.contains(existingConversion));
        assertTrue(conversionHistory.contains(newConversion));
    }
}

