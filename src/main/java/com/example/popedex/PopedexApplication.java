package com.example.popedex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PopedexApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopedexApplication.class, args);
    }

    @Bean
//    @Profile("dev")
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
    @Query("select * from statue_info.statue limit :limit offset :offset")
    List<Statue> findAllPaginated(@Param("limit") Integer limit, @Param("offset") Integer offset);
}

interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u.password = :password from user u where u.name = :name")
    boolean checkPassword(@Param("password") String password, @Param("name") String name);
}

@Controller
@RequestMapping("/statues")
class StatueController {

    private final StatueRepository statueRepository;

    @Autowired
    public StatueController(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }


    @GetMapping("")
    String statues(@RequestParam(defaultValue = "0") Integer page, Model model) {
        int pageLength = 10;
        model.addAttribute("page", page);
        model.addAttribute("statues",
                statueRepository.findAllPaginated(pageLength, page * pageLength));
        if (page == 0) {
            return "index";
        } else {
            return "rows";
        }
    }

    @GetMapping("/{id}")
    String showStatueInfo(@PathVariable Long id, Model model) {
        Optional<Statue> statue = statueRepository.findById(id);
        if (statue.isPresent()) {
            model.addAttribute("statue", statue.get());
            return "show_statue";
        } else {
            return "no_statue";
        }
    }
}
