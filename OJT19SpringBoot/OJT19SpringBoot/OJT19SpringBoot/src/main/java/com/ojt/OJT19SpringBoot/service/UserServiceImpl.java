package com.ojt.OJT19SpringBoot.service;

import com.ojt.OJT19SpringBoot.dto.UserDTO;
import com.ojt.OJT19SpringBoot.entity.UserEntity;
import com.ojt.OJT19SpringBoot.entity.UserRole;
import com.ojt.OJT19SpringBoot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDTO saveUser(UserDTO userdto) {
        UserEntity entity = mapper.map(userdto, UserEntity.class);
        entity.setPassword(passwordEncoder.encode(userdto.getPassword()));
        if (entity.getRole() == null) {
            entity.setRole(UserRole.USER); // Default
        }
        userRepo.save(entity);
        return mapper.map(entity, UserDTO.class);
    }
}
