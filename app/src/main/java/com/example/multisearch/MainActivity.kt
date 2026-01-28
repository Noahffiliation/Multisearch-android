package com.example.multisearch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaButton = findViewById<Button>(R.id.mediaButton)
        val songButton = findViewById<Button>(R.id.songButton)
        val gameButton = findViewById<Button>(R.id.gameButton)
        val mediaSearch = findViewById<EditText>(R.id.mediaSearch)
        val songSearch = findViewById<EditText>(R.id.songSearch)
        val gameSearch = findViewById<EditText>(R.id.gameSearch)

        mediaButton.setOnClickListener { openTabs("media", mediaSearch) }
        songButton.setOnClickListener { openTabs("song", songSearch) }
        gameButton.setOnClickListener { openTabs("game", gameSearch) }
    }

    private fun openTabs(category: String, searchEditText: EditText) {
        val searchValue = searchEditText.text.toString()
        val urls = SearchHelper.getUrlsForCategory(category, searchValue)

        for (url in urls) {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }
    }
}
