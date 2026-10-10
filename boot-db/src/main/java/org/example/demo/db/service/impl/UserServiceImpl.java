package org.example.demo.db.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.demo.api.dto.AddressDto;
import org.example.demo.api.dto.UserDto;
import org.example.demo.api.event.UserUpdateEvent;
import org.example.demo.db.entity.Address;
import org.example.demo.db.entity.User;
import org.example.demo.db.entity.UserAddress;
import org.example.demo.db.mapper.AddressMapper;
import org.example.demo.db.mapper.UserAddressMapper;
import org.example.demo.db.mapper.UserMapper;
import org.example.demo.db.service.UserService;
import org.example.demo.db.utils.AddressMapstruct;
import org.example.demo.db.utils.UserMapstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    final UserMapper userMapper;
    final AddressMapper addressMapper;
    final UserAddressMapper userAddressMapper;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserServiceImpl(UserMapper userMapper, AddressMapper addressMapper, UserAddressMapper userAddressMapper) {
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    public PageInfo<UserDto> queryUsers(Map<String, Object> param, int pageNum, int pageSize) {
        if (pageNum == 0) pageNum = 1;
        if (pageSize == 0) pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        List<UserDto> userDtoList = userMapper.queryUserByParam(param);
        return new PageInfo<>(userDtoList);
    }

    @Override
    public User saveUserWithAddress(UserDto userDto) {
        User user = UserMapstruct.INSTANCE.dtoToEntity(userDto);
        user = this.save(user); //保存用户信息
        List<Long> addressIdList = new ArrayList();
        for (AddressDto addressDto : userDto.getAddresses()) { //处理用户多地址
            Address address = AddressMapstruct.INSTANCE.dtoToEntity(addressDto);
            addressMapper.insert(address);
            //将addressId添加到集合中
            addressIdList.add(address.getAddressId());
        }
        //保存用户地址关系
        for (Long addressId : addressIdList) {
            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(user.getUserId());
            userAddress.setAddressId(addressId);
            userAddressMapper.insert(userAddress);
        }
        return user;
    }

    @Override
    public Integer updateUserById(UserDto userDto) {
        assert userDto.getUserId() != null;

        User user = userMapper.selectByPrimaryKey(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        Integer result = userMapper.updateByPrimaryKeySelective(user);
        logger.info("用户更新结果:{}", result);
        if (result > 0) {
            UserUpdateEvent userUpdateEvent = new UserUpdateEvent(
                    user.getUserId(), user.getUserName(), user.getEmail(), LocalDateTime.now());
            kafkaTemplate.send("user-events", user.getUserId().toString(), userUpdateEvent);
        }
        return result;
    }


}
