package com.example.popedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PopedexApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopedexApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public DataSource dataSourceDev() {
        var dsb = DataSourceBuilder.create();
        dsb.driverClassName("org.postgresql.Driver");
        dsb.url("jdbc:postgresql://localhost:5432/popedex");
        dsb.username("postgres");
        dsb.password("popedex");
        return dsb.build();
    }
}

record User(@Id Long id, String name, String email, LocalDateTime registerTime, boolean active) {
}

record Statue(@Id Long id, Point location, String locationName, LocalDate unveilingDate, boolean exists, User addedBy,
              boolean active) {
}

interface StatueRepository extends CrudRepository<Statue, Long> {
    @Query("select * from statue limit :limit offset :offset")
    List<Statue> findAllPaginated(@Param("limit") Integer limit, @Param("offset") Integer offset);
}

interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u.password = :password from user u where u.name = :name")
    boolean checkPassword(@Param("password") String password, @Param("name") String name);
}