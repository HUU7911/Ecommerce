package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.contract.constant.ReservationStatus;
import com.huumod.ecommerce.inventory.entity.PhoneReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneReservationRepository extends JpaRepository<PhoneReservation, String> {

    Optional<PhoneReservation> findByOrderIdAndInventoryId(String orderId, String inventoryId);
    List<PhoneReservation> findAllByOrderId(String orderId);
    List<PhoneReservation> findAllByStatusAndExpiresAtBefore(ReservationStatus status, Instant expiresAt);
}
