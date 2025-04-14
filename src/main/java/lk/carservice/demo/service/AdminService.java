package lk.carservice.demo.service;

import lk.carservice.demo.dto.AdminDTO;
import lk.carservice.demo.dto.AdminResponseDTO;

import java.util.List;

public interface AdminService {
    AdminResponseDTO createAdmin(AdminDTO adminDTO);
    AdminResponseDTO getAdminById(Integer adminId);
    List<AdminResponseDTO> getAllAdmins();
    AdminResponseDTO updateAdmin(Integer adminId, AdminDTO adminDTO);
    void deleteAdmin(Integer adminId);
    AdminResponseDTO activateAdmin(Integer adminId);
    AdminResponseDTO deactivateAdmin(Integer adminId);
    AdminResponseDTO getAdminByUsername(String username);
}
