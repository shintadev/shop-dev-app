package com.shintadev.shop_dev_app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.payload.user.UserDto;
import com.shintadev.shop_dev_app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
    return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
  }

  // @GetMapping("/{id}")
  // public ResponseEntity<UserDto> getById(@PathVariable String id) {
  // return new ResponseEntity<>(userService.findOne(Long.parseLong(id)),
  // HttpStatus.OK);
  // }

  @GetMapping
  public ResponseEntity<Page<UserResponse>> getAll(
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

  // @PatchMapping("/{id}")
  // public ResponseEntity<UserResponse> update(@PathVariable String id,
  // @RequestBody UserDto userDto) {
  // return new ResponseEntity<>(userService.update(Long.parseLong(id), userDto),
  // HttpStatus.OK);
  // }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    userService.delete(Long.parseLong(id));

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  // @GetMapping("/{email}")
  // public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
  // return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
  // }

  @GetMapping("/{slug}")
  public ResponseEntity<UserResponse> getBySlug(@PathVariable String slug) {
    return new ResponseEntity<>(userService.findBySlug(slug), HttpStatus.OK);
  }

  // @PostMapping("/address")
  // public ResponseEntity<UserResponse> getByAddress(@PathVariable AddressRequest
  // request) {
  // return new ResponseEntity<>(userService.addAddress(request), HttpStatus.OK);
  // }
}
