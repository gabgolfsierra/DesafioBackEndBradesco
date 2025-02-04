package com.bradesco.DesafioBackEnd.Repositories;

import com.bradesco.DesafioBackEnd.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
