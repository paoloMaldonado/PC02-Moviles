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


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user: EditText = findViewById(R.id.et_dni)
        val password: EditText = findViewById(R.id.et_password)
        val buttonLogin: Button = findViewById(R.id.btn_ingresar)
        val buttonCreate: Button = findViewById(R.id.btn_crear)

        buttonLogin.setOnClickListener {
            val dni_user = user.text.toString()
            val password_user = password.text.toString()
            var user_accesed = false

            val db = FirebaseFirestore.getInstance()

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(dni_user == document.data["dni"] && password_user == document.data["password"])
                        {
                            Toast.makeText(this, "ACCESO PERMITIDO", Toast.LENGTH_SHORT).show()
                            user_accesed = true
                        }
                    }
                    if(!user_accesed)
                        Toast.makeText(this, "EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

        buttonCreate.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }


    }
}