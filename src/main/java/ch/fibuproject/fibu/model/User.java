package ch.fibuproject.fibu.model;

import java.util.Map;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Holds Userinformation as well as ThemeBlocks
 */

public class User {

    private String status;
    private String name;
    private String email;
    private String surname;
    private String username;
    private String password;
    private Map<Integer, ThemeBlock> themeBlockMap;

    public User() {
    }

    public Map<Integer, ThemeBlock> getThemeBlockMap() {
        return themeBlockMap;
    }

    public void setThemeBlockMap(Map<Integer, ThemeBlock> themeBlockMap) {
        this.themeBlockMap = themeBlockMap;
    }

    public void addThemeBlock(ThemeBlock themeBlock, Integer integer) {
        themeBlockMap.put(integer, themeBlock);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}