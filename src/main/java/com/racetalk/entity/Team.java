package com.racetalk.entity;

public class Team {
    private int id;
    private String name;
    private String country;
    private int foundedYear;
    private String photo;

    public Team() {}

    public Team(int id, String name, String country, int foundedYear, String photo) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.foundedYear = foundedYear;
        this.photo = photo;
    }

    public Team(String name, String country, int foundedYear, String photo) {
        this.name = name;
        this.country = country;
        this.foundedYear = foundedYear;
        this.photo = photo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getFoundedYear() { return foundedYear; }
    public void setFoundedYear(int foundedYear) { this.foundedYear = foundedYear; }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
