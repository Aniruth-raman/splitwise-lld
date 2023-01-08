package org.aniruth.expensesharing.controller;

import org.aniruth.expensesharing.models.ExpenseType;
import org.aniruth.expensesharing.models.User;
import org.aniruth.expensesharing.models.expense.*;
import org.aniruth.expensesharing.models.split.PercentSplit;
import org.aniruth.expensesharing.models.split.Split;
import org.aniruth.expensesharing.repository.ExpenseRepository;

import java.util.List;

public class ExpenseController {
    ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void addExpense(ExpenseType expenseType, double amount,
                           String expensePaidBy, List<Split> splits, ExpenseData expenseData) {
        expenseRepository.addExpense(expenseType, amount, expensePaidBy, splits, expenseData);
    }

    public void showBalance(String userName) {
        List<String> balances = expenseRepository.getBalance(userName);
        if (balances.isEmpty()) {
            System.out.println("No balances");
        } else {
            for (String balance : balances) {
                System.out.println(balance);
            }
        }
    }

    public void showBalances() {
        List<String> balances = expenseRepository.getBalances();
        if (balances.isEmpty()) {
            System.out.println("No balances");
        } else {
            for (String balance : balances) {
                System.out.println(balance);
            }
        }
    }

    public static Expense createExpense(ExpenseType expenseType, double amount,
                                        User expensePaidBy, List<Split> splits, ExpenseData expenseData) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(amount, expensePaidBy, splits, expenseData);
            case PERCENT:
                for (Split split : splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    split.setAmount((amount * percentSplit.getPercent()) / 100.0);
                }
                return new PercentageExpense(amount, expensePaidBy, splits, expenseData);
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = ((double) Math.round(amount * 100 / totalSplits)) / 100.0;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                return new EqualExpense(amount, expensePaidBy, splits, expenseData);
            default:
                return null;
        }
    }
}
