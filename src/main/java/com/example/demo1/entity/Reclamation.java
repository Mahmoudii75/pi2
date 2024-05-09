package com.example.demo1.entity;
import java.awt.image.BufferedImage;



import java.time.LocalDate;

public class Reclamation {
    private int id_reclamation;

    private LocalDate date_rec;
    private String description;
    private String type;


    // Constructors
    public Reclamation() {
    }

    public Reclamation(int id_reclamation,LocalDate date_rec, String description, String type) {
        this.id_reclamation = id_reclamation;
        this.date_rec = date_rec;
        this.description = description;
        this.type = type;
        //this.pdfFile = pdfFile;
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
    public LocalDate getDate_rec() {
        return date_rec;
    }

    public void setDate_rec(LocalDate date_rec) {
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    // toString() method
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id_reclamation +
                ", date_rec=" + date_rec +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public void setDateRec(String text) {
    }

    public int getIdRec() {
        return id_reclamation;
    }

    public LocalDate getDateRec() {
        return date_rec;
    }


    // Method to set PDF file content from byte array
    //public void setPdfFileFromByteArray(byte[] pdfFileContent) {
      //  this.pdfFile = pdfFileContent;
   // }
}
