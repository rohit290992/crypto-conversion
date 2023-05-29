package org.crypto.conversion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.crypto.conversion.dto.ConversionResponse;
import org.crypto.conversion.service.ConversionHistoryService;
import org.crypto.conversion.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class PriceController {

    private final ConversionHistoryService conversionHistoryService;

    private final ConversionService conversionService;

    @Autowired
    public PriceController(ConversionHistoryService conversionHistoryService, ConversionService conversionService) {
        this.conversionHistoryService = conversionHistoryService;
        this.conversionService = conversionService;
    }

    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("cryptocurrencies", conversionService.getCurrencies());
        model.addAttribute("conversionHistory",
                conversionHistoryService.getAllConversionHistory(session.getAttribute("username").toString()));
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam("username") String username, HttpSession session) {
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String performLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/price")
    public ResponseEntity<ConversionResponse> fetchPrice(@RequestParam("crypto") String cryptoId,
                                                         @RequestParam("ip") String ipAddress,
                                                         HttpServletRequest request,
                                                         HttpSession session) {

        return ResponseEntity.ok(conversionService.cryptoToLocalCurrency(cryptoId, ipAddress, request, session));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ConversionResponse>> getHistory(HttpSession session) {

        return ResponseEntity.ok(conversionHistoryService.getAllConversionHistory(session.getAttribute("username").toString()));
    }
}
