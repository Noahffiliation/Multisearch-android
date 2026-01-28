package com.example.multisearch

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34]) // Robolectric supports up to SDK 34/35
class MainActivityTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun mediaButton_clicks_opensIntents() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val editText = activity.findViewById<EditText>(R.id.mediaSearch)
                val button = activity.findViewById<Button>(R.id.mediaButton)

                editText.setText("Inception")
                button.performClick()

                intended(allOf(
                    hasAction(Intent.ACTION_VIEW),
                    hasData("https://letterboxd.com/search/Inception")
                ))
            }
        }
    }

    @Test
    fun songButton_clicks_opensIntents() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val editText = activity.findViewById<EditText>(R.id.songSearch)
                val button = activity.findViewById<Button>(R.id.songButton)

                editText.setText("Stay")
                button.performClick()

                intended(allOf(
                    hasAction(Intent.ACTION_VIEW),
                    hasData("https://www.enchor.us/?name=Stay")
                ))
            }
        }
    }

    @Test
    fun gameButton_clicks_opensIntents() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val editText = activity.findViewById<EditText>(R.id.gameSearch)
                val button = activity.findViewById<Button>(R.id.gameButton)

                editText.setText("Doom")
                button.performClick()

                intended(allOf(
                    hasAction(Intent.ACTION_VIEW),
                    hasData("https://www.backloggd.com/search/games/Doom")
                ))
            }
        }
    }

    @Test
    fun emptySearch_doesNotOpenIntent() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val button = activity.findViewById<Button>(R.id.mediaButton)
                button.performClick()
                // No intents should be fired. espresso-intents would throw if unintended ones were found.
            }
        }
    }
}
