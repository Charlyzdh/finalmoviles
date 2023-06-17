package com.example.proyectofinalmoviles

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SupermarketActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket)

        //Configuramos el constructor de API de google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        // Obtenemos la informacion pasada como parametro sobre la cuenta loggeada
        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("displayName")
        val photoUrl = intent.getStringExtra("photoUrl")


        //Utilizamos solo el nombre de la cuenta para mostrarlo en un TextView
        findViewById<TextView>(R.id.display_name_text_view).text = displayName

        //Utilizamos Glide para mostrar la fotografía de perfil de la cuenta loggeada
        Glide.with(this)
            .load(photoUrl)
            .circleCrop()
            .into(findViewById<ImageView>(R.id.profile_image_view))


        // Asignamos los listeners de clicks a los botones de las categorias como Frutas, Verduras y Lácteos

        findViewById<TextView>(R.id.fruits_button).setOnClickListener {
            val intent = Intent(this, FruitsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.vegetables_button).setOnClickListener {
            val intent = Intent(this, VegetablesActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.dairy_button).setOnClickListener {
            val intent = Intent(this, DairyActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.credits_button).setOnClickListener {
            Toast.makeText(this,"Hasta pronto " + displayName + "!",Toast.LENGTH_SHORT).show()
            signOut()
        }

    }


    //En este intent utilizaremos la funcion de sign out para poder cerrar la sesion de la cuenta loggeada
    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                navigateToLogin() //Con el CompleteListener podemos detectar si la sesion se cerrí correctamente, con esto mandamos llamar la funcion de regreso al login
            }
            .addOnFailureListener(this) { e ->
                //En caso de tener algun error de cierre, lo podemos mostrar aqui ya sea por Toast o en consola
            }
    }


    //Funcion para regresar al login, el cual es la MainActivity
    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}