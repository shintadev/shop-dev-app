package com.shintadev.shop_dev_app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.user.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
