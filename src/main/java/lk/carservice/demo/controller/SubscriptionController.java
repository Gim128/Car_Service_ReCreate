package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.SubscriptionDTO;
import lk.carservice.demo.dto.SubscriptionResponseDTO;
import lk.carservice.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription (@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionResponseDTO createdSubscription = subscriptionService.createSubscription(subscriptionDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(@PathVariable Integer id) {
        SubscriptionResponseDTO subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        if (userId != null) {
            return ResponseEntity.ok(subscriptionService.getSubscriptionsByUser(userId));
        } else if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(subscriptionService.getSubscriptionByPriceRange(minPrice, maxPrice));
        } else {
            return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> updateSubscription(
            @PathVariable Integer id, @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionResponseDTO updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDTO);
        return ResponseEntity.ok(updatedSubscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
