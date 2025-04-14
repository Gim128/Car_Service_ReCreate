package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.AdminDTO;
import lk.carservice.demo.dto.AdminResponseDTO;
import lk.carservice.demo.entity.Admin;
import lk.carservice.demo.exceptions.ResourceAlreadyExistsException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.AdminRepository;
import lk.carservice.demo.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AdminResponseDTO createAdmin(AdminDTO adminDTO) {

        if (adminRepository.existsByUsername(adminDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }


        if (adminRepository.existsByEmail(adminDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        Admin admin = modelMapper.map(adminDTO, Admin.class);
        admin.setPasswordHash(passwordEncoder.encode(adminDTO.getPassword()));

        if (admin.getIsActive() == null) {
            admin.setIsActive(true);
        }

        if (admin.getRole() == null) {
            admin.setRole("ADMIN");
        }

        Admin savedAdmin = adminRepository.save(admin);
        return modelMapper.map(savedAdmin, AdminResponseDTO.class);
    }

    @Override
    public AdminResponseDTO getAdminById(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        return modelMapper.map(admin, AdminResponseDTO.class);
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> modelMapper.map(admin, AdminResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdminResponseDTO updateAdmin(Integer adminId, AdminDTO adminDTO) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));

        // Check if new username conflicts with existing admins (excluding current one)
        if (adminDTO.getUsername() != null &&
                !adminDTO.getUsername().equals(existingAdmin.getUsername()) &&
                adminRepository.existsByUsername(adminDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        // Check if new email conflicts with existing admins (excluding current one)
        if (adminDTO.getEmail() != null &&
                !adminDTO.getEmail().equals(existingAdmin.getEmail()) &&
                adminRepository.existsByEmail(adminDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        modelMapper.map(adminDTO, existingAdmin);
        existingAdmin.setAdminId(adminId);

        // Update password if provided
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
            existingAdmin.setPasswordHash(passwordEncoder.encode(adminDTO.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return modelMapper.map(updatedAdmin, AdminResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        adminRepository.delete(admin);
    }

    @Override
    public AdminResponseDTO activateAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        admin.setIsActive(true);
        Admin activatedAdmin = adminRepository.save(admin);
        return modelMapper.map(activatedAdmin, AdminResponseDTO.class);
    }

    @Override
    public AdminResponseDTO deactivateAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        admin.setIsActive(false);
        Admin deactivatedAdmin = adminRepository.save(admin);
        return modelMapper.map(deactivatedAdmin, AdminResponseDTO.class);
    }

    @Override
    public AdminResponseDTO getAdminByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with username: " + username));
        return modelMapper.map(admin, AdminResponseDTO.class);
    }
}
