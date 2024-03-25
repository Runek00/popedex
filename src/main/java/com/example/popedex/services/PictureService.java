package com.example.popedex.services;

import com.example.popedex.entities.Statue;
import com.example.popedex.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final InMemoryTempPictureCache picMap;

    @Autowired
    public PictureService(PictureRepository pictureRepository, InMemoryTempPictureCache picMap) {
        this.pictureRepository = pictureRepository;
        this.picMap = picMap;
    }

    public void getRandomPictureForStatueSet(Collection<Statue> statues) {
        List<Long> ids = statues.stream().map(Statue::id).toList();
        picMap.addPics(pictureRepository.randomPictureForEach(ids));
    }

    public byte[] getPic(Long id) {
        return picMap.getPic(id);
    }

}