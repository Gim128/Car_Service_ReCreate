package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.PaymentDTO;
import lk.carservice.demo.dto.PaymentResponseDTO;
import lk.carservice.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentResponseDTO createdPayment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable Integer paymentId,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.updatePayment(paymentId, paymentDTO));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable Integer paymentId) {
        paymentService.deletePaymentById(paymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByBuyer(@PathVariable Integer buyerId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBuyer(buyerId));
    }

    @GetMapping("/card/{carId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByCar(@PathVariable Integer carId) {
        return ResponseEntity.ok(paymentService.getPaymentsByCar(carId));
    }
}
