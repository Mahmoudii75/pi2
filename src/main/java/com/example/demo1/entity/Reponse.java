package com.example.demo1.entity;

import java.util.Date;

public class Reponse {
    private int id_reclamation;

    private String date_rec;
    private String description;


    // Constructors
    public Reponse() {
    }

    public Reponse(int id_reclamation,String date_rec, String description) {
        this.id_reclamation = id_reclamation;
        this.date_rec = date_rec;
        this.description = description;

    }


    // Getters and Setters for id
    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    // Getters and Setters for user_id


    // Getters and Setters for date_rec
    public String getDate_rec() {
        return date_rec;
    }

    public void setDate_rec(String date_rec) {
        this.date_rec = date_rec;
    }

    // Getters and Setters for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters and Setters for type


    // toString() method
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id_reclamation +
                ", date_rec=" + date_rec +
                ", description='" + description + '\'' +

                '}';
    }

    public void setDateRec(String text) {
    }

    public int getIdRec() {
        return id_reclamation;
    }

    public String getDateRec() {
        return date_rec;
    }

    public void setId_reponse(int var3) {
    }

    public void setDescription1(String description) {
    }

    public void setDate_reclamation(String dateRec) {
    }
}
