package com.example.proyectofinalmoviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class SupermarketActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        // Retrieve user details from intent and display in UI
        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("displayName")
        val photoUrl = intent.getStringExtra("photoUrl")


        findViewById<TextView>(R.id.display_name_text_view).text = displayName

        Glide.with(this)
            .load(photoUrl)
            .circleCrop()
            .into(findViewById<ImageView>(R.id.profile_image_view))

        // Handle category buttons clicks
        findViewById<TextView>(R.id.fruits_button).setOnClickListener {
            // Handle fruits category click
        }

        findViewById<TextView>(R.id.vegetables_button).setOnClickListener {
            // Handle vegetables category click
        }

        findViewById<TextView>(R.id.dairy_button).setOnClickListener {
            // Handle dairy products category click
        }

        findViewById<TextView>(R.id.sign_out_button).setOnClickListener {
            Toast.makeText(this,"Hasta pronto " + displayName + "!",Toast.LENGTH_SHORT).show()
            signOut()
        }


        // Add click listeners for other buttons as needed
    }


    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                navigateToLogin()
            }
            .addOnFailureListener(this) { e ->
                // Handle sign out failure
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}