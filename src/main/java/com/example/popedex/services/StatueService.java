package com.example.popedex.services;

import com.example.popedex.entities.Statue;
import com.example.popedex.entities.User;
import com.example.popedex.repositories.StatueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StatueService {

    private final StatueRepository statueRepository;
    private final UserService userService;
    private final PictureService pictureService;

    @Autowired
    public StatueService(StatueRepository statueRepository, UserService userService, PictureService pictureService) {
        this.statueRepository = statueRepository;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    public List<Statue> findAllForUser(Principal principal, String q, int limit, int offset) {
        User user = userService.getUserFromPrincipal(principal);
        return statueRepository.findAllForUserPaginated(user.id(), q, limit, offset);
    }

    public int countForUser(Long id) {
        return statueRepository.countForUser(id);
    }

    public void save(Statue statue) {
        statueRepository.save(statue);
    }

    public Optional<Statue> findById(Long id) {
        return statueRepository.findById(id);
    }

    public record StatueWithPicture(Long id, String locationName, LocalDate unveilingDate, byte[] picture){
        StatueWithPicture(Statue statue, byte[] picture){
            this(statue.id(), statue.locationName(), statue.unveilingDate(), picture);
        }
    }
    public List<StatueWithPicture> findAllPaginated(String q, int limit, int offset) {
        List<Statue> statues = statueRepository.findAllPaginated(q, limit, offset);
        Map<Long, byte[]> pictures = pictureService.getRandomPictureForStatueSet(statues);
        return statues.stream()
                .map(statue -> new StatueWithPicture(statue, pictures.get(statue.id())))
                .toList();

    }
}
