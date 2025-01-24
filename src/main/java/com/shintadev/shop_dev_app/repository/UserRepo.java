package com.shintadev.shop_dev_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  User findByEmail(String email);

  User findBySlug(String slug);
}
