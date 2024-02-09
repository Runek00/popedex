package com.example.popedex.controllers;

import com.example.popedex.entities.Statue;
import com.example.popedex.repositories.StatueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/statues")
class StatueController {

    private final StatueRepository statueRepository;

    @Autowired
    public StatueController(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }


    @GetMapping("/")
    String statues(@RequestParam(defaultValue = "0") Integer page,
                   @RequestParam(defaultValue = "") String q,
                   @RequestHeader(name = "HX-Trigger", required = false) String trigger,
                   Model model) {
        int pageLength = 10;
        model.addAttribute("page", page);
        model.addAttribute("statues",
                statueRepository.findAllPaginated(q, pageLength, page * pageLength));
        if (page == 0 && !"search".equals(trigger)) {
            return "index";
        } else {
            return "statue_rows";
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

    @GetMapping("/new")
    String newStatueInput(Model model) {
        model.addAttribute("statue", new Statue(null,  null, null, null, null, null));
        return "new_statue";
    }

    @PostMapping("/new")
    String addNewStatue(@RequestParam String locationName,
                        @RequestParam LocalDate unveilingDate,
                        @RequestParam(required = false, defaultValue = "false") Boolean exists) {
        Statue statue = new Statue(null, locationName, unveilingDate, exists, null, true);
        statueRepository.save(statue);
        return "redirect:/statues";
    }
}
