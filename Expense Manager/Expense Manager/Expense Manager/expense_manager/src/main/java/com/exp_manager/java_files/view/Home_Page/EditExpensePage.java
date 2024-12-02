package com.exp_manager.java_files.view.Home_Page;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.exp_manager.java_files.control.FirebaseInit;
import com.exp_manager.java_files.model.Expense;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditExpensePage extends Stage {
    private MainPage mainPage;
    private Expense expense;
    private String documentId;

    public EditExpensePage(MainPage mainPage, Expense expense, String documentId) {
        this.documentId = documentId;
        this.mainPage = mainPage;
        this.expense = expense;

        setTitle("EXPENSE MANAGER");
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField(expense.getAmount());
        grid.add(amountLabel, 0, 0);
        grid.add(amountField, 1, 0);

        Label dateLabel = new Label("Date:");
        TextField dateField = new TextField(expense.getDate());
        grid.add(dateLabel, 0, 1);
        grid.add(dateField, 1, 1);

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField(expense.getTime());
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField(expense.getDescription());
        grid.add(descriptionLabel, 0, 3);
        grid.add(descriptionField, 1, 3);

        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField(expense.getCategory());
        grid.add(categoryLabel, 0, 4);
        grid.add(categoryField, 1, 4);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            saveExpense(this.documentId, amountField.getText(), dateField.getText(), timeField.getText(),
                    descriptionField.getText(),
                    categoryField.getText());

            Map<String, Object> updateData = new HashMap<>();

            updateData.put("amount", amountField.getText());
            updateData.put("category", descriptionField.getText());
            updateData.put("date", categoryField.getText());
            updateData.put("description", timeField.getText());
            updateData.put("time", dateField.getText());

            FirebaseInit obj = new FirebaseInit();

            System.out.println(expense.getAmount());
            try {
                obj.updateRec(updateData, expense.getDocumentId());

            } catch (InterruptedException | ExecutionException e1) {
                e1.printStackTrace();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> close());

        grid.add(saveButton, 0, 5);
        grid.add(cancelButton, 1, 5);

        Scene editscene = new Scene(grid);
        setScene(editscene);
    }

    private void saveExpense(String documentId, String amount, String date, String time, String description,
            String category) {
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setTime(time);
        expense.setDescription(description);
        expense.setCategory(category);
        mainPage.updateExpense(expense);
        close();
    }
}
