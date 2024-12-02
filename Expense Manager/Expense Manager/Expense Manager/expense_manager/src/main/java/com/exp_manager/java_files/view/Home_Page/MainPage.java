package com.exp_manager.java_files.view.Home_Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.exp_manager.java_files.control.FirebaseInit;
import com.exp_manager.java_files.model.Expense;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainPage {
    private TableView<Expense> expenseTable;
    private static ObservableList<Expense> expenseList;
    private static Label totalAmountLabel = new Label();
    private static int expenseCounter = 0;
    private Stage prStage;
    private Scene mainScene;

    public void setScene(Scene scene) {
        this.mainScene = scene;
    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    public BorderPane mainPageScene(Runnable backHandler) {
        // prStage.setTitle("Expense Manager");
        // prStage.setHeight(1000);
        // prStage.setWidth(1200);
        // prStage.setResizable(false);

        expenseList = FXCollections.observableArrayList();

        setupExpenseTable();

        Button addExpenseButton = createButton("Add Expense");
        addExpenseButton.setOnAction(e -> openAddExpensePage());

        totalAmountLabel.setFont(new Font(20));
        totalAmountLabel.setTextFill(Color.WHITE);

        Button backButton = createButton("Back");
        backButton.setOnAction(event -> backHandler.run());

        HBox buttonPane = new HBox(10, addExpenseButton, backButton);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setPadding(new Insets(10));

        VBox bottomPane = new VBox(10, buttonPane, totalAmountLabel);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(expenseTable);
        root.setBottom(bottomPane);
        root.setStyle("-fx-background-color: #2C3E50;");

        return root;
        // Scene mainscene = new Scene(root);
        // prStage.setScene(mainscene);
        // prStage.show();
    }

    private void setupExpenseTable() {
        expenseTable = new TableView<>(expenseList);
        expenseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String[] columnNames = { "Amount", "Date", "Time", "Description", "Category", "Edit", "Delete" };
        String[] columnProps = { "amount", "date", "time", "description", "category" };

        for (int i = 0; i < columnProps.length; i++) {
            TableColumn<Expense, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(columnProps[i]));
            applyColumnStyle(column);
            expenseTable.getColumns().add(column);
        }

        expenseTable.getColumns().addAll(createEditColumn(), createDeleteColumn());

        try {
            List<QueryDocumentSnapshot> documents = FirebaseInit.fetchAllDocuments("Expense");
            for (QueryDocumentSnapshot document : documents) {
                DocumentSnapshot expData = null;
                try {
                    expData = FirebaseInit.readRec(document.getId());
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                Expense expense = new Expense(document.getId(), expData.get("amount"), expData.get("date"),
                        expData.get("time"),
                        expData.get("description"), expData.get("category"));

                MainPage.addExpense(expense);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // System.out.println("In Add Exp Page: " + expense);
    }

    private TableColumn<Expense, Void> createEditColumn() {
        TableColumn<Expense, Void> editColumn = new TableColumn<>("Edit");
        applyColumnStyle(editColumn);
        editColumn.setCellFactory(param -> new TableCell<Expense, Void>() {
            private final Button editButton = createButton("Edit");

            {
                editButton.setOnAction(event -> {
                    Expense expense = getTableView().getItems().get(getIndex());
                    openEditExpensePage(expense);
                    updateExpenseInFirebase(expense, expense.getDocumentId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
        return editColumn;
    }

    private TableColumn<Expense, Void> createDeleteColumn() {
        TableColumn<Expense, Void> deleteColumn = new TableColumn<>("Delete");
        applyColumnStyle(deleteColumn);
        deleteColumn.setCellFactory(param -> new TableCell<Expense, Void>() {
            private final Button deleteButton = createButton("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Expense expense = getTableView().getItems().get(getIndex());
                    deleteExpense(expense);
                    deleteExpenseFromFirebase(expense.getDocumentId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        return deleteColumn;
    }

    private void openAddExpensePage() {
        AddExpensePage addExpensePage = new AddExpensePage(this);
        addExpensePage.show();
    }

    private void openEditExpensePage(Expense expense) {
        EditExpensePage editExpensePage = new EditExpensePage(this, expense, expense.getDocumentId());
        editExpensePage.show();
    }

    public static void addExpense(Expense expense) {
        expenseCounter++;
        expense.setId(expenseCounter);
        expenseList.add(expense);
        updateTotalAmount();
    }

    public void updateExpense(Expense expense) {
        expenseList.set(expenseList.indexOf(expense), expense);
        updateTotalAmount();
    }

    private void deleteExpense(Expense expense) {
        expenseList.remove(expense);
        updateTotalAmount();
    }

    private static void updateTotalAmount() {
        double total = expenseList.stream()
                .mapToDouble(e -> Double.parseDouble(e.getAmount()))
                .sum();

        System.out.println(total);
        totalAmountLabel.setText(new String("Total Amount: Rs. " + total));
    }

    private void applyColumnStyle(TableColumn<Expense, ?> column) {
        String columnStyle = "-fx-background-color:#f0fff0; -fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: light; "
                + "-fx-border-radius:2 ; -fx-background-radius: 1; "
                + "-fx-padding: 10 20 10 20;";
        column.setStyle(columnStyle);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        applyButtonStyle(button);
        return button;
    }

    private void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";
        button.setStyle(buttonStyle);
    }

    // private void openHomePage() {
    // try {
    // homePage.start(prStage);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    private void updateExpenseInFirebase(Expense expense, String documentId) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("amount", expense.getAmount());
        updateData.put("time", expense.getTime());
        updateData.put("description", expense.getDescription());
        updateData.put("category", expense.getCategory());
        updateData.put("date", expense.getDate());

        FirebaseInit obj = new FirebaseInit();
        try {
            obj.updateRec(updateData, expense.getDocumentId());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteExpenseFromFirebase(String documentId) {
        FirebaseInit obj = new FirebaseInit();
        try {
            obj.deleteRec(documentId);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
