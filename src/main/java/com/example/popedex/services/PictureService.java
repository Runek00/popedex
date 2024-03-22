package com.example.popedex.services;

import com.example.popedex.entities.Statue;
import com.example.popedex.entities.User;
import com.example.popedex.repositories.PictureRepository;
import com.example.popedex.repositories.StatueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public byte[] getRandomPicureForStatue(Long statueId) {
        return pictureRepository.randomPictureForStatue(statueId);
    }

    public Map<Long, byte[]> getRandomPictureForStatueSet(Collection<Statue> statues) {
        List<Long> ids = statues.stream().map(Statue::id).toList();
        return pictureRepository.randomPictureForEach(ids);
    }

}