package com.bradesco.DesafioBackEnd.Services;


import com.bradesco.DesafioBackEnd.DTOs.UserDTO;
import com.bradesco.DesafioBackEnd.Entities.UserEntity;
import com.bradesco.DesafioBackEnd.Enums.UserTypeEnum;
import com.bradesco.DesafioBackEnd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthDate(userDTO.getBirthDate());
        user.setUserType(UserTypeEnum.valueOf(userDTO.getUserType().toUpperCase()));

        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado")));
    }

    public UserEntity updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setBirthDate(userDTO.getBirthDate());
            user.setUserType(UserTypeEnum.valueOf(userDTO.getUserType().toUpperCase()));
            return userRepository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado");
        }
        userRepository.deleteById(id);
    }
}

