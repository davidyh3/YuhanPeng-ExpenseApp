package com.example.expenseapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val amount: String,
    val balance: String,
    val date: String,
    val categoryId: String
)
