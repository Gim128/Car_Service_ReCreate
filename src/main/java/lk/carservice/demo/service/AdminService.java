package lk.carservice.demo.service;

import lk.carservice.demo.entity.Admin;
import lk.carservice.demo.exceptions.AdminNotFoundException;
import lk.carservice.demo.exceptions.DuplicateEmailException;
import lk.carservice.demo.exceptions.DuplicateUsernameException;
import lk.carservice.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin createAdmin(Admin admin){
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()){
            throw new DuplicateUsernameException("Username already exists: "+admin.getUsername());
        }

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email already exists: "+admin.getEmail());
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdminById(int id){
        return adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException("Admin Not Found with Id: " + id));
    }

    public Admin updateAdmin(int id, Admin adminDetails){
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException("Admin Not Found with Id: " + id));

        if (adminDetails.getUsername() != null && !adminDetails.getUsername().equals(admin.getUsername())) {
            if (adminRepository.findByUsername(adminDetails.getUsername()).isPresent()) {
                throw new DuplicateUsernameException("Username already exists: " + adminDetails.getUsername());
            }
            admin.setUsername(adminDetails.getUsername());
        }

        if (adminDetails.getEmail() != null && !adminDetails.getEmail().equals(admin.getEmail())) {
            if (adminRepository.findByEmail(adminDetails.getEmail()).isPresent()) {
                throw new DuplicateEmailException("Email already exists: " + adminDetails.getEmail());
            }
            admin.setEmail(adminDetails.getEmail());
        }

        if (adminDetails.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
        }

        return adminRepository.save(admin);
    }

    public void deleteAdmin(Integer id) {
        if (!adminRepository.existsById(id)) {
            throw new AdminNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }


}
