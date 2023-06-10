package com.example.proyectofinalmoviles

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SupermarketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket)

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("displayName")
        val photoUrl = intent.getStringExtra("photoUrl")

        // Display user information in the UI
        findViewById<TextView>(R.id.email_text_view).text = email
        findViewById<TextView>(R.id.display_name_text_view).text = displayName

        // Load and display the user's profile photo
        Glide.with(this)
            .load(photoUrl)
            .into(findViewById(R.id.profile_image_view))

        // Handle category buttons clicks
        findViewById<Button>(R.id.fruits_button).setOnClickListener {
            // Handle fruits category click
        }

        findViewById<Button>(R.id.vegetables_button).setOnClickListener {
            // Handle vegetables category click
        }

        findViewById<Button>(R.id.dairy_button).setOnClickListener {
            // Handle dairy products category click
        }

        // Add click listeners for other buttons as needed
    }
}