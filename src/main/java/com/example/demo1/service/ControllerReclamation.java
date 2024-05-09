package com.example.demo1.service;

import com.example.demo1.entity.Reclamation;
import com.example.demo1.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.control.TableView;


import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.util.Arrays;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaException;
import javafx.scene.control.TextFormatter;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import java.time.LocalDate;
import java.awt.Color;

public class ControllerReclamation implements Initializable {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final List<String> badWords = Arrays.asList("hell", "mama");

    public ObservableList<Reclamation> data = FXCollections.observableArrayList();

    @FXML
    public TableView<Reclamation> table_reclamation;
    @FXML
    public Label id_reclamation;
    @FXML
    public AnchorPane Ah;
    @FXML
    public AnchorPane ah1;
    @FXML
    public Label id_reclamtion;
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
    private DatePicker datePicker;
    @FXML
    public Label type;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    public TableColumn<Reclamation, Integer> id_rec;
    @FXML
    public TableColumn<Reclamation, String> description;
    @FXML
    public TableColumn<Reclamation, String> date_rec;
    @FXML
    public TableColumn<Reclamation, String> type_rec;
    @FXML
    private void handleStatButtonAction(ActionEvent event) {
        // Open a new window to display the statistics
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/Stat.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button showButton;

    // New ComboBox and Sort Button
    @FXML
    private ComboBox<String> sortingComboBox;
    @FXML
    private Button sortButton;
    @FXML
    private Button pdfButton;
    @FXML
    private TextField id_reclamtionn; // Assuming this is your search text field

    private boolean validateDateFormat(String input) {
        return input.matches("\\d{0,2}/\\d{0,2}/\\d{0,4}");
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        String query = id_reclamtionn.getText();
        searchReclamation(query);
    }

    @FXML
    private void handleShowButtonAction(ActionEvent event) {
        updateTable();
    }



    private void show() {
        try {
            String query = "SELECT id_rec, description, date_rec, type FROM Reclamation";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id_recValue = rs.getInt("id_rec");
                String descriptionValue = rs.getString("description");
                String date_recValue = rs.getString("date_rec");
                String typeValue = rs.getString("type");

                // Convert the date string to LocalDate
                LocalDate dateRecValue = LocalDate.parse(date_recValue, dateFormatter);

                // Create the Reclamation object using the converted LocalDate
                data.add(new Reclamation(id_recValue, dateRecValue, descriptionValue, typeValue));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        id_rec.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_rec.setCellValueFactory(new PropertyValueFactory<>("dateRec"));
        type_rec.setCellValueFactory(new PropertyValueFactory<>("type"));

        table_reclamation.setItems(data);
    }

    @FXML
    public void select(MouseEvent mouseEvent) {
        Reclamation v = table_reclamation.getSelectionModel().getSelectedItem();
        idfa.setText(String.valueOf(v.getIdRec()));
        desc.setText(v.getDescription());

        // Convert LocalDate to String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = v.getDateRec().format(formatter);
        datePicker.setPromptText(dateString);

        typeComboBox.setValue(v.getType());
    }

    @FXML
    public void recherche(KeyEvent keyEvent) {
        // Check if Enter key is pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String t = id_reclamtion.getText();
            if (t.isEmpty() || !t.matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un entier valide pour t.");
                alert.showAndWait();
            } else {
                table_reclamation.getItems().clear();
                try {
                    String query = "SELECT id_rec, description, date_rec, type FROM Reclamation WHERE id_rec = " + t;
                    Statement st = MyConnection.getInstance().getCnx().createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        int id_recValue = rs.getInt("id_rec");
                        String descriptionValue = rs.getString("description");

                        // Convert the date string to LocalDate
                        LocalDate date_recValue = LocalDate.parse(rs.getString("date_rec"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        String typeValue = rs.getString("type");
                        // Ensure correct parameter order when creating the Reclamation object
                        data.add(new Reclamation(id_recValue, date_recValue, descriptionValue, typeValue));
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                table_reclamation.setItems(data);
            }
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
                Reclamation v = table_reclamation.getSelectionModel().getSelectedItem();
                serviceReclamation rcd = new serviceReclamation();
                rcd.supprimerReclamation(v.getIdRec());
                updateTable();
            }
        });
        playDeleteSound();
    }

    @FXML
    public void modifierah(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modification");
        alert.setHeaderText("Êtes-vous sûr de vouloir modifier cet élément ?");
        alert.setContentText("Cette action est irréversible.");
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");
        alert.getButtonTypes().setAll(ouiButton, nonButton);

        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ouiButton) {
                Reclamation selectedReclamation = table_reclamation.getSelectionModel().getSelectedItem();
                if (selectedReclamation == null) {
                    System.out.println("aaaaaa");
                    return;
                }

                int idRec = Integer.parseInt(idfa.getText());
                String description = desc.getText();
                LocalDate dateRec = datePicker.getValue(); // Update this line
                String type = typeComboBox.getValue();

                selectedReclamation.setId_reclamation(idRec);
                selectedReclamation.setDescription(description);
                selectedReclamation.setDate_rec(dateRec); // Update this line
                selectedReclamation.setType(type);

                serviceReclamation rc = new serviceReclamation();
                rc.modifierReclamation(idRec, description, dateRec.toString(), type); // Update this line

                updateTable();
                idfa.clear();
                desc.clear();
                datePicker.setValue(null); // Clear DatePicker value
            }
        });
        playUpdateSound();
    }

    public void ajooter(ActionEvent actionEvent) {

        String t = idfa.getText();
        if (t.isEmpty() || !t.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un entier valide pour t.");
            alert.showAndWait();
        } else if (desc.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir la description.");
            alert.showAndWait();
        } else {
            serviceReclamation rc = new serviceReclamation();
            int var4 = Integer.parseInt(t);
            Reclamation r = new Reclamation();
            r.setId_reclamation(var4);
            String description = desc.getText();
            String type = typeComboBox.getValue(); // Get selected value from ComboBox
            if (containsBadWord(description) || containsBadWord(type)) {
                description = censorBadWord(description);
                type = censorBadWord(type);
            }
            r.setDescription(description);
            LocalDate dateRec = datePicker.getValue(); // Update this line
            r.setDate_rec(dateRec); // Update this line
            r.setType(type);
            rc.ajouterReclamation(r);
            updateTable();
            idfa.clear();
            desc.clear();
            datePicker.setValue(null); // Clear DatePicker value
        }
        // Play the notification sound
        playNotificationSound();
    }

    private void playNotificationSound() {
        try {
            // Load the audio file from the resources folder
            URL resource = getClass().getResource("/com/example/demo1/reclamationAdded.wav");
            String soundPath = resource.toString();

            // Create a Media object from the URL
            Media sound = new Media(resource.toExternalForm());

            // Create a MediaPlayer object and play the sound
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (MediaException e) {
            // Handle any exceptions related to loading or playing the sound
            e.printStackTrace();
        }
    }

    private void playDeleteSound() {
        try {
            URL resource = getClass().getResource("/com/example/demo1/reclmationDeleted.wav");
            Media sound = new Media(resource.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
    }
    public class PDFExporter {

        public static void exportTableViewToPDF(TableView<?> tableView, String filePath) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 700); // Adjust position as needed
                    contentStream.showText("FREELANCYY");
                    contentStream.endText();

                    // HTML Content
                    String htmlContent = "<h1></h1><p>.</p>";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 680); // Adjust position as needed
                    contentStream.showText(htmlContent);
                    contentStream.endText();

                    // Draw table borders
                    contentStream.setStrokingColor(Color.RED);
                    contentStream.setLineWidth(1f);
                    contentStream.moveTo(50, 1000); // Adjust position as needed
                    contentStream.lineTo(550, 720); // Adjust position as needed
                    contentStream.stroke();


                    // Export TableView content
                    int startY = 680; // Adjust starting Y position
                    for (Object item : tableView.getItems()) {
                        String rowData = item.toString(); // Adjust how you get the data from your model
                        contentStream.beginText();
                        contentStream.newLineAtOffset(100, startY);
                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        contentStream.showText(rowData);
                        contentStream.endText();
                        startY -= 20; // Adjust line spacing as needed
                    }
                }

                document.save(filePath);
                System.out.println("PDF file created successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playUpdateSound() {
        try {
            URL resource = getClass().getResource("/com/example/demo1/reclamationUpdated.wav");
            Media sound = new Media(resource.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
    }

    private void playStartGuideSound() {
        try {
            URL resource = getClass().getResource("/com/example/demo1/guid.wav");
            Media sound = new Media(resource.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showButton.setOnAction(this::handleShowButtonAction);

        // Initialize ComboBox items
        sortingComboBox.getItems().addAll("id_rec", "date_rec", "description", "type");
        sortingComboBox.setValue("Sort By");

        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Handle selection change if needed
        });

        // Add action listener to the sort button
        sortButton.setOnAction(event -> sortData());

        // Attach a listener to the text property of the search TextField
        id_reclamtionn.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call the searchReclamation method with the new search query
            searchReclamation(newValue);
        });

        playStartGuideSound();

        // Request focus on the date field
        UnaryOperator<TextFormatter.Change> filter = change -> {
            // Allow any change
            return change;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        datePicker.getEditor().setTextFormatter(textFormatter); // Use datePicker instead of date
        datePicker.setEditable(true); // Use datePicker instead of date
        datePicker.requestFocus(); // Use datePicker instead of date

    }

    // Method to validate the date format
    private LocalDate convertToDate(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }

    // Method to display an error message
    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    // Method to perform the validation before adding a record
    private boolean validateAndAddRecord() {
        String dateInput = datePicker.getEditor().getText(); // Get date from DatePicker editor
        if (!validateDateFormat(dateInput)) {
            displayErrorMessage("Date format must be in dd/MM/yyyy");
            return false;
        }
        // If the validation passes, proceed with adding the record
        // Add your logic here to add the record to your application
        return true;
    }



    // Method to handle the "Show" action
    public void handleShowAction(ActionEvent event) {
        // Validate the input before performing the show action
        if (validateAndAddRecord()) {
            // Add your logic here to handle showing the record
            LocalDate dateRec = datePicker.getValue(); // Get date from DatePicker
        }
    }

    @FXML
    private void handleSortButtonAction(ActionEvent event) {
        sortData();
        playSortSound();
    }

    private void playSortSound() {
        try {
            URL resource = getClass().getResource("/com/example/demo1/reclmationDeleted.wav");
            Media sound = new Media(resource.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
    }

    // Method to sort data based on selected ComboBox item
    private void sortData() {
        String selectedSortItem = sortingComboBox.getValue();
        if (selectedSortItem != null) {
            switch (selectedSortItem) {
                case "id_rec":
                    data.sort((r1, r2) -> Integer.compare(r1.getIdRec(), r2.getIdRec()));
                    break;
                case "date_rec":
                    data.sort((r1, r2) -> r1.getDateRec().compareTo(r2.getDateRec()));
                    break;
                case "description":
                    data.sort((r1, r2) -> r1.getDescription().compareTo(r2.getDescription()));
                    break;
                case "type":
                    data.sort((r1, r2) -> r1.getType().compareTo(r2.getType()));
                    break;
            }
            table_reclamation.setItems(data);
        }
    }

    // Method to perform reclamation search
    public void searchReclamation(String query) {
        // Clear the current data in the table
        data.clear();

        // Perform the search query
        try {
            // Use PreparedStatement to avoid SQL injection
            String searchQuery = "SELECT id_rec, description, date_rec, type FROM Reclamation WHERE CAST(id_rec AS CHAR) LIKE ?";
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(searchQuery);
            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id_recValue = rs.getInt("id_rec");
                String descriptionValue = rs.getString("description");
                String date_recValue = rs.getString("date_rec");
                String typeValue = rs.getString("type");

                // Assuming 'date_recValue' is a string in the format "dd/MM/yyyy"
                LocalDate date_rec = LocalDate.parse(date_recValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));


                // Adjust the order of parameters when creating the Reclamation object
                data.add(new Reclamation(id_recValue, date_rec, descriptionValue, typeValue));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Update the TableView with the search results
        table_reclamation.setItems(data);
    }

    // Method to handle exporting TableView data to PDF
    @FXML
    public void exportToPDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            PDFExporter.exportTableViewToPDF(table_reclamation, selectedFile.getAbsolutePath());
        }
    }

    // Method to check if a string contains a bad word
    public boolean containsBadWord(String text) {
        for (String word : badWords) {
            if (text.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    // Method to censor bad words in a string
    private String censorBadWord(String text) {
        for (String word : badWords) {
            text = text.replaceAll("(?i)" + word, "*".repeat(word.length()));
        }
        return text;
    }
}
