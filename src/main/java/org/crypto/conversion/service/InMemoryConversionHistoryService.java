package org.crypto.conversion.service;

import org.crypto.conversion.dto.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryConversionHistoryService implements ConversionHistoryService {
    private final Map<String, List<ConversionResponse>> conversionHistory;

    public InMemoryConversionHistoryService() {
        this.conversionHistory = new HashMap<>();
    }

    @Override
    public List<ConversionResponse> getAllConversionHistory(String username) {
        return conversionHistory.getOrDefault(username, new ArrayList<>());
    }

    @Override
    public void addConversionToHistory(String username, ConversionResponse conversionResponse) {
        if (conversionHistory.get(username) != null) {
            conversionHistory.get(username).add(conversionResponse);
        } else {
            List<ConversionResponse> conversionResponses = new ArrayList<>();
            conversionResponses.add(conversionResponse);
            conversionHistory.put(username, conversionResponses);
        }
    }

}
