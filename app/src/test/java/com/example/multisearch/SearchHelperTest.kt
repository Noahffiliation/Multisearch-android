package com.example.multisearch

import org.junit.Assert.assertEquals
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
        val baseUrl = "https://example.com/search?q="
        val query = ""
        val expected = ""
        val actual = SearchHelper.createUrl(baseUrl, query)
        assertEquals(expected, actual)
    }

    @Test
    fun createUrl_handlesBlankQuery() {
        val baseUrl = "https://example.com/search?q="
        val query = "   "
        val expected = ""
        val actual = SearchHelper.createUrl(baseUrl, query)
        assertEquals(expected, actual)
    }

    @Test
    fun createUrl_handlesSpecialCharacters() {
        val baseUrl = "https://example.com/search?q="
        val query = "C++ & Java"
        val expected = "https://example.com/search?q=C%2B%2B+%26+Java"
        val actual = SearchHelper.createUrl(baseUrl, query)
        assertEquals(expected, actual)
    }
}
