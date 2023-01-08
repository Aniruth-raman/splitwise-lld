package org.aniruth.expensesharing.models.split;

import org.aniruth.expensesharing.models.User;

public class ExactSplit extends Split {

    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}