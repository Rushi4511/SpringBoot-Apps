package com.exp_manager.java_files.view.Home_Page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.exp_manager.java_files.control.FirebaseInit;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PieChart1 {
    static Double food = 0.0;
    static Double other = 0.0;
    static Double grocery = 0.0;
    static Double medicine = 0.0;
    static Double shopping = 0.0;
    static Double accommodation = 0.0;

    private Stage prStage;
    private Scene pieScene;

    public void setScene(Scene scene) {
        this.pieScene = scene;

    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    public VBox pieChartScene(Runnable backHandler) {
        ArrayList<String> allDocList = new ArrayList<>();

        try {
            List<QueryDocumentSnapshot> allDocuments = FirebaseInit.fetchAllDocuments("Expense");
            for (QueryDocumentSnapshot document : allDocuments) {
                allDocList.add(document.getId());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < allDocList.size(); i++) {
            try {
                Object cat = FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "category");
                if (cat.equals("Food")) {
                    food += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else if (cat.equals("Grocery")) {
                    grocery += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else if (cat.equals("Shopping")) {
                    shopping += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else if (cat.equals("Medicine")) {
                    medicine += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else if (cat.equals("Accommodation")) {
                    accommodation += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else if (cat.equals("Other")) {
                    other += Double.parseDouble(
                            FirebaseInit.fetchAttribute("Expense", allDocList.get(i), "amount").toString());
                } else {

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Create Pie Chart
        PieChart pieChart = new PieChart();
        pieChart.getData().add(new PieChart.Data("Food", food));
        pieChart.getData().add(new PieChart.Data("Shopping", shopping));
        pieChart.getData().add(new PieChart.Data("Medicine", medicine));
        pieChart.getData().add(new PieChart.Data("Grocery", grocery));
        pieChart.getData().add(new PieChart.Data("Accommodation", accommodation));
        pieChart.getData().add(new PieChart.Data("Other", other));

        // Customize the pie chart appearance
        pieChart.setStyle("-fx-font-size: 16px;");

        // Create ListView for expenses
        ListView<String> expenseList = new ListView<>();
        expenseList.getItems().add("Food                         ₹ " + food);
        expenseList.getItems().add("Shopping                  ₹ " + shopping);
        expenseList.getItems().add("Medicine                   ₹ " + medicine);
        expenseList.getItems().add("Grocery                     ₹ " + grocery);
        expenseList.getItems().add("Accommodation       ₹ " + accommodation);
        expenseList.getItems().add("Other                        ₹ " + other);

        expenseList.setStyle("-fx-font-size: 14px; -fx-background-color: #E0E0E0;");

        // Create Total Label
        Label totalLabel = new Label(
                "Total          ₹ " + (food + medicine + shopping + grocery + other + accommodation));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Create Back Button
        Button backButton = new Button("Back");
        applyButtonStyle(backButton);
        backButton.setOnAction(event -> backHandler.run());

        // Create Layout
        VBox layout = new VBox(20, pieChart, new Separator(), expenseList, new Separator(), totalLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #F5F5F5; -fx-spacing: 20;");

        // Create HBox for back button and align it to the right
        HBox buttonBox = new HBox(backButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        VBox mainLayout = new VBox(layout, buttonBox);
        mainLayout.setStyle("-fx-background-color: #f0fff0;");

        return mainLayout;

        // Create Scene
        // Scene piescene = new Scene(mainLayout, 500, 700);

        // // Setup Stage
        // primaryStage.setTitle("Expense Management");
        // primaryStage.setScene(piescene);
        // primaryStage.show();
    }

    private void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);
    }

}
