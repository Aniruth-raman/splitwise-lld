package org.aniruth.expensesharing.models.expense;

import org.aniruth.expensesharing.models.User;
import org.aniruth.expensesharing.models.split.Split;

import java.util.List;
import java.util.UUID;

public abstract class Expense {
    private final String id;
    private final double amount;
    private final User expensePaidBy;
    private final List<Split> splits;
    private final ExpenseData expenseData;


    public Expense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.expensePaidBy = expensePaidBy;
        this.splits = splits;
        this.expenseData = expenseData;
    }

    public double getAmount() {
        return amount;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public abstract boolean validate();
}
