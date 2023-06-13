package com.example.proyectofinalmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VegetablesActivity : AppCompatActivity() {
    private val vegetablesList = mutableListOf<Fruit>()
    val adapter = CardAdapter(vegetablesList)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetables)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerVegetables)

        // Create and set the adapter

        recyclerView.adapter = adapter

        // Set the layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Populate the fruitsList with your data
        populateFruitsList()

        findViewById<Button>(R.id.adminButton).setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

    }

    fun populateFruitsList(){
        // Example usage
        val databaseHelper = DatabaseHelper(this)

        /*databaseHelper.agregar("Piña", "Lata 1KG Piña en almibar", 25.5, "Fruits")
        databaseHelper.agregar("Duraznos", "Lata 1KG Durazno en almibar", 25.5, "Fruits")
        databaseHelper.agregar("Mango", "Lata 1KG Mango en almibar", 25.5, "Fruits")*/


        val bd = databaseHelper.readableDatabase
        val rs = bd.rawQuery("Select * from Vegetables",null)
        while(rs.moveToNext()){
            vegetablesList.add(Fruit(rs.getInt(0),rs.getString(1),rs.getString(2), rs.getDouble(3)))
        }

        for(item in vegetablesList){
            Log.d("Products", item.toString())
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged()
    }
}