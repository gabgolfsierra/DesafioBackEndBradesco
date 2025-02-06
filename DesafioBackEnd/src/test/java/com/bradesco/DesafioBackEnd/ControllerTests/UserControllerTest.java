package com.bradesco.DesafioBackEnd.ControllerTests;

import com.bradesco.DesafioBackEnd.Controllers.UserController;
import com.bradesco.DesafioBackEnd.DTOs.UserDTO;
import com.bradesco.DesafioBackEnd.Entities.UserEntity;
import com.bradesco.DesafioBackEnd.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFullName("Gabriel G");
        userEntity.setEmail("gab@gmail.com");

        userDTO = new UserDTO();
        userDTO.setFullName("Gabriel G");
        userDTO.setEmail("gab@gmail.com");
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userEntity);
        ResponseEntity<UserEntity> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userEntity, response.getBody());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(userEntity));
        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllUsers_ShouldReturnNoContent_WhenListIsEmpty() {
        when(userService.getAllUsers()).thenReturn(List.of());
        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userEntity));
        ResponseEntity<UserEntity> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userEntity, response.getBody());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userController.getUserById(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(userEntity);
        ResponseEntity<?> response = userController.updateUser(1L, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userEntity, response.getBody());
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.updateUser(eq(1L), any(UserDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseEntity<?> response = userController.updateUser(1L, userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteUser_ShouldReturnSuccessMessage_WhenUserExists() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userEntity));
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<?> response = userController.deleteUser(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.deleteUser(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
