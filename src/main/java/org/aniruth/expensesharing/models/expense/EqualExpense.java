package org.aniruth.expensesharing.models.expense;

import org.aniruth.expensesharing.models.User;
import org.aniruth.expensesharing.models.split.EqualSplit;
import org.aniruth.expensesharing.models.split.Split;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData) {
        super(amount, expensePaidBy, splits, expenseData);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof EqualSplit)) return false;
        }
        return true;
    }


}