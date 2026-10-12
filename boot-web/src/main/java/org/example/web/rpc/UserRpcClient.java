package org.example.web.rpc;

import com.google.gson.Gson;
import org.example.demo.api.dto.PageDto;
import org.example.demo.api.dto.UserDto;
import org.example.demo.api.utils.UserProtoMapstruct;
import org.example.grpc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserRpcClient {

    private final static Logger logger = LoggerFactory.getLogger(UserRpcClient.class);

    @Autowired
    private UserRpcServiceGrpc.UserRpcServiceBlockingStub blockingStub;
    @Autowired
    Gson gson;

    public UserDto findUserById(Long userId) {
        UserProto userInfo = blockingStub.selectUserById(UserIdReqProto.newBuilder().setUserId(userId).build()).getUserProto();
        return UserProtoMapstruct.INSTANCE.protoToDto(userInfo);
    }

    public PageDto<UserDto> queryUsers(Map<String, Object> params) {
        logger.info("查询用户列表入参:{}", gson.toJson(params));
        PageProto pageInfoMsg = blockingStub.queryUsers(UserQueryProto.newBuilder()
                .setPageNum(Integer.parseInt(params.get("pageNum").toString()))
                .setPageSize(Integer.parseInt(params.get("pageSize").toString()))
                .build()).getPageProto();
        //转为dto
        List<UserDto> userDtoList = UserProtoMapstruct.INSTANCE.protoListToDtoList(pageInfoMsg.getUsersList());
        //拼装page对象
        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setPageNum(pageInfoMsg.getPageNum());
        pageDto.setPageSize(pageInfoMsg.getPageSize());
        pageDto.setPages(pageInfoMsg.getPages());
        pageDto.setTotal(pageInfoMsg.getTotal());
        pageDto.setList(userDtoList);
        return pageDto;
    }
}
