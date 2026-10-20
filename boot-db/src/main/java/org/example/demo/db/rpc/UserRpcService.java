package org.example.demo.db.rpc;

import com.github.pagehelper.PageInfo;
import io.grpc.stub.StreamObserver;
import org.example.demo.api.dto.UserDto;
import org.example.demo.db.entity.User;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.UserMapstruct;
import org.example.grpc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GrpcService
public class UserRpcService extends UserRpcServiceGrpc.UserRpcServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(UserRpcService.class);

    final UserService userService;

    public UserRpcService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void selectUserById(UserIdReqProto idReq, StreamObserver<UserIdReqProto.Response> responseObserver) {
        LOG.info("根据用户id查询入参:{},服务ip:{}", idReq.getUserId(), this.getServerAddress());
        UserDto user = userService.queryUserById(idReq.getUserId());

        UserProto userInfo = UserMapstruct.INSTANCE.dtoToProto(user);
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
        List<UserProto> userInfoList = UserMapstruct.INSTANCE.dtoListToProtoList(userDtoPageInfo.getList());

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

    private String getServerAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return localHost.getHostAddress();
    }
}
