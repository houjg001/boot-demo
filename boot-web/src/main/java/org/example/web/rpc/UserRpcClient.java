package org.example.web.rpc;

import org.example.demo.api.dto.UserDto;
import org.example.demo.api.dto.utils.UserProtoMapstruct;
import org.example.grpc.service.UserIdReq;
import org.example.grpc.service.UserInfo;
import org.example.grpc.service.UserRpcServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRpcClient {

    @Autowired
    private UserRpcServiceGrpc.UserRpcServiceBlockingStub blockingStub;

    public UserDto findUserById(Long userId) {
        UserInfo userInfo = blockingStub.selectUserById(UserIdReq.newBuilder().setUserId(userId).build()).getUserInfo();
        UserDto userDto = UserProtoMapstruct.INSTANCE.toDto(userInfo);
        return userDto;
    }
}
