package com.example.multisearch

import java.net.URLEncoder

object SearchHelper {

    val mediaWebsites = listOf(
        "https://letterboxd.com/search/",
        "https://trakt.tv/search?query=",
        "https://myanimelist.net/search/all?q=",
        "https://mydramalist.com/search?q="
    )

    val songWebsites = listOf(
        "https://www.enchor.us/?name=",
        "https://beatsaver.com/?q="
    )

    val gameWebsites = listOf(
        "https://www.notion.so/noahffiliation/61f7093e99ed455fb4e497d2da55873f?v=10901b25e6fa41be83893d27b81a58c9",
        "https://www.backloggd.com/search/games/",
        "https://store.steampowered.com/search/?term=",
        "https://store.playstation.com/en-us/search/",
        "https://www.gog.com/en/games?query=",
        "https://store.rockstargames.com/search?query=",
        "https://store.epicgames.com/en-US/browse?q="
    )

    fun createUrl(baseUrl: String, query: String): String {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) return ""
        val encodedQuery = URLEncoder.encode(trimmedQuery, "UTF-8")
        return baseUrl + encodedQuery
    }

    fun getUrlsForCategory(category: String, query: String): List<String> {
        if (query.isBlank()) return emptyList()
        val websites = when (category) {
            "media" -> mediaWebsites
            "song" -> songWebsites
            "game" -> gameWebsites
            else -> return emptyList()
        }
        return websites.map { createUrl(it, query) }
    }
}
