package com.example.swagger.services;

import com.example.swagger.client.AccountClient;
import com.example.swagger.controller.dto.TokenDTO;
import com.example.swagger.controller.dto.UserRequestDTO;
import com.example.swagger.controller.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final AccountClient accountClient;

    public Boolean authorized(UserRequestDTO userRequestDTO) {
        return accountClient.authorized(userRequestDTO);
    }

    public TokenDTO generateToken(UserRequestDTO userRequestDTO) {
        return accountClient.generateToken(userRequestDTO);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        return accountClient.createUser(userRequestDTO) ;
    }

    public void deleteUser(String uuid, String token) {
        token = getToken(token);
        accountClient.deleteUser(uuid, token);
    }

    public UserResponseDTO getUser(String uuid, String token) {
        token = getToken(token);
       return accountClient.getUser(uuid, token);
    }

    private String getToken(String token) {
        token = "Bearer " + token;
        return token;
    }
}
