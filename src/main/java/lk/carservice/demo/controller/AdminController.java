package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.AdminDTO;
import lk.carservice.demo.dto.AdminResponseDTO;
import lk.carservice.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminResponseDTO> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AdminResponseDTO createdAdmin = adminService.createAdmin(adminDTO);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{adminId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Integer adminId) {
        return ResponseEntity.ok(adminService.getAdminById(adminId));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/{adminId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or (#adminId == principal.id)")
    public ResponseEntity<AdminResponseDTO> updateAdmin(
            @PathVariable Integer adminId,
            @Valid @RequestBody AdminDTO adminDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(adminId, adminDTO));
    }

    @DeleteMapping("/{adminId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{adminId}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminResponseDTO> activateAdmin(@PathVariable Integer adminId) {
        return ResponseEntity.ok(adminService.activateAdmin(adminId));
    }

    @PostMapping("/{adminId}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminResponseDTO> deactivateAdmin(@PathVariable Integer adminId) {
        return ResponseEntity.ok(adminService.deactivateAdmin(adminId));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<AdminResponseDTO> getAdminByUsername(@PathVariable String username) {
        return ResponseEntity.ok(adminService.getAdminByUsername(username));
    }
}
