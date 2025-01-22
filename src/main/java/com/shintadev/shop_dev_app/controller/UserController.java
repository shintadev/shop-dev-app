package com.shintadev.shop_dev_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.payload.user.UserDto;
import com.shintadev.shop_dev_app.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> add(@RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable String id) {
    return new ResponseEntity<>(userService.findOne(Long.parseLong(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<UserDto>> getAll(
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.update(Long.parseLong(id), userDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    userService.delete(Long.parseLong(id));

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @GetMapping("/{email}")
  public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
    return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
  }

  @GetMapping("/{slug}")
  public ResponseEntity<UserDto> getBySlug(@PathVariable String slug) {
    // TODO: process GET request
    return new ResponseEntity<>(HttpStatus.OK);
    // return new ResponseEntity<>(userService.findBySlug(slug), HttpStatus.OK);
  }
}
