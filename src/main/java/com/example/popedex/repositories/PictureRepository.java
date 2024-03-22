package com.example.popedex.repositories;

import com.example.popedex.entities.Picture;
import com.example.popedex.services.LongByteArrMapRowMapper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PictureRepository extends CrudRepository<Picture, Long> {
    @Query("select picture from statue_info.picture where statue_id = :statueId order by random() limit 1")
    byte[] randomPictureForStatue(@Param("statueId") Long statueId);

    @Query(value = "select distinct on (s.id), p.picture from statue_info.statue s join statue_info.picture p on s.id = p.statue_id order by random()",
    rowMapperClass = LongByteArrMapRowMapper.class)
    Map<Long, byte[]> randomPictureForEach(List<Long> ids);
}