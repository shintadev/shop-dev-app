package com.shintadev.shop_dev_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  // private final OrderService orderService;

  // OrderController(OrderService orderService) {
  // this.orderService = orderService;
  // }

  // @PostMapping
  // public ResponseEntity<OrderDto> add(@RequestBody OrderDto orderDto) {
  // return new ResponseEntity<>(orderService.create(orderDto),
  // HttpStatus.CREATED);
  // }

  // @GetMapping("/{id}")
  // public ResponseEntity<OrderDto> getById(@PathVariable String id) {
  // return new ResponseEntity<>(orderService.findOne(Long.parseLong(id)),
  // HttpStatus.OK);
  // }

  // @GetMapping
  // public ResponseEntity<Page<OrderDto>> getAll(
  // @RequestParam int page,
  // @RequestParam(defaultValue = "10") int size,
  // @RequestParam(defaultValue = "id") String sort,
  // @RequestParam(defaultValue = "desc") String order) {
  // int pageNumber = Math.max(0, page - 1);
  // Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
  // : Sort.Direction.DESC;
  // Sort sortBy = Sort.by(direction, sort);
  // Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
  // return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
  // }

  // @PatchMapping("/{id}")
  // public ResponseEntity<OrderDto> update(@PathVariable String id, @RequestBody
  // OrderDto orderDto) {
  // return new ResponseEntity<>(orderService.update(Long.parseLong(id),
  // orderDto), HttpStatus.OK);
  // }

  // @DeleteMapping("/{id}")
  // public ResponseEntity<String> delete(@PathVariable String id) {
  // orderService.delete(Long.parseLong(id));

  // return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
  // }

  // @GetMapping("/customer/{userId}")
  // public ResponseEntity<Page<OrderDto>> getByUserId(
  // @PathVariable String userId,
  // @RequestParam int page,
  // @RequestParam(defaultValue = "10") int size,
  // @RequestParam(defaultValue = "id") String sort,
  // @RequestParam(defaultValue = "desc") String order) {
  // int pageNumber = Math.max(0, page - 1);
  // Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
  // : Sort.Direction.DESC;
  // Sort sortBy = Sort.by(direction, sort);
  // Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
  // return new ResponseEntity<>(orderService.findByUserId(Long.parseLong(userId),
  // pageable), HttpStatus.OK);
  // }
}
