package com.lorenzo.rentalmanagement.property.service;

import com.lorenzo.rentalmanagement.common.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.property.service.impl.PropertyServiceImpl;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private Property property;
    private User owner;

    @BeforeEach
    void setUp() {
        owner = new User.Builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario@test.com")
                .phoneNumber("123456789")
                .password("password")
                .active(true)
                .build();

        property = new Property("Casa Roma", "Via Roma 1", "Roma", 3, new BigDecimal("1200"));
        property.setOwner(owner);
        property.setActive(true);
        property.setCreatedAt(LocalDate.now());
    }


    @Test
    void findAll_asAdmin_shouldReturnAllProperties() {
        when(propertyRepository.findAllByActiveTrue()).thenReturn(List.of(property));

        List<PropertyResponse> result = propertyService.findAll(1L, "ADMIN");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Casa Roma");
        verify(propertyRepository).findAllByActiveTrue();
    }

    @Test
    void findAll_asOwner_shouldReturnOnlyOwnerProperties() {
        when(propertyRepository.findAllByOwner_IdAndActiveTrue(1L)).thenReturn(List.of(property));

        List<PropertyResponse> result = propertyService.findAll(1L, "OWNER");

        assertThat(result).hasSize(1);
        verify(propertyRepository).findAllByOwner_IdAndActiveTrue(1L);
        verify(propertyRepository, never()).findAllByActiveTrue();
    }


    @Test
    void findById_whenPropertyExists_shouldReturnPropertyResponse() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        PropertyResponse result = propertyService.findById(1L);

        assertThat(result.getName()).isEqualTo("Casa Roma");
        assertThat(result.getCity()).isEqualTo("Roma");
        assertThat(result.getRooms()).isEqualTo(3);
    }

    @Test
    void findById_whenPropertyNotExists_shouldThrowResourceNotFoundException() {
        when(propertyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> propertyService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_whenOwnerExists_shouldSaveAndReturnPropertyResponse() {
        PropertyRequest request = new PropertyRequest();
        request.setOwnerId(1L);
        request.setName("Casa Milano");
        request.setAddress("Via Milano 1");
        request.setCity("Milano");
        request.setRooms(2);
        request.setPricePerMonth(new BigDecimal("900"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        PropertyResponse result = propertyService.create(request);

        assertThat(result).isNotNull();
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void create_whenOwnerNotExists_shouldThrowResourceNotFoundException() {
        PropertyRequest request = new PropertyRequest();
        request.setOwnerId(99L);
        request.setName("Casa Milano");
        request.setAddress("Via Milano 1");
        request.setCity("Milano");
        request.setRooms(2);
        request.setPricePerMonth(new BigDecimal("900"));

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> propertyService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void deleteById_whenPropertyExists_shouldSetActiveFalse() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        propertyService.deleteById(1L);

        assertThat(property.getActive()).isFalse();
        verify(propertyRepository).save(property);
    }

    @Test
    void deleteById_whenPropertyNotExists_shouldThrowResourceNotFoundException() {
        when(propertyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> propertyService.deleteById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(propertyRepository, never()).save(any());
    }
}