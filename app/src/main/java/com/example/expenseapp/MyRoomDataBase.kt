package com.example.expenseapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ExpenseTable::class, CategoryTable::class], exportSchema = false, version = 1)
abstract class MyRoomDataBase(): RoomDatabase(){

    abstract fun ExpenseDoa() : ExpenseDao
    abstract fun CategoryDao() : CategaryDao


    companion object{

        const val DB_NAME = "ExpenseDB"
        var DB_INSTANCE : MyRoomDataBase?=null


        fun getIntance(context: Context) : MyRoomDataBase{
            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(context,
                    MyRoomDataBase::class.java,
                    DB_NAME).allowMainThreadQueries().build()
            }
            return DB_INSTANCE!!
        }



    }

}