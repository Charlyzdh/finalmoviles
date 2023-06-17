package com.example.proyectofinalmoviles

import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity(), CardAdapter.OnItemClickListener {

    //Creamos una lista mutable, basado en la clase de ayuda para la creacion de registros
    private val fruitsList = mutableListOf<Fruit>()

    //Al utilizar un recyclerView para mostrar los productos en CardViews, debemos declarar la variable para manipular el adaptador que las comunica
    val adapter = CardAdapter(fruitsList)


    override fun onCreate(savedInstanceState: Bundle?) {

        //Asignamos un listener de click para cada item que se mostrará en el recyclerView
        adapter.setOnItemClickListener(this)

        //Declaramos una variable para comunicar con la clase de base de datos
        val databaseHelper = DatabaseHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Declaramos variables para cada elemento de texto y botones tipo radio
        val idBox = findViewById<EditText>(R.id.idBox)
        val prodBox = findViewById<EditText>(R.id.prodBox)
        val descBox = findViewById<EditText>(R.id.descBox)
        val priceBox = findViewById<EditText>(R.id.priceBox)
        val radioDairy = findViewById<RadioButton>(R.id.radioDairy)
        val radioVegetables = findViewById<RadioButton>(R.id.radioVegetables)
        val radioFruits = findViewById<RadioButton>(R.id.radioFruits)



        // Inicializamos el recyclerView de la actividad
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProds)

        // Le definimos el adaptador a utilizar
        recyclerView.adapter = adapter

        // Le definimos el manager encargado de la interfaz
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Definimos los listeners para cada radioButton
        radioFruits.setOnClickListener{
            //Limpiamos el texto contenido en las cajas de texto
            idBox.setText("")
            prodBox.setText("")
            descBox.setText("")
            priceBox.setText("")

            //Populamos la lista con los datos de la tabla de frutas
            populateFruitsList("Fruits")
        }

        radioVegetables.setOnClickListener{
            //Limpiamos el texto contenido en las cajas de texto
            idBox.setText("")
            prodBox.setText("")
            descBox.setText("")
            priceBox.setText("")

            //Populamos la lista con los datos de la tabla de vegetales
            populateFruitsList("Vegetables")
        }

        radioDairy.setOnClickListener{
            //Limpiamos el texto contenido en las cajas de texto
            idBox.setText("")
            prodBox.setText("")
            descBox.setText("")
            priceBox.setText("")

            //Populamos la lista con los datos de la tabla de lacteos
            populateFruitsList("Dairy")
        }



        //Asignamos un listener para eliminar registros de la base de datos
        findViewById<Button>(R.id.deleteButton).setOnClickListener{

            //Validamos que las cajas de texto no estén vacias para proceder con la eliminacion
            if(idBox.text.isNotBlank() && prodBox.text.isNotBlank() && descBox.text.isNotBlank() && priceBox.text.isNotBlank()){
                var table = "";

                //Validamos cual de las tablas se van a manipular
                if(radioVegetables.isChecked){
                    table = "Vegetables"
                }else if(radioFruits.isChecked){
                    table = "Fruits"
                }else if(radioDairy.isChecked){
                    table = "Dairy"
                }

                //Utilizamos la funcion eliminar dentro de la clase helper de la base de datos
                databaseHelper.eliminar(idBox.text.toString(),table)

                //Populamos la lista de productos de la tabla seleccionada por los radioButtons
                populateFruitsList(table)

            }else{

                //En caso de que no se cumplan los requisitos de informacion, mostramos un Toast con el error
                Toast.makeText(this,"Debe seleccionar al menos un producto!",Toast.LENGTH_SHORT).show()
            }

            //Para evitar errores o manipulaciones involuntarias, eliminamos los valores dentro de los EditText
            idBox.setText("")
            prodBox.setText("")
            descBox.setText("")
            priceBox.setText("")
        }


        findViewById<Button>(R.id.editButton).setOnClickListener{
            //Validamos que las cajas de texto no estén vacias para proceder con la edicion
            if(idBox.text.isNotBlank() && prodBox.text.isNotBlank() && descBox.text.isNotBlank() && priceBox.text.isNotBlank()){

                //Validamos cual de las tablas se van a manipular
                var table = "";
                if(radioVegetables.isChecked){
                    table = "Vegetables"
                }else if(radioFruits.isChecked){
                    table = "Fruits"
                }else if(radioDairy.isChecked){
                    table = "Dairy"
                }

                //Utilizamos la funcion actualizar dentro de la clase helper de la base de datos con el paso de parametros sobre todos los datos de la tabla de la bd
                databaseHelper.actualizar(idBox.text.toString(),prodBox.text.toString(), descBox.text.toString(), priceBox.text.toString().toDouble(),table)

                //Populamos la lista de productos de la tabla seleccionada por los radioButtons
                populateFruitsList(table)

            }else{
                //En caso de que no se cumplan los requisitos de informacion, mostramos un Toast con el error
                Toast.makeText(this,"Todos los campos deben contener información!",Toast.LENGTH_SHORT).show()
            }

            //Para evitar errores o manipulaciones involuntarias, eliminamos los valores dentro de los EditText
            idBox.setText("")
            prodBox.setText("")
            descBox.setText("")
            priceBox.setText("")
        }

        findViewById<Button>(R.id.addButton).setOnClickListener{

            //Validamos que las cajas de texto no estén vacias para proceder con la edicion
            if(prodBox.text.isNotBlank() && descBox.text.isNotBlank() && priceBox.text.isNotBlank()){
                var table = "";

                //Validamos cual de las tablas se van a manipular
                if(radioVegetables.isChecked){
                    table = "Vegetables"
                }else if(radioFruits.isChecked){
                    table = "Fruits"
                }else if(radioDairy.isChecked){
                    table = "Dairy"
                }

                //Utilizamos la funcion agregar dentro de la clase helper de la base de datos con el paso de parametros sobre todos los datos de la tabla de la bd
                databaseHelper.agregar(prodBox.text.toString(), descBox.text.toString(), priceBox.text.toString().toDouble(),table)

                //Populamos la lista de productos de la tabla seleccionada por los radioButtons
                populateFruitsList(table)

            }else{
                //En caso de que no se cumplan los requisitos de informacion, mostramos un Toast con el error
                Toast.makeText(this,"Todos los campos deben contener información!",Toast.LENGTH_SHORT).show()
            }
        }

        //Para evitar errores o manipulaciones involuntarias, eliminamos los valores dentro de los EditText
        idBox.setText("")
        prodBox.setText("")
        descBox.setText("")
        priceBox.setText("")

    }

    override fun onItemClick(position: Int) {

        //Evento para llenar los campos de los EditText al hacer click sobre las tarjetas de productos
        findViewById<EditText>(R.id.idBox).setText("" + fruitsList[position].idprod)
        findViewById<EditText>(R.id.prodBox).setText("" + fruitsList[position].prod)
        findViewById<EditText>(R.id.descBox).setText("" + fruitsList[position].description)
        findViewById<EditText>(R.id.priceBox).setText("" + fruitsList[position].price)
    }

    //Funcion para popular la lista de productos basado en los resultados del select de la base de datos
    fun populateFruitsList(table: String){
        // Tomamos como auxiliar la clase DatabaseHelper para realizar operaciones CRUD
        val databaseHelper = DatabaseHelper(this)

        //Eliminamos los valores dentro de la lista mutable para poder mostrar nuevos valores dependiendo de la tabla seleccionada
        fruitsList.clear()

        //Definimos la variable bd como tipo lectura de base de datos
        val bd = databaseHelper.readableDatabase

        //Ejecutamos un query SQL se seleccion para obtener lo almacenado en la tabla de lacteos
        val rs = bd.rawQuery("Select * from $table",null)

        //Funcion ciclica para obtener todos los valores y almacenarlos en la lista mutable
        while(rs.moveToNext()){
            fruitsList.add(Fruit(rs.getInt(0),rs.getString(1),rs.getString(2), rs.getDouble(3)))
        }

        //Funcion ciclica para mostrar en consola los resultados de la lista (Fines de debugging)
        for(item in fruitsList){
            Log.d("Products", item.toString())
        }

        // Notificamos al adapter que ha habido un cambio para que muestre los nuevos resultados
        adapter.notifyDataSetChanged()
    }

}