package com.shems.server.controller;

import com.shems.server.context.UserContext;
import com.shems.server.converter.PriceToPriceConsumptionResponseConverter;
import com.shems.server.dto.response.PriceConsumptionResponse;
import com.shems.server.service.PriceService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/price")
public class PriceController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PriceController.class);

    @Inject
    private PriceService priceService;

    @Inject
    private PriceToPriceConsumptionResponseConverter converter;

    @GetMapping("/total")
    ResponseEntity<PriceConsumptionResponse> getTotal() {
        Long userId = UserContext.getCurrentUser();
        LOGGER.info("Fetching total price for user with id: {}", userId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(converter.convert(priceService.getTotal(userId)));
    }
}
