package com.example.popedex.services;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTempPictureCache {

    private final ConcurrentHashMap<Long, byte[]> picMap = new ConcurrentHashMap<>();

    byte[] getPic(Long id) {
        return picMap.getOrDefault(id, null);
    }

    void addPics(Map<Long, byte[]> newMap) {
        picMap.putAll(newMap);
    }

}
