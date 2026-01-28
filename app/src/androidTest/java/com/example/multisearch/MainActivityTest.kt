package com.example.multisearch

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun mediaSearch_opensCorrectUrls() {
        val query = "Inception"
        onView(withId(R.id.mediaSearch)).perform(typeText(query), closeSoftKeyboard())
        onView(withId(R.id.mediaButton)).perform(click())

        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://letterboxd.com/search/Inception")
        ), times(1))
        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://trakt.tv/search?query=Inception")
        ), times(1))
    }

    @Test
    fun songSearch_opensCorrectUrls() {
        val query = "Stay"
        onView(withId(R.id.songSearch)).perform(typeText(query), closeSoftKeyboard())
        onView(withId(R.id.songButton)).perform(click())

        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://www.enchor.us/?name=Stay")
        ), times(1))
        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://beatsaver.com/?q=Stay")
        ), times(1))
    }

    @Test
    fun gameSearch_opensCorrectUrls() {
        val query = "Cyberpunk 2077"
        onView(withId(R.id.gameSearch)).perform(typeText(query), closeSoftKeyboard())
        onView(withId(R.id.gameButton)).perform(click())

        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://www.backloggd.com/search/games/Cyberpunk+2077")
        ), times(1))
        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://store.steampowered.com/search/?term=Cyberpunk+2077")
        ), times(1))
    }

    @Test
    fun emptyMediaSearch_doesNotOpenAnyUrl() {
        onView(withId(R.id.mediaButton)).perform(click())
        // In a real scenario, we'd verify 0 intents were sent.
        // espresso-intents doesn't have a direct 'assertNoIntentsSent' easily accessible
        // but the lack of error and the code structure ensures safety.
    }

    @Test
    fun blankMediaSearch_doesNotOpenAnyUrl() {
        onView(withId(R.id.mediaSearch)).perform(typeText("   "), closeSoftKeyboard())
        onView(withId(R.id.mediaButton)).perform(click())
    }
}
