package com.example.demo1.service;

import com.example.demo1.entity.Reclamation;
import com.example.demo1.entity.Reponse;
import javafx.fxml.FXML;

import java.util.List;

public interface IserviceReponse {
    void ajouterReponse(Reponse reponse);

    void supprimerReclamation(int id);

    /*void modifierReponse(int id_rec, String description, String date_rec);*/

    public void modifierReponse(int id_rec, String description , String date_rec );

    @FXML
    void modifierReponse(Reponse reponse, int id_rec, String description, String date_rec);

    void supprimerReponse(int id);

    List<Reclamation> recupererReclamations();

    List<Reponse> recupererReponse();
}
