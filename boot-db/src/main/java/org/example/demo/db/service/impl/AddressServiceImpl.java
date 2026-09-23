package org.example.demo.db.service.impl;

import org.example.demo.db.entity.Address;
import org.example.demo.db.mapper.AddressMapper;
import org.example.demo.db.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressMapper, Address> implements AddressService {
}
