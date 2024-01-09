package com.example.popedex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/statue")
class StatueController {

    private final StatueRepository statueRepository;

    @Autowired
    public StatueController(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }


    @GetMapping("/")
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
