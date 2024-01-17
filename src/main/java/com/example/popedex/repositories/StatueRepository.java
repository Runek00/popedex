package com.example.popedex.repositories;

import com.example.popedex.entities.Statue;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatueRepository extends CrudRepository<Statue, Long> {

    @Query("select * from statue_info.statue where location_name like ('%' || :q || '%') or unveiling_date::text like ('%' || :q || '%') or exists::text like (:q || '%') limit :limit offset :offset")
    List<Statue> findAllPaginated(@Param("q") String q, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
