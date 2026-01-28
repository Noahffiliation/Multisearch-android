package com.example.multisearch

import java.net.URLEncoder

object SearchHelper {
    fun createUrl(baseUrl: String, query: String): String {
        if (query.isBlank()) return ""
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        return baseUrl + encodedQuery
    }
}
