package com.example.expenseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expenseapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var expenseViewModel: ExpenseViewModel
    lateinit var binding: ActivityMainBinding
    var balance = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var db = MyRoomDataBase.getIntance(this)
        var repository = ExpenseRepository(db)
        expenseViewModel = ViewModelProvider(
            this,
            ExpenseViewModelFactory(repository)
        )[ExpenseViewModel::class.java]

        expenseViewModel.getAllExpenses().observe(this) { data ->
            /*for(eachExpense in data){
                Log.d("Expense Data", "expense name: ${eachExpense.title}")
            }*/

            if (data.isNotEmpty()) {
                binding.txtBalance.visibility = View.VISIBLE
                binding.recyclerMainView.visibility = View.VISIBLE
                binding.txtNoExpenses.visibility = View.GONE

                balance = data.last().balance
                binding.txtBalance.text = "\u20B9 $balance"

                val dateWiseData = filterExpenseDateWise(data as ArrayList<ExpenseTable>)

                binding.recyclerMainView.layoutManager = LinearLayoutManager(this)
                binding.recyclerMainView.adapter = RecyclerMainAdpter(this, dateWiseData)
            } else {
                binding.txtBalance.visibility = View.GONE
                binding.recyclerMainView.visibility = View.GONE
                binding.txtNoExpenses.visibility = View.VISIBLE
            }
        }

        binding.floatingActionButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddExpanseActivity::class.java
                ).putExtra(AddExpanseActivity.BALANCE_KEY, balance)
            )
        }
    }

    fun filterExpenseDateWise(expenseData: ArrayList<ExpenseTable>): ArrayList<MainModel> {

        val arrMainModel = ArrayList<MainModel>()

        val arrUniqueDates = ArrayList<String>()

        for (eachExpense in expenseData) {

            val eachExpenseDate = eachExpense.date.split(" ")[0]
            if (!arrUniqueDates.contains(eachExpenseDate)) {
                arrUniqueDates.add(eachExpenseDate)
            }
        }

        Log.d("Unique Dates", arrUniqueDates.toString())

        for (eachDate in arrUniqueDates) {

            val arrEachDateExpenses = ArrayList<ExpenseTable>()
            var eachDateAmt = 0

            for (eachExpense in expenseData) {
                val eachExpenseDate = eachExpense.date.split(" ")[0]

                if (eachDate == eachExpenseDate) {
                    arrEachDateExpenses.add(eachExpense)
                    if (eachExpense.type == "0") {
                        eachDateAmt += eachExpense.amount.toInt()
                    } else {
                        eachDateAmt -= eachExpense.amount.toInt()
                    }
                }
            }

            Log.d("Data: ", "${eachDate}, $eachDateAmt")

            //for Today
            val calendar = Calendar.getInstance()
            val todayYear = calendar.get(Calendar.YEAR)
            val todayMonth = calendar.get(Calendar.MONTH)
            val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
            val todayDate = "$todayDay/$todayMonth/$todayYear"


            //for Yesterday
            calendar.add(Calendar.DAY_OF_MONTH, -1)

            val yesterdayYear = calendar.get(Calendar.YEAR)
            val yesterdayMonth = calendar.get(Calendar.MONTH)
            val yesterdayDay = calendar.get(Calendar.DAY_OF_MONTH)
            val yesterdayDate = "$yesterdayDay/$yesterdayMonth/$yesterdayYear"

            var date  = eachDate

            if(date == todayDate){
                date = "Today"
            }
            if(date == yesterdayDate){
                date = "Yesterday"
            }

            arrMainModel.add(MainModel(date, eachDateAmt.toString(), arrEachDateExpenses))
        }

        Log.d("ArrMainModel", arrMainModel.toString())
        return arrMainModel
    }
}