package org.example.demo.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo.db.entity.Address;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}