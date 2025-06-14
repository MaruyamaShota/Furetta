package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    private Integer age;

    private String gender;

    @Column(name = "x_id")
    private String xId;

    @Column(name = "discord_id")
    private String discordId;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "favorite_games")
    private String favoriteGames;

    @Column(name = "platform")
    private String platform;

    @Column(name = "play_style")
    private String playStyle;

    @Column(name = "gaming_experience")
    private String gamingExperience;

    @Column(name = "vc_available")
    private Boolean vcAvailable;

    @Column(name = "game_preferences")
    private String gamePreferences;

    @Column(name = "relationship_preferences")
    private String relationshipPreferences;

    @Column(name = "self_introduction", length = 1000)
    private String selfIntroduction;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getXId() {
        return xId;
    }

    public void setXId(String xId) {
        this.xId = xId;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFavoriteGames() {
        return favoriteGames;
    }

    public void setFavoriteGames(String favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlayStyle() {
        return playStyle;
    }

    public void setPlayStyle(String playStyle) {
        this.playStyle = playStyle;
    }

    public String getGamingExperience() {
        return gamingExperience;
    }

    public void setGamingExperience(String gamingExperience) {
        this.gamingExperience = gamingExperience;
    }

    public Boolean getVcAvailable() {
        return vcAvailable;
    }

    public void setVcAvailable(Boolean vcAvailable) {
        this.vcAvailable = vcAvailable;
    }

    public String getGamePreferences() {
        return gamePreferences;
    }

    public void setGamePreferences(String gamePreferences) {
        this.gamePreferences = gamePreferences;
    }

    public String getRelationshipPreferences() {
        return relationshipPreferences;
    }

    public void setRelationshipPreferences(String relationshipPreferences) {
        this.relationshipPreferences = relationshipPreferences;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
} 