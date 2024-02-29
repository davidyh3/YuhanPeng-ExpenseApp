package com.example.expenseapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryTable(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,
    val categoryName : String
)
