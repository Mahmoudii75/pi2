package com.example.demo1.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.example.demo1.entity.Reclamation;
import com.example.demo1.entity.Reponse;
import com.example.demo1.util.MyConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class ServiceReponse implements IserviceReponse {
    Connection cnxfl;

    public ServiceReponse() {
        cnxfl = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterReponse(Reponse reponse) {
        try {
            String req = "insert into Reponse( id_rec, description , date_rec)"
                    +"values('"+reponse.getId_reclamation()+"','"+reponse.getDescription()+"','"+reponse.getDateRec()
                    +"')";
            Statement st = cnxfl.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse ajouter avec succ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());        }


    }

    @Override
    public void supprimerReclamation(int id) {

    }

    @Override
    public void modifierReponse(int id_rec, String description, String date_rec) {

    }

    @FXML
    @Override
    public void modifierReponse(Reponse reponse, int id_rec, String description, String date_rec) {
        try {
            String req = " UPDATE Reponse SET " + "id_rec=?,  description = ? , date_rec = ? where id_rec=" + reponse.getId_reclamation();
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, id_rec);
            pst.setString(2,description);
            pst.setString(3,date_rec);



            pst.executeUpdate();
            System.out.println("reponse est  modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


    }
    /*public void modifierReponse(int id_rec, String description, String date_rec) {
        try {
            String req = "UPDATE Reponse SET  description=?, date_rec=?,  WHERE id_rec=?";
            PreparedStatement pst = cnxfl.prepareStatement(req);
            pst.setInt(3, id_rec);
            pst.setString(1, description);
            pst.setString(2,date_rec);




            pst.executeUpdate();
            System.out.println("Reclamation modifiée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }*/

    @Override
    public void supprimerReponse(int id) {
        String req = "DELETE FROM Reponse WHERE id_rec=?";
        try (PreparedStatement pst = cnxfl.prepareStatement(req)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reponse supprimée avec succès !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la réponse : " + ex.getMessage());
        }
    }

    @Override
    public List<Reclamation> recupererReclamations() {
        return null;
    }

    @Override
    public List<Reponse> recupererReponse() {
        ObservableList<Reponse> myList = FXCollections.observableArrayList();
        try {

            String requete2 = "Select id_rec,description,date_rec  FROM Reponse";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                Reponse rec = new Reponse();
                rec.setId_reclamation(rs.getInt("id_rec"));

                rec.setDescription1(rs.getString("description1"));
                rec.setDate_reclamation(rs.getString("date_rec"));


                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;
    }}


