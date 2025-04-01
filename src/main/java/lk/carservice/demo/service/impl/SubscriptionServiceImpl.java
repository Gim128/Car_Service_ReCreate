package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.SubscriptionDTO;
import lk.carservice.demo.dto.SubscriptionResponseDTO;
import lk.carservice.demo.entity.Subscription;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.SubscriptionRepository;
import lk.carservice.demo.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ModelMapper modelMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubscriptionResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return modelMapper.map(savedSubscription, SubscriptionResponseDTO.class);
    }

    @Override
    public SubscriptionResponseDTO getSubscriptionById(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        return modelMapper.map(subscription, SubscriptionResponseDTO.class);

    }

    @Override
    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDTO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<SubscriptionResponseDTO> getSubscriptionByUser(Integer userId) {
//        return subscriptionRepository.findByUserId(userId).stream()
//                .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public SubscriptionResponseDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));

        modelMapper.map(subscriptionDTO, existingSubscription);
        existingSubscription.setSubscriptionId(id); // Ensure ID remains the same
        Subscription updatedSubscription = subscriptionRepository.save(existingSubscription);

        return modelMapper.map(updatedSubscription, SubscriptionResponseDTO.class);
    }

    @Override
    public void deleteSubscription(int id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subscription not found with id: " + id);
        }
        subscriptionRepository.deleteById(id);
    }

    @Override
    public List<SubscriptionResponseDTO> getSubscriptionByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return subscriptionRepository.findBySubscriptionAmountBetween(minPrice, maxPrice).stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<SubscriptionResponseDTO> getSubscriptionsByUser(Integer userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
