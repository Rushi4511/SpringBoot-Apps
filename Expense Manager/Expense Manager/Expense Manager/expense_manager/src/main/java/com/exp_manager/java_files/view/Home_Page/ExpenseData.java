package com.exp_manager.java_files.view.Home_Page;

import com.exp_manager.java_files.model.Expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExpenseData {
    private ObservableList<Expense> expenses;

    public ExpenseData() {
        expenses = FXCollections.observableArrayList();
    }

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void updateExpense(Expense expense) {
        // Implement update logic if needed
    }

    public void deleteExpense(Expense expense) {
        expenses.remove(expense);
    }

    public double getTotalAmount() {
        return expenses.stream()
                .mapToDouble(e -> Double.parseDouble(e.getAmount()))
                .sum();
    }
}
