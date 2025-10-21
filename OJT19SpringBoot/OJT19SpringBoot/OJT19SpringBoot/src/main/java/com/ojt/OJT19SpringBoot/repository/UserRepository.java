package com.ojt.OJT19SpringBoot.repository;

import com.ojt.OJT19SpringBoot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public List<UserEntity> findByUsername(String username);
}
