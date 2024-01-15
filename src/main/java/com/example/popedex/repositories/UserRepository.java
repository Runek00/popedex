package com.example.popedex.repositories;

import com.example.popedex.entities.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u.password = :password from user u where u.name = :name")
    boolean checkPassword(@Param("password") String password, @Param("name") String name);
}
