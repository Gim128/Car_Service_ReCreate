package lk.carservice.demo.service;


import lk.carservice.demo.dto.UserDTO;
import lk.carservice.demo.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO getUserById(Integer userId);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Integer userId, UserDTO userDTO);
    void deleteUser(Integer userId);
    UserResponseDTO activateUser(Integer userId);
    UserResponseDTO deactivateUser(Integer userId);
    List<UserResponseDTO> getUsersByType(Integer userType);
    List<UserResponseDTO> getActiveUsers();
    String getUserNameById(Integer userId);

}
