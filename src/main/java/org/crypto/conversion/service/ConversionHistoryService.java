package org.crypto.conversion.service;

import org.crypto.conversion.dto.Conversion; // <-- Unused import
import org.crypto.conversion.dto.ConversionResponse;

import java.util.List;

public interface ConversionHistoryService {
    List<ConversionResponse> getAllConversionHistory(String username);

    void addConversionToHistory(String username, ConversionResponse conversionResponse);
}
