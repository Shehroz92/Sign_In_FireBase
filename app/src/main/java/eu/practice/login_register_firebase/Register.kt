package eu.practice.login_register_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var editTextEmail : TextInputEditText
    private lateinit var editTextPassword : TextInputEditText
    private lateinit var buttonReg : Button
    private lateinit var auth : FirebaseAuth
    private lateinit var progressBar :ProgressBar
    private lateinit var logInPage : TextView


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_Register)
        progressBar =  findViewById(R.id.progressBar)
        logInPage = findViewById(R.id.loginNow)

        logInPage.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val   email  :String
            val password :String

            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this@Register , "Please insert Email" ,Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(this@Register , "Please insert Password" ,Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        // If sign in successful, display a message to the user.
                        Toast.makeText(baseContext, "Account Created.", Toast.LENGTH_SHORT,).show()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,).show()

                    }
                }
        }
    }
}