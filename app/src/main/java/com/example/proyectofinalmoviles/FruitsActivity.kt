package com.example.proyectofinalmoviles

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FruitsActivity : AppCompatActivity() {

    private val fruitsList = mutableListOf<Fruit>()
    val adapter = CardAdapter(fruitsList)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruits)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerFruits)

        // Create and set the adapter

        recyclerView.adapter = adapter

        // Set the layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Populate the fruitsList with your data
        populateFruitsList()

    }

    fun populateFruitsList(){
        // Example usage
        val databaseHelper = DatabaseHelper(this)

        /*databaseHelper.agregar("Piña", "Lata 1KG Piña en almibar", 25.5, "Fruits")
        databaseHelper.agregar("Duraznos", "Lata 1KG Durazno en almibar", 25.5, "Fruits")
        databaseHelper.agregar("Mango", "Lata 1KG Mango en almibar", 25.5, "Fruits")*/


        val bd = databaseHelper.readableDatabase
        val rs = bd.rawQuery("Select * from Fruits",null)
        while(rs.moveToNext()){
            fruitsList.add(Fruit(rs.getInt(0),rs.getString(1),rs.getString(2), rs.getDouble(3)))
        }

        for(item in fruitsList){
            Log.d("Products", item.toString())
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged()
    }

}