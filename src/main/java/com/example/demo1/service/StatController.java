package com.example.demo1.service;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatController {

    @FXML
    private PieChart pieChart;

    public void initialize() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pijava", "root", "");

            // Query to get total count of reclamations
            String totalCountQuery = "SELECT COUNT(*) AS totalCount FROM reclamation";
            PreparedStatement totalCountStatement = conn.prepareStatement(totalCountQuery);
            ResultSet totalCountResultSet = totalCountStatement.executeQuery();
            int totalCount = 0;
            if (totalCountResultSet.next()) {
                totalCount = totalCountResultSet.getInt("totalCount");
            }

            // Query to get type statistics from the database
            String query = "SELECT type, COUNT(*) AS count FROM reclamation GROUP BY type";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Populate pie chart data
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int count = resultSet.getInt("count");
                double percentage = ((double) count / totalCount) * 100;
                pieChartData.add(new PieChart.Data(type + " (" + String.format("%.2f", percentage) + "%)", count));
            }

            // Set data to pie chart
            pieChart.setData(pieChartData);

            // Close resources
            resultSet.close();
            statement.close();
            totalCountResultSet.close();
            totalCountStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
