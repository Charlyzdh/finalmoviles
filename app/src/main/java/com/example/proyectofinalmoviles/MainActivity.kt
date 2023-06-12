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
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }

        findViewById<Button>(R.id.credits_button).setOnClickListener {
            val intent = Intent(this, Credits::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener(this) { e ->
                Log.e("LoginActivity", "Sign out failed", e)
                Toast.makeText(this, "Sign out failed", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Sign-in successful", Toast.LENGTH_SHORT).show()
            // You can now use the account object to access user details
            val email = account?.email
            val displayName = account?.displayName
            val photoUrl = account?.photoUrl

            showSupermarketApp(account)

            // Proceed with your app's logic here...

        } catch (e: ApiException) {
            // The ApiException status code indicates the sign-in attempt failed.
            Log.e("LoginActivity", "signInResult:failed code=${e.statusCode}")
            Toast.makeText(this, "Sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSupermarketApp(account: GoogleSignInAccount?) {
        val intent = Intent(this, SupermarketActivity::class.java)
        intent.putExtra("email", account?.email)
        intent.putExtra("displayName", account?.displayName)
        intent.putExtra("photoUrl", account?.photoUrl?.toString())
        startActivity(intent)
        finish()
    }
}