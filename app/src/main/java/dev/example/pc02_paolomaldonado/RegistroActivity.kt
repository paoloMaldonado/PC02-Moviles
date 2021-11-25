package dev.example.pc02_paolomaldonado

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val r_dni: EditText = findViewById(R.id.field_dni)
        val r_nombre: EditText = findViewById(R.id.field_nombre)
        val r_password: EditText = findViewById(R.id.field_password)
        val r_passwordValid: EditText = findViewById(R.id.field_passwordValid)
        val r_button: Button = findViewById(R.id.btnRegistro)

        r_button.setOnClickListener {
            var password_is_valid = false

            val user = hashMapOf(
                "dni" to r_dni.text.toString(),
                "name" to r_nombre.text.toString(),
                "password" to r_password.text.toString(),
            )

            if(r_password.text.toString() == r_passwordValid.text.toString())
                password_is_valid = true

            if(password_is_valid)
            {
                val db = FirebaseFirestore.getInstance()

                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "CREACION EXITOSA", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "OCURRIO UN ERROR", Toast.LENGTH_LONG).show()
                    }
            }
            else
                Toast.makeText(this, "Las claves no coinciden", Toast.LENGTH_SHORT).show()
        }

    }
}