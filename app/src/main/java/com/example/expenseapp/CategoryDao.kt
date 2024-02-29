package com.example.expenseapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {

    @Insert
    fun addCategory(newCategory : CategoryTable)

    @Query("select * from category")
    fun getAllCategory(): List<CategoryTable>

    @Update
    fun updateCategory(newCategory : CategoryTable)

    @Delete
    fun deleteCategory(newCategory : CategoryTable)
}