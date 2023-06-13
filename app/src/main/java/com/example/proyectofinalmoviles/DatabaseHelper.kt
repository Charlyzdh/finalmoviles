package com.example.proyectofinalmoviles

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getLongOrNull


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,"Supermarket.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val fruitsTable = "CREATE TABLE Fruits (" +
                "idprod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product TEXT,"+
                "description TEXT,"+
                "price DOUBLE"+
                ")"
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


    }//onCreate
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlfruits = "DROP TABLE IF EXISTS fruitsTable"
        db!!.execSQL(sqlfruits)
        val sqlvegetables = "DROP TABLE IF EXISTS vegetablesTable"
        db!!.execSQL(sqlvegetables)
        val sqldairy = "DROP TABLE IF EXISTS dairy"
        db!!.execSQL(sqldairy)
        onCreate(db)
    }// onUpgrade


    fun agregar(prod: String, description: String, price: Double, table: String) {
        val datos = ContentValues()
        datos.put("product", prod)
        datos.put("description", description)
        datos.put("price", price)
        val bd = this.writableDatabase
        bd.insert(table, null, datos)
        Log.d("Agregado!","Agregado")
        bd.close()
    }//agregar

    fun mostrar(table: String){
        val bd = this.readableDatabase
        val rs = bd.rawQuery("Select * from Fruits",null)

        if(rs.moveToNext()){
            Log.d("Text","Products: " + rs.getString(1) + "\nDescription: " + rs.getString(2))
        }
    }


}//class