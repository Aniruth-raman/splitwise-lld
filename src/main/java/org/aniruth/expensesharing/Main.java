package org.aniruth.expensesharing;

import org.aniruth.expensesharing.controller.ExpenseController;
import org.aniruth.expensesharing.controller.UserController;
import org.aniruth.expensesharing.models.ExpenseType;
import org.aniruth.expensesharing.models.Type;
import org.aniruth.expensesharing.models.User;
import org.aniruth.expensesharing.models.expense.ExpenseData;
import org.aniruth.expensesharing.models.split.EqualSplit;
import org.aniruth.expensesharing.models.split.ExactSplit;
import org.aniruth.expensesharing.models.split.PercentSplit;
import org.aniruth.expensesharing.models.split.Split;
import org.aniruth.expensesharing.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user1 = UserController.createUser(1, "u1", "u1@email.com", "9876543210");
        User user2 = UserController.createUser(2, "u2", "u2@email.com", "9876543210");
        User user3 = UserController.createUser(3, "u3", "u3@email.com", "9876543210");
        User user4 = UserController.createUser(4, "u4", "u4@email.com", "9876543210");

        ExpenseRepository expenseRepository = new ExpenseRepository();
        UserController userController = new UserController(expenseRepository);
        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);
        userController.addUser(user4);
        ExpenseController service = new ExpenseController(expenseRepository);

        while (true) {
            Scanner scan = new Scanner(System.in);
            String[] commands = scan.nextLine().split(" ");
            Type type = Type.of(commands[0]);
            switch (type) {
                case EXPENSE:
                    String userName = commands[1];
                    double amountSpend = Double.parseDouble(commands[2]);
                    int totalMembers = Integer.parseInt(commands[3]);
                    List<Split> splits = new ArrayList<>();
                    int expenseIndex = 3 + totalMembers + 1;
                    ExpenseType expense = ExpenseType.of(commands[expenseIndex]);
                    switch (expense) {
                        case EQUAL:
                            for (int i = 0; i < totalMembers; i++) {
                                splits.add(new EqualSplit(userController.getUser(commands[4 + i])));
                            }
                            service.addExpense(ExpenseType.EQUAL, amountSpend, userName, splits, new ExpenseData("GoaFlight"));
                            break;
                        case EXACT:
                            for (int i = 0; i < totalMembers; i++) {
                                splits.add(new ExactSplit(userController.getUser(commands[4 + i]), Double.parseDouble(commands[expenseIndex + i + 1])));
                            }
                            service.addExpense(ExpenseType.EXACT, amountSpend, userName, splits, new ExpenseData("CabTickets"));

                            break;
                        case PERCENT:
                            for (int i = 0; i < totalMembers; i++) {
                                splits.add(new PercentSplit(userController.getUser(commands[4 + i]), Double.parseDouble(commands[expenseIndex + i + 1])));
                            }
                            service.addExpense(ExpenseType.PERCENT, amountSpend, userName, splits, new ExpenseData("Dinner"));
                            break;
                    }
                    break;
                case SHOW:
                    if (commands.length == 1) service.showBalances();
                    else service.showBalance(commands[1]);
                    break;
                case QUIT:
                    System.out.println("Quitting...");
                    return;
                default:
                    System.out.println("No Expected Argument Found");
                    break;
            }

        }
    }
}