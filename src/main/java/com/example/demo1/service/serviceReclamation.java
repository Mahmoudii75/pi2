package com.example.demo1.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.example.demo1.util.MyConnection;

import com.example.demo1.entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter; // Import DateTimeFormatter
public class serviceReclamation implements IserviceReclamation {
    Connection cnxfl;

    public serviceReclamation() {
        cnxfl= MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterReclamation(Reclamation reclamation) {
        try {
            String req = "INSERT INTO Reclamation (id_rec, description, date_rec, type) VALUES ('"
                    + reclamation.getId_reclamation() + "', '"
                    + reclamation.getDescription() + "', '"
                    + reclamation.getDate_rec() + "', '"
                    + reclamation.getType() + "')";
            Statement st = cnxfl.createStatement();
            st.executeUpdate(req);
            System.out.println("reclamation ajouteé avec succ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());        }


    }
    @Override
    public void modifierReclamation(int id, String description, String date_rec, String type) {
        try {
            String req = "UPDATE Reclamation SET  description=?, date_rec=?, type=? WHERE id_rec=?";
            PreparedStatement pst = cnxfl.prepareStatement(req);
            pst.setInt(4, id);
            pst.setString(1, description);
            pst.setString(2,date_rec);
            pst.setString(3, type);



            pst.executeUpdate();
            System.out.println("Reclamation modifiée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerReclamation(int id) {
        try {
            String req = "DELETE FROM Reclamation WHERE id_rec=?";
            PreparedStatement pst = cnxfl.prepareStatement(req);
            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("Reclamation supprimée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Reclamation> recupererReclamations() {
        ObservableList<Reclamation> myList = FXCollections.observableArrayList();
        try {
            String requete2 = "SELECT id_rec, description,  date_rec, type FROM Reclamation";
            Statement st = cnxfl.createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setId_reclamation(rs.getInt("id_rec"));
                rec.setDescription(rs.getString("description"));

                // Convert the date string from database to LocalDate
                String dateString = rs.getString("date_rec");
                LocalDate dateRec = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                rec.setDate_rec(dateRec);

                rec.setType(rs.getString("type"));
                myList.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }




}