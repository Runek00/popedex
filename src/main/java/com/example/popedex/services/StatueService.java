package com.example.popedex.services;

import com.example.popedex.repositories.StatueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatueService {

    private final StatueRepository statueRepository;

    @Autowired
    public StatueService(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }

    public int countForUser(Long id) {
        return statueRepository.countForUser(id);
    }

}
