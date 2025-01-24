package com.shintadev.shop_dev_app.payload.user;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public abstract class UserDto {
  private String firstName;
  private String lastName;
  private String email;
  private String address;
  private String phone;
  private String slug;
}
