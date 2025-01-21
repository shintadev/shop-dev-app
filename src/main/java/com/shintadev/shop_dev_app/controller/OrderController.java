package com.shintadev.shop_dev_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.dto.user.UserCreateDto;
import com.shintadev.shop_dev_app.model.Order;
import com.shintadev.shop_dev_app.service.OrderService;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public String add(@RequestBody UserCreateDto user) {
    // TODO: process POST request

    return "";
  }

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public Order getById(@PathVariable String id) {
    // TODO: process GET request

    return new Order();
  }

  // @GetMapping("/all")
  // @ResponseStatus(code = HttpStatus.OK)
  // public Page<User> getAll(
  // @RequestParam int page,
  // @RequestParam(defaultValue = "10") int size,
  // @RequestParam(defaultValue = "id") String sort,
  // @RequestParam(defaultValue = "desc") String order) {
  // int pageNumber = Math.max(0, page - 1);
  // Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
  // : Sort.Direction.DESC;
  // Sort sortBy = Sort.by(direction, sort);
  // Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
  // return userService.findAll(pageable);
  // }

  @PatchMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public String update(@PathVariable String id, @RequestBody String entity) {
    // TODO: process PATCH request

    return entity;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public String delete(@PathVariable String id) {
    // TODO: process DELETE request

    return id;
  }
}
