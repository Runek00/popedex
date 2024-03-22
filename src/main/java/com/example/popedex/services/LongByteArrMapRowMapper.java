package com.example.popedex.services;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LongByteArrMapRowMapper implements RowMapper<Map<Long, byte[]>> {
    @Override
    public Map<Long, byte[]> mapRow(ResultSet rs, int rowNum) throws SQLException {
        HashMap<Long, byte[]> out = new HashMap<>();
        while(rs.next()) {
            long key = rs.getLong(1);
            byte[] value = rs.getBytes(2);
            out.put(key, value);
        }
        return out;
    }
}
