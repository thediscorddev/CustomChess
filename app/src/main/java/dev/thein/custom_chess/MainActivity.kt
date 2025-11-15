package dev.thein.custom_chess

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.RadioGroup
import dev.thein.custom_chess.R
import android.text.TextWatcher
import android.text.Editable
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {
    private var typingTimer: Job? = null
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
        LoadSettingsValue()
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
    fun LoadSettingsValue()
        {
            // This is called when we click into settings
            val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
            var theme = 0
            if(prefs.contains("theme"))
            {
                theme = prefs.getInt("theme",0)
            }
            var username = prefs.getString("username","")
            val ThemeGroup = findViewById<RadioGroup>(R.id.ThemeGroup)
            ThemeGroup.setOnCheckedChangeListener{gr, id -> 
                when(id)
                {
                    R.id.radioWhite -> {
                        prefs.edit().putInt("theme",0)
                    }
                    R.id.radioBlack -> {
                        prefs.edit().putInt("theme",1)
                    }
                }
            }
            if(theme == 0) ThemeGroup.check(R.id.radioWhite)
            else if (theme == 1) ThemeGroup.check(R.id.radioBlack)
            else {
                ThemeGroup.check(R.id.radioWhite)
                theme = 0
                prefs.edit().putInt("theme",0)
            }
            val UsernameField = findViewById<EditText>(R.id.usernameInput);
            UsernameField.setText(username)
            UsernameField.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) 
                {
                    typingTimer?.cancel()
                    typingTimer = CoroutineScope(Dispatchers.Main).launch {
                        delay(400) 
                        val value = s.toString()
                        username = value
                        prefs.edit().putString("username",username)
                    }
                }
            })
        }
}
