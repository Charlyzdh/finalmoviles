package com.example.proyectofinalmoviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//Extendemos esta clase con SQLite Helper
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,"Supermarket.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        //Almacenamos las sentencias de para creacion de tablas como query de SQL
        val fruitsTable = "CREATE TABLE Fruits (" +
                "idprod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product TEXT,"+
                "description TEXT,"+
                "price DOUBLE"+
                ")"

        //Executamos el query con el operador !! el cual ayuda a validar que exista la base de datos
        db!!.execSQL(fruitsTable)

        val vegetablesTable = "CREATE TABLE Vegetables (" +
                "idprod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product TEXT,"+
                "description TEXT,"+
                "price DOUBLE"+
                ")"
        db!!.execSQL(vegetablesTable)

        val dairy = "CREATE TABLE Dairy (" +
                "idprod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product TEXT,"+
                "description TEXT,"+
                "price DOUBLE"+
                ")"
        db!!.execSQL(dairy)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //En caso de actualizacion de base de datos, eliminamos las tablas completas y ejecutamos de nuevo los queries de creacion
        val sqlfruits = "DROP TABLE IF EXISTS Fruits"
        db!!.execSQL(sqlfruits)
        val sqlvegetables = "DROP TABLE IF EXISTS Vegetables"
        db!!.execSQL(sqlvegetables)
        val sqldairy = "DROP TABLE IF EXISTS Dairy"
        db!!.execSQL(sqldairy)
        onCreate(db)
    }


    //Funcion para ejecutar el Insert a la base de datos con los parametros que alimentan cada registro
    fun agregar(prod: String, description: String, price: Double, table: String) {

        //Almacenamos en un array los valores
        val datos = ContentValues()
        datos.put("product", prod)
        datos.put("description", description)
        datos.put("price", price)

        //Definimos el tipo de query a realizar, en este caso uno de escritura
        val bd = this.writableDatabase

        //Ejecutamos el query con la funcion .insert incluida en las librerias de SQLite
        bd.insert(table, null, datos)

        //Cerramos la conexion a la base de datos
        bd.close()
    }

    //Funcion para ejecutar el Update a la base de datos con los parametros que alimentan cada registro
    fun actualizar(idprod: String, prod: String, description: String, price: Double, table: String) {

        //Definimos el tipo de query a realizar, en este caso uno de escritura
        val db = this.writableDatabase

        //Almacenamos en un array los valores
        val datos = ContentValues()
        datos.put("product", prod)
        datos.put("description", description)
        datos.put("price", price)

        //Ejecutamos el query con la funcion .update incluida en las librerias de SQLite
        db.update(table, datos, "idprod=?", arrayOf(idprod));

        //Cerramos la conexion a la base de datos
        db.close()
    }

    //Funcion para ejecutar el Delete a la base de datos con los parametros que alimentan cada registro
    fun eliminar(idprod: String, table: String) {
        //Definimos el tipo de query a realizar, en este caso uno de escritura
        val db = this.writableDatabase

        //Ejecutamos el query con la funcion .delete incluida en las librerias de SQLite
        db.delete(table,"idprod=?", arrayOf(idprod));

        //Cerramos la conexion a la base de datos
        db.close()
    }

}