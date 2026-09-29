package org.example.web.controller;

import org.example.demo.api.dto.UserDto;
import org.example.grpc.service.UserInfo;
import org.example.web.rpc.UserRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRpcClient userRpcClient;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = userRpcClient.findUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/hello")
    public ResponseEntity<UserInfo> getUserInfo() {
        return ResponseEntity.ok(UserInfo.newBuilder().setUserId(3L).build());
    }
}
