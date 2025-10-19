package dev.thein.custom_chess

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import dev.thein.custom_chess.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
        if(prefs.contains("username"))
        {
            val username = prefs.getString("username", "")
            setContentView(R.layout.activity_main)
        }else {
            setContentView(R.layout.activity_firstlaunch)
            val usernameInput = findViewById<EditText>(R.id.usernameInput)
            val StatusText = findViewById<TextView>(R.id.statusText)
            val SubmitButton = findViewById<Button>(R.id.SubmitButton)
            SubmitButton.setOnClickListener {
                val enteredName = usernameInput.text.toString().trim()
                if (enteredName.isNotEmpty()) {
                    prefs.edit().putString("username", enteredName).apply()
                    StatusText.text = "Hello, $enteredName"
                    setContentView(R.layout.activity_main)
                } else {
                    StatusText.text = "Username cannot be empty"
                    StatusText.setTextColor(android.graphics.Color.parseColor("#FF8800"))

                }
            }

        }
    }
}
