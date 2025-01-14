package com.shintadev.shop_dev_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shintadev.shop_dev_app.model.User;

public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
