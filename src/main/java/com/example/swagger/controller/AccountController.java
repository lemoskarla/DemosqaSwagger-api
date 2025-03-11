package com.example.swagger.controller;

import com.example.swagger.controller.dto.TokenDTO;
import com.example.swagger.controller.dto.UserRequestDTO;
import com.example.swagger.services.AccountService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Account/v1")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

  private final AccountService accountService;

  @PostMapping("/authorized")
  public ResponseEntity<Boolean> authorized(@RequestBody UserRequestDTO userRequestDTO) {
    return ResponseEntity.ok(accountService.authorized(userRequestDTO));
  }

  @PostMapping("/generate-token")
  public ResponseEntity<TokenDTO> generateToken(@RequestBody UserRequestDTO userRequestDTO) {
    return ResponseEntity.ok(accountService.generateToken(userRequestDTO));
  }

  @PostMapping(value = "/User", produces = "application/json")
  public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    try {
      return ResponseEntity.ok(accountService.createUser(userRequestDTO));
    } catch (FeignException e) {
      if (HttpStatus.BAD_REQUEST.value() == e.status()) {
        String responseBody =
            e.responseBody().map(buffer -> new String(buffer.array())).orElse("Resposta vazia");
        return ResponseEntity.badRequest().body(responseBody);
      } else if (HttpStatus.NOT_ACCEPTABLE.value() == e.status()) {
        return ResponseEntity.status(406).body("User already exists");
      }

      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteUser(
      @Parameter(hidden = true) @RequestHeader(value = "Authorization") String token,
      @PathVariable String uuid) {
    accountService.deleteUser(uuid, token);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/{uuid}", produces = "application/json")
  public ResponseEntity<?> getUser(
          @Parameter(hidden = true) @RequestHeader(value = "Authorization") String token, @PathVariable String uuid) {

    try {
      return ResponseEntity.ok(ResponseEntity.ok(accountService.getUser(uuid, token)));
    } catch (FeignException e) {
      String responseBody =
              e.responseBody().map(buffer -> new String(buffer.array())).orElse("Resposta vazia");
      if (HttpStatus.UNAUTHORIZED.value() == e.status()) {
        return ResponseEntity.status(401).body(responseBody);
      } else if (HttpStatus.BAD_REQUEST.value() == e.status()) {
        return ResponseEntity.badRequest().body(responseBody);
      } else if (HttpStatus.NOT_ACCEPTABLE.value() == e.status()) {
        return ResponseEntity.status(406).body("User already exists");
      }
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
