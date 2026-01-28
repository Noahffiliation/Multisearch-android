package com.example.multisearch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private val mediaWebsites = listOf(
        "https://letterboxd.com/search/",
        "https://trakt.tv/search?query=",
        "https://myanimelist.net/search/all?q=",
        "https://mydramalist.com/search?q="
    )

    private val songWebsites = listOf(
        "https://www.enchor.us/?name=",
        "https://beatsaver.com/?q="
    )

    private val gameWebsites = listOf(
        "https://www.notion.so/noahffiliation/61f7093e99ed455fb4e497d2da55873f?v=10901b25e6fa41be83893d27b81a58c9",
        "https://www.backloggd.com/search/games/",
        "https://store.steampowered.com/search/?term=",
        "https://store.playstation.com/en-us/search/",
        "https://www.gog.com/en/games?query=",
        "https://store.rockstargames.com/search?query=",
        "https://store.epicgames.com/en-US/browse?q="
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaButton = findViewById<Button>(R.id.mediaButton)
        val songButton = findViewById<Button>(R.id.songButton)
        val gameButton = findViewById<Button>(R.id.gameButton)
        val mediaSearch = findViewById<EditText>(R.id.mediaSearch)
        val songSearch = findViewById<EditText>(R.id.songSearch)
        val gameSearch = findViewById<EditText>(R.id.gameSearch)

        mediaButton.setOnClickListener { openTabs(mediaWebsites, mediaSearch) }
        songButton.setOnClickListener { openTabs(songWebsites, songSearch) }
        gameButton.setOnClickListener { openTabs(gameWebsites, gameSearch) }
    }

    private fun openTabs(websites: List<String>, searchEditText: EditText) {
        val searchValue = searchEditText.text.toString()

        if (searchValue.isBlank()) return

        for (website in websites) {
            val finalUrl = SearchHelper.createUrl(website, searchValue)
            if (finalUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, finalUrl.toUri())
                startActivity(intent)
            }
        }
    }
}
