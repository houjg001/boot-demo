package org.example.demo.db.rpc;

import com.github.pagehelper.PageInfo;
import io.grpc.stub.StreamObserver;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
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
    public void selectUserById(UserIdReqProto idReq, StreamObserver<UserIdReqProto.Response> responseObserver) {
        User user = userService.findById(idReq.getUserId());

        UserProto userInfo = UserMapstruct.INSTANCE.toProto(user);
        UserIdReqProto.Response response = UserIdReqProto.Response.newBuilder().setUserProto(userInfo).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void queryUsers(org.example.grpc.service.UserQueryProto request,
                           io.grpc.stub.StreamObserver<UserQueryProto.Response> responseObserver) {
        Map<String, Object> param = new HashMap<>();
        if (StringUtils.hasText(request.getUserName())) {
            param.put("userName", request.getUserName());
        }
        if (StringUtils.hasText(request.getEmail())) {
            param.put("email", request.getEmail());
        }
        PageInfo<UserDto> userDtoPageInfo = userService.queryUsers(param, request.getPageNum(), request.getPageSize());
        List<UserProto> userInfoList = UserMapstruct.INSTANCE.dtoToProto(userDtoPageInfo.getList());

        PageProto pageInfoMsg = PageProto.newBuilder()
                .setPageNum(userDtoPageInfo.getPageNum())
                .setPageSize(userDtoPageInfo.getPageSize())
                .setPages(userDtoPageInfo.getPages())
                .setTotal(userDtoPageInfo.getTotal())
                .addAllUsers(userInfoList).build();
        UserQueryProto.Response response =  UserQueryProto.Response.newBuilder().setPageProto(pageInfoMsg).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
