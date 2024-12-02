package com.exp_manager.java_files.view.Home_Page;

import com.exp_manager.java_files.control.FirebaseInit;
import com.exp_manager.java_files.model.Expense;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AddExpensePage extends Stage {
    private MainPage mainPage;

    public AddExpensePage(MainPage mainPage) {
        this.mainPage = mainPage;

        setTitle("EXPENSE MANAGER");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Food", "Medicine", "Grocery", "Shopping", "Accommodation", "Other");

        Button saveButton = new Button("Save");
        applyButtonStyle(saveButton);
        saveButton.setTranslateX(35);

        saveButton.setOnAction(e -> {
            try {
                saveExpense(amountField.getText(), datePicker.getValue(), timeField.getText(),
                        descriptionField.getText(), categoryComboBox.getValue());
            } catch (InterruptedException | ExecutionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });

        gridPane.add(amountLabel, 0, 0);
        gridPane.add(amountField, 1, 0);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);
        gridPane.add(timeLabel, 0, 2);
        gridPane.add(timeField, 1, 2);
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionField, 1, 3);
        gridPane.add(categoryLabel, 0, 4);
        gridPane.add(categoryComboBox, 1, 4);
        gridPane.add(saveButton, 1, 5);

        Scene addscene = new Scene(gridPane, 300, 250);
        initModality(Modality.APPLICATION_MODAL);
        setScene(addscene);
    }

    private void saveExpense(String amount, java.time.LocalDate date, String time, String description,
            String category) throws InterruptedException, ExecutionException {
        Expense expense = new Expense(null, amount, date.toString(), time, description, category);

        Map<String, Object> teamData = new HashMap<>();
        teamData.put("amount", expense.getAmount());
        teamData.put("time", expense.getTime());
        teamData.put("description", expense.getDescription());
        teamData.put("category", expense.getCategory());
        teamData.put("date", expense.getDate());
        FirebaseInit obj = new FirebaseInit();
        obj.createRec(teamData);
        // obj.readRec("AddExp");
        mainPage.addExpense(expense);

        // Save the data to Firestore

        close();
        close();
    }

    void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 8px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);

    }
}
