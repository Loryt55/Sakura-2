package com.lorenzo.rentalmanagement.rental.service;

import com.lorenzo.rentalmanagement.common.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import com.lorenzo.rentalmanagement.rental.dto.request.RentalRequest;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;
import com.lorenzo.rentalmanagement.rental.repository.RentalRepository;
import com.lorenzo.rentalmanagement.rental.service.impl.RentalServiceImpl;
import com.lorenzo.rentalmanagement.role.domain.entity.Role;
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
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private Property property;
    private User tenant;
    private Rental rental;

    @BeforeEach
    void setUp() {
        Role role = new Role("TENANT");

        tenant = new User.Builder()
                .firstName("Anna")
                .lastName("Bianchi")
                .email("anna@test.com")
                .phoneNumber("123456789")
                .password("hashedPassword")
                .role(role)
                .active(true)
                .createdAt(LocalDate.now())
                .build();

        property = new Property("Casa Roma", "Via Roma 1", "Roma", 3, new BigDecimal("1000"));
        property.setOwner(new User.Builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario@test.com")
                .phoneNumber("987654321")
                .password("hashedPassword")
                .role(new Role("OWNER"))
                .active(true)
                .createdAt(LocalDate.now())
                .build());
        property.setActive(true);
        property.setCreatedAt(LocalDate.now());

        rental = new Rental(
                property,
                tenant,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 1),
                new BigDecimal("2000"),
                true
        );
    }

    @Test
    void create_whenPropertyAndUserExist_shouldSaveAndReturnRentalResponse() {
        RentalRequest request = new RentalRequest();
        request.setPropertyId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 3, 1));

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(userRepository.findById(1L)).thenReturn(Optional.of(tenant));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        RentalResponse result = rentalService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getPropertyName()).isEqualTo("Casa Roma");
        assertThat(result.getUserFullName()).isEqualTo("Anna Bianchi");
        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    void create_whenPropertyNotExists_shouldThrowResourceNotFoundException() {
        RentalRequest request = new RentalRequest();
        request.setPropertyId(99L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 3, 1));

        when(propertyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rentalService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(rentalRepository, never()).save(any());
    }

    @Test
    void create_whenUserNotExists_shouldThrowResourceNotFoundException() {
        RentalRequest request = new RentalRequest();
        request.setPropertyId(1L);
        request.setUserId(99L);
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 3, 1));

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rentalService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(rentalRepository, never()).save(any());
    }

    @Test
    void create_shouldCalculateTotalPriceCorrectly_forExactMonths() {
        RentalRequest request = new RentalRequest();
        request.setPropertyId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 3, 1));

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(userRepository.findById(1L)).thenReturn(Optional.of(tenant));
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        RentalResponse result = rentalService.create(request);

        assertThat(result.getTotalPrice()).isEqualByComparingTo(new BigDecimal("1966.47"));
    }

    @Test
    void create_shouldRoundUpPartialMonths() {
        RentalRequest request = new RentalRequest();
        request.setPropertyId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 2, 15));

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(userRepository.findById(1L)).thenReturn(Optional.of(tenant));
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        RentalResponse result = rentalService.create(request);

        assertThat(result.getTotalPrice()).isEqualByComparingTo(new BigDecimal("1499.85"));
    }

    @Test
    void findAll_asAdmin_shouldReturnAllRentals() {
        when(rentalRepository.findAllByActiveTrue()).thenReturn(List.of(rental));

        List<RentalResponse> result = rentalService.findAll(1L, "ADMIN");

        assertThat(result).hasSize(1);
        verify(rentalRepository).findAllByActiveTrue();
    }

    @Test
    void findAll_asOwner_shouldReturnOnlyOwnerRentals() {
        when(rentalRepository.findAllByProperty_Owner_IdAndActiveTrue(1L))
                .thenReturn(List.of(rental));

        List<RentalResponse> result = rentalService.findAll(1L, "OWNER");

        assertThat(result).hasSize(1);
        verify(rentalRepository).findAllByProperty_Owner_IdAndActiveTrue(1L);
        verify(rentalRepository, never()).findAllByActiveTrue();
    }

    @Test
    void findAll_asTenant_shouldReturnOnlyTenantRentals() {
        when(rentalRepository.findAllByTenant_IdAndActiveTrue(1L))
                .thenReturn(List.of(rental));

        List<RentalResponse> result = rentalService.findAll(1L, "TENANT");

        assertThat(result).hasSize(1);
        verify(rentalRepository).findAllByTenant_IdAndActiveTrue(1L);
        verify(rentalRepository, never()).findAllByActiveTrue();
    }

    @Test
    void deleteById_whenRentalExists_shouldSetActiveFalse() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        rentalService.deleteById(1L);

        assertThat(rental.getActive()).isFalse();
        verify(rentalRepository).save(rental);
    }

    @Test
    void deleteById_whenRentalNotExists_shouldThrowResourceNotFoundException() {
        when(rentalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rentalService.deleteById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rentalRepository, never()).save(any());
    }
}