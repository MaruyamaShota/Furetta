package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.GameRecruitment;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.GameRecruitmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRecruitmentRepository gameRecruitmentRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userRepository.findAll();
        List<GameRecruitment> recruitments = gameRecruitmentRepository.findAllByOrderByCreatedAtDesc();
        List<String> platforms = List.of("PC", "PlayStation", "Xbox", "Nintendo Switch");
        model.addAttribute("users", users);
        model.addAttribute("recruitments", recruitments);
        model.addAttribute("platforms", platforms);
        return "index";
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(@RequestParam(required = false) String gameName,
                        @RequestParam(required = false) String platform,
                        Model model) {
        System.out.println("Search parameters - Game: " + gameName + ", Platform: " + platform);
        
        List<User> users = userRepository.findAll();
        List<String> platforms = List.of("PC", "PlayStation", "Xbox", "Nintendo Switch");
        List<GameRecruitment> recruitments;
        
        if (gameName != null && !gameName.isEmpty() && platform != null && !platform.isEmpty()) {
            recruitments = gameRecruitmentRepository.findByGameNameAndPlatformOrderByCreatedAtDesc(gameName, platform);
            System.out.println("Searching by both game and platform");
        } else if (gameName != null && !gameName.isEmpty()) {
            recruitments = gameRecruitmentRepository.findByGameNameOrderByCreatedAtDesc(gameName);
            System.out.println("Searching by game only");
        } else if (platform != null && !platform.isEmpty()) {
            recruitments = gameRecruitmentRepository.findByPlatformOrderByCreatedAtDesc(platform);
            System.out.println("Searching by platform only");
        } else {
            recruitments = gameRecruitmentRepository.findAllByOrderByCreatedAtDesc();
            System.out.println("Showing all recruitments");
        }
        
        System.out.println("Found " + recruitments.size() + " recruitments");
        
        model.addAttribute("users", users);
        model.addAttribute("recruitments", recruitments);
        model.addAttribute("platforms", platforms);
        model.addAttribute("selectedGameName", gameName);
        model.addAttribute("selectedPlatform", platform);
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String username,
                             Model model) {
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "このメールアドレスは既に使用されています");
            return "register";
        }

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "このユーザー名は既に使用されています");
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        User user = userRepository.findByEmail(email);
        
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("username", user.getUsername());
            return "redirect:/profile";
        }

        model.addAttribute("error", "メールアドレスまたはパスワードが正しくありません");
        return "login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("session", session);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam String username,
                              @RequestParam(required = false) String password,
                              @RequestParam(required = false) Integer age,
                              @RequestParam(required = false) String gender,
                              @RequestParam(required = false) String xId,
                              @RequestParam(required = false) String discordId,
                              @RequestParam(required = false) String startTime,
                              @RequestParam(required = false) String endTime,
                              @RequestParam(required = false) String favoriteGames,
                              @RequestParam(required = false) String platform,
                              @RequestParam(required = false) String playStyle,
                              @RequestParam(required = false) String gamingExperience,
                              @RequestParam(required = false) Boolean vcAvailable,
                              @RequestParam(required = false) String gamePreferences,
                              @RequestParam(required = false) String relationshipPreferences,
                              @RequestParam(required = false) String selfIntroduction,
                              HttpSession session,
                              Model model) {
        try {
            if (session.getAttribute("userId") == null) {
                return "redirect:/login";
            }

            Long userId = (Long) session.getAttribute("userId");
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return "redirect:/login";
            }

            // ユーザー名の重複チェック
            User existingUser = userRepository.findByUsername(username);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                model.addAttribute("error", "このユーザー名は既に使用されています");
                model.addAttribute("user", user);
                return "profile-edit";
            }

            user.setUsername(username);
            user.setAge(age);
            user.setGender(gender);
            user.setXId(xId);
            user.setDiscordId(discordId);
            user.setStartTime(startTime);
            user.setEndTime(endTime);
            user.setFavoriteGames(favoriteGames);
            user.setPlatform(platform);
            user.setPlayStyle(playStyle);
            user.setGamingExperience(gamingExperience);
            user.setVcAvailable(vcAvailable);
            user.setGamePreferences(gamePreferences);
            user.setRelationshipPreferences(relationshipPreferences);
            user.setSelfIntroduction(selfIntroduction);
            
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }
            userRepository.save(user);

            session.setAttribute("username", username);

            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "プロフィールの更新中にエラーが発生しました: " + e.getMessage());
            return "profile-edit";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable Long id, Model model, HttpSession session) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("session", session);
        return "profile";
    }
} 