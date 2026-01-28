package com.example.multisearch

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchHelperTest {

    @Test
    fun createUrl_encodesQueryCorrectly() {
        val baseUrl = "https://example.com/search?q="
        val query = "hello world"
        val expected = "https://example.com/search?q=hello+world"
        val actual = SearchHelper.createUrl(baseUrl, query)
        assertEquals(expected, actual)
    }

    @Test
    fun createUrl_handlesEmptyQuery() {
        assertEquals("", SearchHelper.createUrl("http://test.com", ""))
    }

    @Test
    fun createUrl_handlesBlankQuery() {
        assertEquals("", SearchHelper.createUrl("http://test.com", "   "))
    }

    @Test
    fun getUrlsForCategory_media() {
        val urls = SearchHelper.getUrlsForCategory("media", "Inception")
        assertTrue(urls.contains("https://letterboxd.com/search/Inception"))
        assertEquals(SearchHelper.mediaWebsites.size, urls.size)
    }

    @Test
    fun getUrlsForCategory_song() {
        val urls = SearchHelper.getUrlsForCategory("song", "Stay")
        assertTrue(urls.contains("https://www.enchor.us/?name=Stay"))
        assertEquals(SearchHelper.songWebsites.size, urls.size)
    }

    @Test
    fun getUrlsForCategory_game() {
        val urls = SearchHelper.getUrlsForCategory("game", "Doom")
        assertTrue(urls.contains("https://www.backloggd.com/search/games/Doom"))
        assertEquals(SearchHelper.gameWebsites.size, urls.size)
    }

    @Test
    fun getUrlsForCategory_invalid() {
        val urls = SearchHelper.getUrlsForCategory("invalid", "test")
        assertTrue(urls.isEmpty())
    }

    @Test
    fun getUrlsForCategory_blankQuery() {
        val urls = SearchHelper.getUrlsForCategory("media", "  ")
        assertTrue(urls.isEmpty())
    }
}
