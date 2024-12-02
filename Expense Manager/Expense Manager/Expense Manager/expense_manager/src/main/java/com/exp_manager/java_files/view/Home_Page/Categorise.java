package com.exp_manager.java_files.view.Home_Page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.exp_manager.java_files.control.FirebaseInit;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Categorise {
    static Double food = 0.0;
    static Double other = 0.0;
    static Double grocery = 0.0;
    static Double medicine = 0.0;
    static Double shopping = 0.0;
    static Double accommodation = 0.0;

    private Stage prStage;
    private Scene catScene;

    public void setScene(Scene scene) {
        this.catScene = scene;
    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    public VBox catPageScene(Runnable backHandler) {
        prStage.setTitle("EXPENSE MANAGER");

        Button backButton = new Button("Back");
        applyButtonStyle(backButton);
        backButton.setOnAction(event -> backHandler.run());
        // backButton.setOnAction(new EventHandler<ActionEvent>() {
        // public void handle(ActionEvent event) {
        // HomePage homePageExPage = new HomePage();
        // try {
        // homePageExPage.start(primarStage);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });

        HBox buttonBox = new HBox(backButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        // Main VBox
        VBox mainVBox = new VBox(20, buttonBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(20));
        mainVBox.setStyle("-fx-background-color: #f0fff0;");

        // Header
        Label headerLabel = new Label("Categorize Your Transactions");
        headerLabel.setFont(new Font("Arial", 28));
        headerLabel.setStyle("-fx-text-fill: BLACK; -fx-font-weight: bold;");
        mainVBox.getChildren().add(headerLabel);

        // Month and Year Selection
        HBox dateSelectionHBox = new HBox(10);
        dateSelectionHBox.setAlignment(Pos.CENTER);
        dateSelectionHBox.setPadding(new Insets(20));
        dateSelectionHBox.setStyle("-fx-border-color: #2e8b57; -fx-border-width: 0 0 1 0;");

        Label monthLabel = new Label("Month & Year:");
        monthLabel.setStyle("-fx-text-fill: BLACK; -fx-font-weight: bold;");

        monthLabel.setFont(new Font("Arial", 16));

        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December");
        monthComboBox.setValue("January"); // Default value

        applyButtonStyle1(monthComboBox);

        ComboBox<Integer> yearComboBox = new ComboBox<>();
        for (int year = 2000; year <= 2100; year++) {
            yearComboBox.getItems().add(year);
        }
        yearComboBox.setValue(2024);
        applyButtonStyle(yearComboBox); // Default value

        dateSelectionHBox.getChildren().addAll(monthLabel, monthComboBox, yearComboBox);
        mainVBox.getChildren().add(dateSelectionHBox);

        // Category Cards
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));

        String[] categories = { "Food", "Shopping", "Medicine", "Grocery", "Accommodation", "Other" };
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
                System.out.println(cat);
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

        ArrayList<Double> catExp = new ArrayList<>();
        catExp.add(food);
        catExp.add(shopping);
        catExp.add(medicine);
        catExp.add(grocery);
        catExp.add(accommodation);
        catExp.add(other);
        for (int i = 0; i < categories.length; i++) {
            VBox card = createCategoryCard(categories[i], catExp.get(i));
            gridPane.add(card, i % 3, i / 3);
        }

        mainVBox.getChildren().add(gridPane);
        mainVBox.setStyle("-fx-background-color: #f0fff0;");

        return mainVBox;

        // Scene scene = new Scene(mainVBox, 900, 700);

        // primaryStage.setScene(scene);
        // primaryStage.show();
    }

    private VBox createCategoryCard(String category, Double totalExp) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #2e8b57; -fx-border-radius: 5; -fx-border-width: 2px;");
        card.setPrefSize(220, 220);

        card.setOnMouseEntered(e -> card.setEffect(new DropShadow(10, Color.GREEN)));
        card.setOnMouseExited(e -> card.setEffect(null));

        Label categoryLabel = new Label(category);
        categoryLabel.setFont(new Font("Arial", 18));
        categoryLabel.setStyle("-fx-font-weight: bold;");

        Label totalTransactionsLabel = new Label("Total: ₹" + totalExp); // Placeholder for total transactions
        totalTransactionsLabel.setFont(new Font("Arial", 14));

        // Progress Ring
        // StackPane progressRing = createProgressRing(0); // 0% placeholder
        // progressRing.setPrefSize(100, 100);

        Button detailsButton = new Button("Show Transactions");
        detailsButton.setStyle("-fx-background-color: GREEN; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        detailsButton.setOnAction(e -> showTransactionsDetails(category));

        // Tooltip progressTooltip = new Tooltip("Shows the progress of transactions for
        // " + category);
        // Tooltip.install(progressRing, progressTooltip);

        Tooltip detailsTooltip = new Tooltip("Click to view detailed transactions for " + category);
        Tooltip.install(detailsButton, detailsTooltip);

        card.getChildren().addAll(/* progressRing, */ categoryLabel, totalTransactionsLabel, detailsButton);
        return card;
    }

    private void applyButtonStyle(ComboBox<Integer> yearComboBox) {
        String buttonStyle = "-fx-background-color: LIGHTGREEN; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; ";

        yearComboBox.setStyle(buttonStyle);

    }

    private void applyButtonStyle1(ComboBox<String> monthComboBox) {
        String buttonStyle = "-fx-background-color: LIGHTGREEN; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; ";

        monthComboBox.setStyle(buttonStyle);

    }

    // private StackPane createProgressRing(double progress) {
    // Circle outerRing = new Circle(50, Color.TRANSPARENT);
    // outerRing.setStroke(Color.LIGHTGRAY);
    // outerRing.setStrokeWidth(10);
    // outerRing.setStrokeType(StrokeType.OUTSIDE);

    // Circle innerRing = new Circle(50, Color.TRANSPARENT);
    // innerRing.setStroke(Color.LIGHTCORAL);
    // innerRing.setStrokeWidth(10);
    // innerRing.setStrokeType(StrokeType.OUTSIDE);
    // innerRing.setStrokeDashOffset(100 - (progress * 100));
    // innerRing.getStrokeDashArray().addAll(314.0, 314.0);

    // RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2),
    // innerRing);
    // rotateTransition.setByAngle(360);
    // rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
    // rotateTransition.setAutoReverse(false);
    // rotateTransition.play();

    // // Placeholder for category image in the center
    // ImageView categoryImage = null;
    // try {
    // categoryImage = new ImageView(/* new Image(getClass().getResourceAsStream()
    // */);
    // } catch (Exception e) {
    // categoryImage = new ImageView(new Image("")); // Placeholder image
    // }
    // categoryImage.setFitWidth(40);
    // categoryImage.setFitHeight(40);

    // // StackPane progressRing = new StackPane(outerRing, innerRing,
    // categoryImage);
    // // return progressRing;
    // }

    private void showTransactionsDetails(String category) {
        // Placeholder for showing transactions details
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transactions Details");
        alert.setHeaderText(null);
        alert.setContentText("Showing details for " + category);
        alert.showAndWait();
    }

    private void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);
    }
}
