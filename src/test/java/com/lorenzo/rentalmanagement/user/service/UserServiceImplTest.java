package com.lorenzo.rentalmanagement.user.service;

import com.lorenzo.rentalmanagement.common.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.role.domain.entity.Role;
import com.lorenzo.rentalmanagement.role.repository.RoleRepository;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.dto.request.UserRequest;
import com.lorenzo.rentalmanagement.user.dto.request.UserUpdateRequest;
import com.lorenzo.rentalmanagement.user.dto.response.UserResponse;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import com.lorenzo.rentalmanagement.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role("TENANT");

        user = new User.Builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario@test.com")
                .phoneNumber("123456789")
                .password("hashedPassword")
                .role(role)
                .active(true)
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    void findAll_shouldReturnAllActiveUsers() {
        when(userRepository.findAllByActiveTrue()).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Mario");
        assertThat(result.get(0).getLastName()).isEqualTo("Rossi");
        verify(userRepository).findAllByActiveTrue();
    }

    @Test
    void findById_whenUserExists_shouldReturnUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.findById(1L);

        assertThat(result.getFirstName()).isEqualTo("Mario");
        assertThat(result.getEmail()).isEqualTo("mario@test.com");
    }

    @Test
    void findById_whenUserNotExists_shouldThrowResourceNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createUser_whenRoleExists_shouldSaveAndReturnUserResponse() {
        UserRequest request = new UserRequest();
        request.setFirstName("Luigi");
        request.setLastName("Verdi");
        request.setEmail("luigi@test.com");
        request.setPhoneNumber("987654321");
        request.setPassword("password123");
        request.setRoleId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.createUser(request);

        assertThat(result).isNotNull();
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_whenRoleNotExists_shouldThrowResourceNotFoundException() {
        UserRequest request = new UserRequest();
        request.setFirstName("Luigi");
        request.setLastName("Verdi");
        request.setEmail("luigi@test.com");
        request.setPhoneNumber("987654321");
        request.setPassword("password123");
        request.setRoleId(99L);

        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_whenUserAndRoleExist_shouldUpdateAndReturnUserResponse() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName("Mario Updated");
        request.setLastName("Rossi Updated");
        request.setEmail("mario.updated@test.com");
        request.setPhoneNumber("111111111");
        request.setRoleId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.updateUser(1L, request);

        assertThat(result).isNotNull();
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_whenUserNotExists_shouldThrowResourceNotFoundException() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setEmail("mario@test.com");
        request.setPhoneNumber("123456789");
        request.setRoleId(1L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteById_whenUserExists_shouldSetActiveFalse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.deleteById(1L);

        assertThat(user.getActive()).isFalse();
        verify(userRepository).save(user);
    }

    @Test
    void deleteById_whenUserNotExists_shouldThrowResourceNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void findAllByRole_shouldReturnUsersWithGivenRole() {
        when(userRepository.findAllByRole_NameAndActiveTrue("TENANT")).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAllByRole("TENANT");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRoleName()).isEqualTo("TENANT");
        verify(userRepository).findAllByRole_NameAndActiveTrue("TENANT");
    }
}