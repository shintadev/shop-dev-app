package com.shintadev.shop_dev_app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.user.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.user.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.user.UserResponse;
import com.shintadev.shop_dev_app.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
    return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getOne(@Valid @PathVariable String id) {
    return new ResponseEntity<>(userService.findOne(Long.parseLong(id)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
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

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable String id,
      @Valid @RequestBody UserProfileUpdateRequest request) {
    return new ResponseEntity<>(userService.update(Long.parseLong(id), request),
        HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    userService.delete(Long.parseLong(id));

    return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
  }

  @GetMapping("/profile")
  public ResponseEntity<UserResponse> profile() {
    return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
  }

  @PutMapping("/profile")
  public ResponseEntity<UserResponse> updateProfile(
      @Valid @RequestBody UserProfileUpdateRequest request) {
    return new ResponseEntity<>(userService.updateCurrentUser(request), HttpStatus.OK);
  }

  @GetMapping("/{userId}/addresses")
  public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.findUserAddresses(userId), HttpStatus.OK);
  }

  @PutMapping("/{userId}/addresses/{addressId}")
  public ResponseEntity<UserResponse> updateAddress(@PathVariable Long userId,
      @PathVariable Long addressId, @Valid @RequestBody AddressRequest request) {
    return new ResponseEntity<>(userService.updateUserAddress(userId, addressId, request),
        HttpStatus.OK);
  }
}
