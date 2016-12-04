package com.engsoft.poli.upe.quitandaverdeapp.models;

public class User {

    private int id;
    private String name;
    private String password;
    private String email;

    public User(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
