package com.shintadev.shop_dev_app.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController<T, ID> {

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public T findById(@PathVariable ID id) {
    // TODO Auto-generated method stub
    return null;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public T save(@RequestBody T entity) {
    // TODO Auto-generated method stub
    return null;
  }
}
