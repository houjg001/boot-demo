package org.example.web.controller;

import org.example.demo.api.dto.PageDto;
import org.example.demo.api.dto.UserDto;
import org.example.web.rpc.UserRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 分页查询用户
     * @param params
     * @return
     */
    @GetMapping("/queryUsers")
    public ResponseEntity<PageDto<UserDto>> queryUser(@RequestParam Map<String, Object> params) {
        PageDto<UserDto> userDtoList = userRpcClient.queryUsers(params);
        return ResponseEntity.ok(userDtoList);
    }
}
