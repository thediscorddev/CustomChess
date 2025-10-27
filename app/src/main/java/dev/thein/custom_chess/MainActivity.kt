package dev.thein.custom_chess

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import dev.thein.custom_chess.R

class MainActivity : AppCompatActivity() {
    fun InitMenuButton()
    {
       val homeButton = findViewById<Button>(R.id.homeButton) 
       val AllGamesButton = findViewById<Button>(R.id.AllGamesButton) 
       val settingsButton = findViewById<Button>(R.id.settingsButton) 
       val AboutButton = findViewById<Button>(R.id.AboutButton) 
       homeButton.setOnClickListener {
        InitMainView()
       }
       AllGamesButton.setOnClickListener {
        SwitchToAllGames()
       }
       settingsButton.setOnClickListener {
        SwitchToSettings()
       }
       AboutButton.setOnClickListener {
        SwitchToAbout()
       }
    }
    fun SwitchToSettings()
    {
        setContentView(R.layout.activity_settings)
        // listen to button
        InitMenuButton()
    }
    fun SwitchToAllGames()
    {
        setContentView(R.layout.activity_allgames)
        // listen to button
        InitMenuButton()
    }
    fun SwitchToAbout()
    {
        setContentView(R.layout.activity_about)
        // listen to button
        InitMenuButton()
    }
    fun InitMainView()
    {
        setContentView(R.layout.activity_main)
        // listen to button
        InitMenuButton()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
        if(prefs.contains("username"))
        {
            val username = prefs.getString("username", "")
            InitMainView()
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
                    InitMainView()
                } else {
                    StatusText.text = "Username cannot be empty"
                    StatusText.setTextColor(android.graphics.Color.parseColor("#FF8800"))

                }
            }

        }
    }
}
