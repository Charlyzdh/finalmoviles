package com.example.proyectofinalmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient //Variable que almacena las funciones de la API
    private val RC_SIGN_IN = 123 //Numero unico para validar las llamadas a la API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creamos el constructor para el inicio de sesion de Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //Asignamos un listener de click al boton de inicio de sesion, el cual llama la funcion SignIn
        findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }

        //Asignamos un listener de click al boton de creditos, el cual llama a la activity que almacena dicho contenido
        findViewById<Button>(R.id.credits_button).setOnClickListener {
            val intent = Intent(this, Credits::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        //Iniciamos el intent para el inicio de sesion con el constructor de google
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    //Funcion para manejar el resultado del intent de google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Validamos que el codigo de solicitud coincida, para posterior obtener la data de la cuenta
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    //Funcion para manejar continuar despues de haber recibido la informacion del inicio de sesion
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        //Encerramos en try/catch para poder hacer las peticiones sin interrumpir la aplicacion
        try {
            val account = completedTask.getResult(ApiException::class.java)

            //Almacenamos en las variables, la informacion de la cuenta que se pudo loggear de manera satisfactoria
            val email = account?.email
            val displayName = account?.displayName
            val photoUrl = account?.photoUrl

            // Lanzamos un mensaje Toast para mostrar que el inicio de sesion se hizo de manera correcta
            Toast.makeText(this, "Bienvenid@ " + displayName, Toast.LENGTH_SHORT).show()

            //Llamamos la funcion showSupermarketApp con el paso de parametro de la cuenta loggeada
            showSupermarketApp(account)


        } catch (e: ApiException) {
            // Mostramos la posible excepcion que se haya provocado tanto en consola como en mensaje Toast
            Log.e("LoginActivity", "signInResult:failed code=${e.statusCode}")
            Toast.makeText(this, "Sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }

    //Funcion para iniciar el intent con el menu del supermercado
    private fun showSupermarketApp(account: GoogleSignInAccount?) {

        //Al recibir como parametro la cuenta (account), podemos acceder a la informacion de la misma y mandarla para su manejo en el intent de supermercado
        val intent = Intent(this, SupermarketActivity::class.java)
        intent.putExtra("email", account?.email)
        intent.putExtra("displayName", account?.displayName)
        intent.putExtra("photoUrl", account?.photoUrl?.toString())
        startActivity(intent)
    }
}