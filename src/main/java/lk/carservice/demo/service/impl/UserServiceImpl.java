package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.UserDTO;
import lk.carservice.demo.dto.UserResponseDTO;
import lk.carservice.demo.entity.User;
import lk.carservice.demo.exceptions.EmailAlreadyExistsException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.UserRepository;
import lk.carservice.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use: " + userDTO.getEmail());
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
        response.setUserTypeName(getUserTypeName(user.getUserType()));
        response.setUserTypeName(user.getUserType().toString());
        return response;
    }

    @Override
    public UserResponseDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return convertToResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Integer userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Check if new email conflicts with existing users
        if (!userDTO.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use: " + userDTO.getEmail());
        }

        modelMapper.map(userDTO, existingUser);
        existingUser.setUserId(userId);

        // Only encode password if it's different
        if (!userDTO.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponseDTO activateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setIsActive(true);
        User activatedUser = userRepository.save(user);
        return convertToResponseDTO(activatedUser);
    }

    @Override
    public UserResponseDTO deactivateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setIsActive(false);
        User deactivatedUser = userRepository.save(user);
        return convertToResponseDTO(deactivatedUser);
    }

    @Override
    public List<UserResponseDTO> getUsersByType(Integer userType) {
        return userRepository.findByUserType(userType)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> getActiveUsers() {
        return userRepository.findByIsActive(true)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String getUserNameById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return user.getFirstName() + " " + user.getLastName();
    }

    private String getUserTypeName(Integer userType) {
        if (userType == null) return "Unknown";
        return switch (userType) {
            case 1 -> "Admin";
            case 2 -> "Seller";
            case 3 -> "Customer";
            default -> "Unknown";
        };
    }
}
