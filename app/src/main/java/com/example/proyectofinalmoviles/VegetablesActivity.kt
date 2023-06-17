package com.example.proyectofinalmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VegetablesActivity : AppCompatActivity() {

    //Creamos una lista mutable, basado en la clase de ayuda para la creacion de registros
    private val vegetablesList = mutableListOf<Fruit>()

    //Al utilizar un recyclerView para mostrar los productos en CardViews, debemos declarar la variable para manipular el adaptador que las comunica
    val adapter = CardAdapter(vegetablesList)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vegetables)

        // Inicializamos el recyclerView de la actividad
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerVegetables)

        // Le definimos el adaptador a utilizar
        recyclerView.adapter = adapter

        // Le definimos el manager encargado de la interfaz
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Populamos la lista para que el adaptador sepa de los cambios y los pueda mostrar
        populateFruitsList()

        //Asignamos un listener de click para iniciar el intent de administracion de productos
        findViewById<Button>(R.id.adminButton).setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

    }

    //Funcion para popular la lista de productos basado en los resultados del select de la base de datos
    fun populateFruitsList(){
        // Tomamos como auxiliar la clase DatabaseHelper para realizar operaciones CRUD
        val databaseHelper = DatabaseHelper(this)

        //Definimos la variable bd como tipo lectura de base de datos
        val bd = databaseHelper.readableDatabase

        //Ejecutamos un query SQL se seleccion para obtener lo almacenado en la tabla de vegetales
        val rs = bd.rawQuery("Select * from Vegetables",null)

        //Funcion ciclica para obtener todos los valores y almacenarlos en la lista mutable
        while(rs.moveToNext()){
            vegetablesList.add(Fruit(rs.getInt(0),rs.getString(1),rs.getString(2), rs.getDouble(3)))
        }

        //Funcion ciclica para mostrar en consola los resultados de la lista (Fines de debugging)
        for(item in vegetablesList){
            Log.d("Products", item.toString())
        }

        // Notificamos al adapter que ha habido un cambio para que muestre los nuevos resultados
        adapter.notifyDataSetChanged()
    }
}