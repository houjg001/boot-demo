package org.example.demo.db.rpc;

import com.github.pagehelper.PageInfo;
import io.grpc.stub.StreamObserver;
import org.example.demo.api.dto.AddressDto;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.AddressMapstruct;
import org.example.demo.db.utils.UserMapstruct;
import org.example.grpc.service.*;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GrpcService
public class UserRpcService extends UserRpcServiceGrpc.UserRpcServiceImplBase {

    final UserService userService;

    public UserRpcService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void selectUserById(UserIdReq idReq, StreamObserver<UserIdReq.Response> responseObserver) {
        User user = userService.findById(idReq.getUserId());

        UserInfo userInfo = UserMapstruct.INSTANCE.toProto(user);
        UserIdReq.Response response = UserIdReq.Response.newBuilder().setUserInfo(userInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void queryUsers(org.example.grpc.service.UserQueryParam request,
                           io.grpc.stub.StreamObserver<org.example.grpc.service.UserQueryParam.Response> responseObserver) {
        Map<String, Object> param = new HashMap<>();
        if (StringUtils.hasText(request.getUserName())) {
            param.put("userName", request.getUserName());
        }
        if (StringUtils.hasText(request.getEmail())) {
            param.put("email", request.getEmail());
        }
        if (StringUtils.hasText(request.getAddr())) {
            param.put("addr", request.getAddr());
        }
        PageInfo<UserDto> userDtoPageInfo = userService.queryUsers(param, request.getPageNum(), request.getPageSize());
        List<UserInfo> userInfoList = new ArrayList<>();
        for (UserDto userDto : userDtoPageInfo.getList()) {
            UserInfo userInfo = UserMapstruct.INSTANCE.dtoToProto(userDto);
            userInfoList.add(userInfo);
        }
        UserQueryParam.Response response = UserQueryParam.Response.newBuilder().addAllUserInfo(userInfoList).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
