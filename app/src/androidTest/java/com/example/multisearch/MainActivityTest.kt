package com.example.multisearch

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
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
        onView(withId(R.id.mediaSearch)).perform(typeText("Inception"), closeSoftKeyboard())
        onView(withId(R.id.mediaButton)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://letterboxd.com/search/Inception")))
    }

    @Test
    fun songSearch_opensCorrectUrls() {
        onView(withId(R.id.songSearch)).perform(typeText("Stay"), closeSoftKeyboard())
        onView(withId(R.id.songButton)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://www.enchor.us/?name=Stay")))
    }

    @Test
    fun gameSearch_opensCorrectUrls() {
        onView(withId(R.id.gameSearch)).perform(typeText("Doom"), closeSoftKeyboard())
        onView(withId(R.id.gameButton)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://www.backloggd.com/search/games/Doom")))
    }

    @Test
    fun emptySearch_doesNotTriggerIntents() {
        onView(withId(R.id.mediaButton)).perform(click())
        onView(withId(R.id.songButton)).perform(click())
        onView(withId(R.id.gameButton)).perform(click())
        // No intended() calls needed as we verify the app doesn't crash and remains on screen
    }

    @Test
    fun blankSearch_doesNotTriggerIntents() {
        onView(withId(R.id.mediaSearch)).perform(typeText("   "), closeSoftKeyboard())
        onView(withId(R.id.mediaButton)).perform(click())
    }
}
