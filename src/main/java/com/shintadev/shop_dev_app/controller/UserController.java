package com.shintadev.shop_dev_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.dto.user.UserCreateDto;
import com.shintadev.shop_dev_app.model.User;
import com.shintadev.shop_dev_app.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
@Slf4j
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

  @GetMapping("/all")
  @ResponseStatus(code = HttpStatus.OK)
  public Page<User> getAll(
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return userService.findAll(pageable);
  }

  @PutMapping("/{id}")
  public String putMethodName(@PathVariable String id, @RequestBody String entity) {
    // TODO: process PUT request

    return entity;
  }
}
