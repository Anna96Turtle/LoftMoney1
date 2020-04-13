package com.loftblog.loftmoney.screens.balance;

public interface BalanceViewState {
    void setState(Boolean isLoading);
    void setBalance(String balance);
    void setIncome(String income);
    void setExpense(String expense);
    void setDiagram(int expense, int income);
}