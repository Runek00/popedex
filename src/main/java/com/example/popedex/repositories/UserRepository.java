package com.example.popedex.repositories;

import com.example.popedex.entities.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select count(*) = 0 from users u where u.username = :username")
    boolean checkLoginUnique(@Param("username") String username);

    @Modifying
    @Query("update users set password = :password where id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}
