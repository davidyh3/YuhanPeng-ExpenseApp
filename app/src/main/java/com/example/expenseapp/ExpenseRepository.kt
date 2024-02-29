package com.example.expenseapp

import androidx.lifecycle.LiveData

class ExpenseRepository(val db: MyRoomDataBase) {

    fun addExpense(newExpense : ExpenseTable){
        db.ExpenseDoa().addExpense(newExpense)
    }

    fun getAllExpense() : LiveData<List<ExpenseTable>>{
        return db.ExpenseDoa().getAllExpense()
    }

    fun updateExpense(updateExpense: ExpenseTable) {
        db.ExpenseDoa().updateExpense(updateExpense)
    }

    fun deleteExpense(deleteExpense: ExpenseTable) {
        db.ExpenseDoa().deleteExpense(deleteExpense)
    }
}