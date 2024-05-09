package com.example.demo1.service;


import com.example.demo1.entity.Reclamation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IserviceReclamation {
    void ajouterReclamation(Reclamation reclamation) throws SQLException;
    void modifierReclamation(int id_rec, String description, String date_rec, String type);
    void supprimerReclamation(int id_rec);
    List<Reclamation> recupererReclamations();
}
