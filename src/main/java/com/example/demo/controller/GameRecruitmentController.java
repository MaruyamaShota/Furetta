package com.example.demo.controller;

import com.example.demo.model.GameRecruitment;
import com.example.demo.model.User;
import com.example.demo.repository.GameRecruitmentRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recruitment")
public class GameRecruitmentController {

    @Autowired
    private GameRecruitmentRepository gameRecruitmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/new")
    public String showRecruitmentForm(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "recruitment-form";
    }

    @PostMapping("/new")
    public String createRecruitment(@RequestParam String gameName,
                                  @RequestParam Integer requiredPlayers,
                                  @RequestParam(required = false) Boolean vcRequired,
                                  @RequestParam String startTime,
                                  @RequestParam String endTime,
                                  @RequestParam String platform,
                                  @RequestParam String description,
                                  HttpSession session,
                                  Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        GameRecruitment recruitment = new GameRecruitment();
        recruitment.setUser(user);
        recruitment.setGameName(gameName);
        recruitment.setRequiredPlayers(requiredPlayers);
        recruitment.setVcRequired(vcRequired);
        recruitment.setStartTime(startTime);
        recruitment.setEndTime(endTime);
        recruitment.setPlatform(platform);
        recruitment.setDescription(description);

        gameRecruitmentRepository.save(recruitment);

        return "redirect:/";
    }
} 