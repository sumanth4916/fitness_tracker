package com.fitness.userservice.repositery;

import com.fitness.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposertiry extends JpaRepository<User, String> {


}
