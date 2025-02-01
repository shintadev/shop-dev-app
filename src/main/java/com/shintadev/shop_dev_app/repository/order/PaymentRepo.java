package com.shintadev.shop_dev_app.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.order.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
