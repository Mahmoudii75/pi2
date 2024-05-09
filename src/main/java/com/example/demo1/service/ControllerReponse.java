package com.example.demo1.service;


import com.example.demo1.util.MyConnection;
import com.example.demo1.entity.Reponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerReponse implements Initializable {
    public ObservableList<Reponse> data = FXCollections.observableArrayList();

    @FXML
    public TableView<Reponse> table_reclamation;
    @FXML
    public Label id_reclamation;
    @FXML
    public AnchorPane Ah;
    @FXML
    public AnchorPane ah1;

    @FXML
    public TextField idfa;
    @FXML
    public Label description1;
    @FXML
    public TextField desc;
    @FXML
    public Button supr;
    @FXML
    public Button btn2;
    @FXML
    public Button btn1;
    @FXML
    public Label date_reclamation;
    @FXML
    public TextField date;


    @FXML
    public TableColumn<Reponse, Integer> id_rec;
    @FXML
    public TableColumn<Reponse, String> description;
    @FXML
    public TableColumn<Reponse, String> date_rec;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
        table_reclamation.setOnMouseClicked(event -> {
            Reponse selectedItem = table_reclamation.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                idfa.setText(""+selectedItem.getId_reclamation());
                desc.setText(selectedItem.getDescription());

                date.setText(selectedItem.getDate_rec());


                // You can perform actions with the selected item here
            }
        });    }

    private void show() {
        try {
            String query = "SELECT id_rec, description, date_rec FROM Reponse";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id_recValue = rs.getInt("id_rec");
                String descriptionValue = rs.getString("description");
                String date_recValue = rs.getString("date_rec");

                data.add(new Reponse(id_recValue, descriptionValue, date_recValue));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        id_rec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_rec.setCellValueFactory(new PropertyValueFactory<>("dateRec"));


        table_reclamation.setItems(data);
    }







    @FXML
    public void select(MouseEvent mouseEvent) {
        Reponse v = table_reclamation.getSelectionModel().getSelectedItem();
        idfa.setText(String.valueOf(v.getIdRec()));
        desc.setText(v.getDescription());
        date.setText(v.getDateRec());

    }

    @FXML
    public void recherche(KeyEvent keyEvent) {
        String t = id_reclamation.getText();
        if (t.isEmpty() || !t.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un entier valide pour t.");
            alert.showAndWait();
        } else {
            table_reclamation.getItems().clear();
            try {
                String query = "SELECT id_rec, description, date_rec FROM Reclamation WHERE id_rec = " + t;
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    int id_recValue = rs.getInt("id_rec");
                    String descriptionValue = rs.getString("description");
                    String date_recValue = rs.getString("date_rec");

                    data.add(new Reponse(id_recValue, descriptionValue, date_recValue));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            table_reclamation.setItems(data);
        }
    }

    private void updateTable() {
        data.clear();
        show();
    }

    public void checkentier(KeyEvent event) {
    }

    public void checkstringa(KeyEvent event) {
    }

    public void suprimerah(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet élément ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");

        alert.getButtonTypes().setAll(ouiButton, nonButton);

        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ouiButton) {
                Reponse v = table_reclamation.getSelectionModel().getSelectedItem();
                ServiceReponse rcd = new ServiceReponse();
                rcd.supprimerReponse(v.getIdRec());
                updateTable();
            }
        });

    }

@FXML
    public void modifierah(ActionEvent actionEvent) {          {  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modifier");
        alert.setHeaderText("Êtes-vous sûr de vouloir modifier cet élément ?");
        alert.setContentText("Cette action est irréversible.");
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");
        alert.getButtonTypes().setAll(ouiButton, nonButton);

        // afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ouiButton){

                ServiceReponse rc = new ServiceReponse();
                String var1=idfa.getText();
                String var2=desc.getText();
                String var3=date.getText();



                int var4=Integer.parseInt(var1);

                Reponse r = new Reponse() ;
                r.setId_reclamation(var4);
                r.setDescription(var2);
                r.setDate_reclamation(var3);


                r=table_reclamation.getSelectionModel().getSelectedItem();
                rc.modifierReponse(r,var4,var2,var3);
                updateTable();
                idfa.clear();
                desc.clear();
                date.clear();
            }

            else if (reponse == nonButton) {
            }
        });

    }
    }



  /*  @FXML
    public void modifierah(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modification");
        alert.setHeaderText("Êtes-vous sûr de vouloir modifier cet élément ?");
        alert.setContentText("Cette action est irréversible.");
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");
        alert.getButtonTypes().setAll(ouiButton, nonButton);

        // afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(Reponse -> {
            if (Reponse == ouiButton) {
                Reponse selectedReponse = table_reclamation.getSelectionModel().getSelectedItem();
                if (selectedReponse == null) {
                    System.out.println("aaaaaa");
                    return;
                }

                // Get the updated values from the input fields
                int id_rec = Integer.parseInt(idfa.getText());
                String description = desc.getText();
                String date_rec = date.getText();


                // Update the selected reclamation with the new values
                selectedReponse.setId_reclamation(id_rec);
                selectedReponse.setDescription(description);
                selectedReponse.setDate_rec(String.valueOf(date));


                // Call the appropriate method to modify the reclamation
                ServiceReponse rc = new ServiceReponse();


                // Update the table and clear the input fields
                updateTable();
                idfa.clear();
                desc.clear();
                date.clear();

            } else if (Reponse == nonButton) {
                // Handle the case when the user selects "Non"
            }
        });
    }*/
    public void ajooter(ActionEvent actionEvent) {
        String t = idfa.getText();
        if (t.isEmpty() || !t.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un entier valide pour t.");
            alert.showAndWait();
        } else if (desc.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir la description.");
            alert.showAndWait();
        } else {
            ServiceReponse rc = new ServiceReponse();
            int var3 = Integer.parseInt(t);
            Reponse r = new Reponse();
            r.setId_reclamation(var3);
            r.setDescription(desc.getText());
            r.setDateRec(date.getText());

            rc.ajouterReponse(r);
            updateTable();
            idfa.clear();
            desc.clear();
            date.clear();

        }
    }

    public void selectervoitureadmin(MouseEvent mouseEvent) {
    }
}
