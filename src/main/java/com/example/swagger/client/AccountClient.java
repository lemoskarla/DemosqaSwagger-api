package com.example.swagger.client;

import com.example.swagger.controller.dto.TokenDTO;
import com.example.swagger.controller.dto.UserRequestDTO;
import com.example.swagger.controller.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

@org.springframework.cloud.openfeign.FeignClient(
    name = "accountClient",
    url = "https://demoqa.com/Account/v1")
public interface AccountClient {

  @PostMapping("/Authorized")
  Boolean authorized(@RequestBody UserRequestDTO user);

  @PostMapping("/GenerateToken")
  TokenDTO generateToken(@RequestBody UserRequestDTO user);

  @PostMapping(value = "/User", consumes = "application/json", produces = "application/json")
  UserResponseDTO createUser(@RequestBody UserRequestDTO user);

  @DeleteMapping("/User/{uuid}")
  void deleteUser(@PathVariable String uuid, @RequestHeader("Authorization") String token);

  @GetMapping("/User/{uuid}")
  UserResponseDTO getUser(@PathVariable String uuid, @RequestHeader("Authorization") String token);
}
