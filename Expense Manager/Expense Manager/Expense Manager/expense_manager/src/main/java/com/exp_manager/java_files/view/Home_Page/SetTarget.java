package com.exp_manager.java_files.view.Home_Page;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SetTarget {

    private Stage prStage;
    private List<Target> targetList = new ArrayList<>();
    private Scene setTargetScene;

    public void setScene(Scene scene) {
        this.setTargetScene = scene;
    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    // public BorderPane setTargetScene(Runnable backHandler) {
    // this.prStage = prStage;
    // prStage.setTitle("Expenditure Manager");

    // Scene targetScene = createTargetScene();
    // prStage.setScene(targetScene);
    // prStage.show();
    // }

    public VBox setTargetScene(Runnable backHandler) {

        Button backButton = new Button("Back");
        applyButtonStyle(backButton);
        backButton.setOnAction(event -> backHandler.run());

        HBox buttonBox = new HBox(backButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        VBox mainVBox = new VBox(10, buttonBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(25));
        mainVBox.setStyle("-fx-background-color: #f2f2f2;");

        // Header
        Label headerLabel = new Label("Add Your Savings Targets");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        mainVBox.getChildren().add(headerLabel);

        // ScrollPane for Targets
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setMinHeight(200);
        scrollPane.setMaxHeight(300);
        VBox targetsVBox = new VBox(10);
        targetsVBox.setAlignment(Pos.TOP_CENTER);
        targetsVBox.setPadding(new Insets(10));
        scrollPane.setContent(targetsVBox);

        mainVBox.getChildren().add(scrollPane);

        // Plus Icon to Add New Target
        Button addTargetButton = new Button("+");
        addTargetButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        addTargetButton.setOnAction(e -> {
            Target newTarget = showTargetDialog();
            if (newTarget != null) {
                targetList.add(newTarget);
                updateTargetsDisplay(targetsVBox);
            }
        });

        mainVBox.getChildren().add(addTargetButton);
        mainVBox.setStyle("-fx-background-color: #f0fff0;");

        // return new Scene(mainVBox, 800, 600);
        return mainVBox;
    }

    private Target showTargetDialog() {
        Dialog<Target> dialog = new Dialog<>();
        dialog.setTitle("Add New Target");

        // Set the button types (OK and Cancel)
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create inner GridPane for dialog content
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Target Fields
        Label totalMoneyLabel = new Label("Total Money to Save (₹):");
        totalMoneyLabel.setStyle("-fx-font-weight: bold;");
        grid.add(totalMoneyLabel, 0, 0);

        TextField totalMoneyTextField = new TextField();
        totalMoneyTextField.setPromptText("Enter total money to save");
        grid.add(totalMoneyTextField, 1, 0);

        Label reasonLabel = new Label("Reason for Saving:");
        reasonLabel.setStyle("-fx-font-weight: bold;");
        grid.add(reasonLabel, 0, 1);

        TextField reasonTextField = new TextField();
        reasonTextField.setPromptText("Enter the reason for saving");
        grid.add(reasonTextField, 1, 1);

        Label timeFrameLabel = new Label("Time Frame (months):");
        timeFrameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(timeFrameLabel, 0, 2);

        TextField timeFrameTextField = new TextField();
        timeFrameTextField.setPromptText("Enter time frame in months");
        grid.add(timeFrameTextField, 1, 2);

        Label frequencyLabel = new Label("Frequency of Adding Money:");
        frequencyLabel.setStyle("-fx-font-weight: bold;");
        grid.add(frequencyLabel, 0, 3);

        ComboBox<String> frequencyComboBox = new ComboBox<>();
        frequencyComboBox.getItems().addAll("Daily", "Weekly", "Monthly");
        grid.add(frequencyComboBox, 1, 3);

        // Amount Suggestion Label
        Label amountSuggestionLabel = new Label();
        grid.add(amountSuggestionLabel, 1, 4);

        // Update amount suggestion based on time frame and frequency
        timeFrameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmountSuggestion(amountSuggestionLabel, totalMoneyTextField.getText(), newValue,
                    frequencyComboBox.getValue());
        });

        frequencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateAmountSuggestion(amountSuggestionLabel, totalMoneyTextField.getText(), timeFrameTextField.getText(),
                    newValue);
        });

        // Enable/Disable add button based on input validation
        Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.disableProperty().bind(
                totalMoneyTextField.textProperty().isEmpty()
                        .or(reasonTextField.textProperty().isEmpty())
                        .or(timeFrameTextField.textProperty().isEmpty())
                        .or(frequencyComboBox.valueProperty().isNull()));

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Target object when the add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Target(
                        Double.parseDouble(totalMoneyTextField.getText()),
                        reasonTextField.getText(),
                        Integer.parseInt(timeFrameTextField.getText()),
                        frequencyComboBox.getValue());
            }
            return null;
        });

        // Show the dialog and wait for the user's response
        dialog.showAndWait().ifPresent(result -> {
            // Handle result if needed
        });

        return dialog.getResult();
    }

    private void updateAmountSuggestion(Label amountSuggestionLabel, String totalMoney, String timeFrame,
            String frequency) {
        if (!totalMoney.isEmpty() && !timeFrame.isEmpty() && frequency != null) {
            double money = Double.parseDouble(totalMoney);
            int months = Integer.parseInt(timeFrame);

            double amount = 0.0;
            switch (frequency) {
                case "Daily":
                    amount = money / (months * 30);
                    break;
                case "Weekly":
                    amount = money / (months * 4);
                    break;
                case "Monthly":
                    amount = money / months;
                    break;
            }

            amountSuggestionLabel.setText(String.format("Add ₹%.2f %s", amount, frequency));
        } else {
            amountSuggestionLabel.setText("");
        }
    }

    private void updateTargetsDisplay(VBox targetsVBox) {
        targetsVBox.getChildren().clear();
        for (Target target : targetList) {
            VBox targetBox = new VBox(10);
            targetBox.setAlignment(Pos.TOP_LEFT);
            targetBox.setPadding(new Insets(10));
            targetBox.setStyle(
                    "-fx-background-color: #ffffff; -fx-border-color: #b3b3b3; -fx-border-radius: 5; -fx-border-width: 1px;");

            Label targetLabel = new Label(String.format("Target: ₹%.2f for %d months, %s", target.getTotalMoney(),
                    target.getTimeFrame(), target.getFrequency()));
            targetLabel.setStyle("-fx-font-weight: bold;");

            Label reasonLabel = new Label("Reason: " + target.getReason());

            ProgressBar progressBar = new ProgressBar(0);
            progressBar.setPrefWidth(200);

            Label progressLabel = new Label("Progress: 0%");
            Button addMoneyButton = new Button("Add Money");
            TextField addMoneyTextField = new TextField();
            addMoneyTextField.setPromptText("Enter amount to add");

            addMoneyButton.setOnAction(e -> {
                try {
                    double amountToAdd = Double.parseDouble(addMoneyTextField.getText());
                    if (amountToAdd > 0) {
                        target.addMoney(amountToAdd);
                        double progress = target.getSavedMoney() / target.getTotalMoney();
                        progressBar.setProgress(progress);
                        progressLabel.setText(String.format("Progress: %.0f%%", progress * 100));
                        addMoneyTextField.clear();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Please enter a valid amount to add.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Please enter a valid number.");
                }
            });

            targetBox.getChildren().addAll(targetLabel, reasonLabel, progressBar, progressLabel, addMoneyTextField,
                    addMoneyButton);
            targetsVBox.getChildren().add(targetBox);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Target Class
    private static class Target {
        private double totalMoney;
        private double savedMoney = 0;
        private String reason;
        private int timeFrame;
        private String frequency;

        public Target(double totalMoney, String reason, int timeFrame, String frequency) {
            this.totalMoney = totalMoney;
            this.reason = reason;
            this.timeFrame = timeFrame;
            this.frequency = frequency;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public String getReason() {
            return reason;
        }

        public int getTimeFrame() {
            return timeFrame;
        }

        public String getFrequency() {
            return frequency;
        }

        public double getSavedMoney() {
            return savedMoney;
        }

        public void addMoney(double amount) {
            savedMoney += amount;
        }
    }

    private void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);
    }
}