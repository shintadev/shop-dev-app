package com.shintadev.shop_dev_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.dto.user.UserCreateDto;
import com.shintadev.shop_dev_app.model.User;
import com.shintadev.shop_dev_app.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public String add(@RequestBody UserCreateDto user) {
    return userService.create(user).toString();
  }

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public User getById(@PathVariable String id) {
    return userService.findOne(Long.parseLong(id));
  }

}
