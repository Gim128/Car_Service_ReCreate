package lk.carservice.demo.service.impl;

import lk.carservice.demo.dto.PaymentDTO;
import lk.carservice.demo.dto.PaymentResponseDTO;
import lk.carservice.demo.entity.Payment;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.PaymentRepository;
import lk.carservice.demo.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        Payment savedPayment = paymentRepository.save(payment);
        return modelMapper.map(savedPayment, PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO updatePayment(Integer PaymentId, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(PaymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + PaymentId));

        modelMapper.map(paymentDTO, existingPayment);
        existingPayment.setPaymentId(PaymentId);

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return modelMapper.map(updatedPayment, PaymentResponseDTO.class);
    }

    @Override
    public void deletePaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByBuyer(Integer buyerId) {
        return paymentRepository.findByBuyerId(buyerId)
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByCar(Integer carId) {
        return paymentRepository.findByCarId(carId)
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }
}
