package org.aniruth.expensesharing.controller;

import org.aniruth.expensesharing.models.User;
import org.aniruth.expensesharing.repository.ExpenseRepository;

public class UserController {
    ExpenseRepository expenseRepository;

    public UserController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public static User createUser(int id, String userName, String email, String mobileNumber) {
        return new User(id, userName, email, mobileNumber);
    }

    public void addUser(User user) {
        expenseRepository.addUser(user);
    }

    public User getUser(String userName) {
        return expenseRepository.getUser(userName);
    }

}
