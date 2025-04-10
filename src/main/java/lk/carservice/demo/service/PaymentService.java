package lk.carservice.demo.service;

import lk.carservice.demo.dto.PaymentDTO;
import lk.carservice.demo.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentDTO paymentDTO);
    PaymentResponseDTO getPaymentById(Integer paymentId);
    List<PaymentResponseDTO> getAllPayments();
    PaymentResponseDTO updatePayment(Integer PaymentId, PaymentDTO paymentDTO);
    void deletePaymentById(Integer paymentId);
    List<PaymentResponseDTO> getPaymentsByBuyer(Integer buyerId);
    List<PaymentResponseDTO> getPaymentsByCar(Integer cardId);
}
