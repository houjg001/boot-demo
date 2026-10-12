package org.example.web.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
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
    @Autowired
    MeterRegistry meterRegistry;
    @Autowired
    ObservationRegistry observationRegistry;

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
        PageDto<UserDto> userDtoList =
                Observation.createNotStarted("user.pageQuery", observationRegistry)
                .contextualName("用户列表查询")
                .lowCardinalityKeyValue("user.page", "pageQuery")
                .observe(()->
                        Timer.builder("user.pageQuery.time")
                                .description("用户分页查询花费时间")
                                .tag("page", "pageQuery")
                                .register(meterRegistry)
                                .record(() -> pageQueryUsers(params)));
        return ResponseEntity.ok(userDtoList);
    }

    private PageDto<UserDto> pageQueryUsers(Map<String, Object> params) {
        PageDto<UserDto> userDtoList = userRpcClient.queryUsers(params);

        meterRegistry.counter("user.pageQuery.count", "pagequery", "pageQuery").increment();
        return userDtoList;
    }
}
