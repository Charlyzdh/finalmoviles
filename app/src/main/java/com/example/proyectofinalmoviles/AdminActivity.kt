package com.example.proyectofinalmoviles

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity(), CardAdapter.OnItemClickListener {
    private val fruitsList = mutableListOf<Fruit>()
    val adapter = CardAdapter(fruitsList)


    override fun onCreate(savedInstanceState: Bundle?) {

        adapter.setOnItemClickListener(this)

        val databaseHelper = DatabaseHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val prodBox = findViewById<EditText>(R.id.prodBox)
        val descBox = findViewById<EditText>(R.id.descBox)
        val priceBox = findViewById<EditText>(R.id.priceBox)
        val radioDairy = findViewById<RadioButton>(R.id.radioDairy)
        val radioVegetables = findViewById<RadioButton>(R.id.radioVegetables)
        val radioFruits = findViewById<RadioButton>(R.id.radioFruits)



        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProds)

        // Create and set the adapter

        recyclerView.adapter = adapter

        // Set the layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        radioFruits.setOnClickListener{
            // Populate the fruitsList with your data
            populateFruitsList("Fruits")
        }

        radioVegetables.setOnClickListener{
            populateFruitsList("Vegetables")
        }

        radioDairy.setOnClickListener{
            populateFruitsList("Dairy")
        }

        findViewById<Button>(R.id.editButton).setOnClickListener{
            Log.d("Products: ",adapter.itemCount.toString())
        }

        findViewById<Button>(R.id.addButton).setOnClickListener{

            if(prodBox.text.isNotBlank() && descBox.text.isNotBlank() && priceBox.text.isNotBlank()){
                var table = "";
                if(radioVegetables.isChecked){
                    table = "Vegetables"
                }else if(radioFruits.isChecked){
                    table = "Fruits"
                }else if(radioDairy.isChecked){
                    table = "Dairy"
                }

                databaseHelper.agregar(prodBox.text.toString(), descBox.text.toString(), priceBox.text.toString().toDouble(),table)
                populateFruitsList(table)

            }else{
                Toast.makeText(this,"Todos los campos deben contener información!",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemClick(position: Int) {
        /*val items = arrayOf<String>("")

        items[position] = fruitsList[position].toString()
        items[position].substring(items[position].indexOf("=") + 1, items[position].indexOf(","))*/
        findViewById<EditText>(R.id.prodBox).setText("" + fruitsList[position].prod)
        Toast.makeText(this,"Clicked: " + fruitsList[position].prod,Toast.LENGTH_SHORT).show()
    }

    fun populateFruitsList(table: String){
        // Example usage
        val databaseHelper = DatabaseHelper(this)

        fruitsList.clear()

        val bd = databaseHelper.readableDatabase
        val rs = bd.rawQuery("Select * from $table",null)
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