package org.example.web.rpc;

import org.example.demo.api.dto.PageDto;
import org.example.demo.api.dto.UserDto;
import org.example.demo.api.utils.UserProtoMapstruct;
import org.example.grpc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserRpcClient {

    @Autowired
    private UserRpcServiceGrpc.UserRpcServiceBlockingStub blockingStub;

    public UserDto findUserById(Long userId) {
        UserProto userInfo = blockingStub.selectUserById(UserIdReqProto.newBuilder().setUserId(userId).build()).getUserProto();
        UserDto userDto = UserProtoMapstruct.INSTANCE.toDto(userInfo);
        return userDto;
    }

    public PageDto<UserDto> queryUsers(Map<String, Object> params) {
        PageProto pageInfoMsg = blockingStub.queryUsers(UserQueryProto.newBuilder()
                .setPageNum(Integer.parseInt(params.get("pageNum").toString()))
                .setPageSize(Integer.parseInt(params.get("pageSize").toString()))
                .build()).getPageProto();
        //转为dto
        List<UserDto> userDtoList = UserProtoMapstruct.INSTANCE.protoToDto(pageInfoMsg.getUsersList());
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
