package com.example.multisearch

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ResolveInfo
import android.content.pm.ServiceInfo
import android.widget.Button
import android.widget.EditText
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
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

                intended(
                    allOf(
                        hasAction(Intent.ACTION_VIEW),
                        hasData("https://letterboxd.com/search/Inception"),
                    ),
                )
            }
        }
    }

    @Test
    @Suppress("DEPRECATION")
    fun mediaButton_clicks_withBrowserPackage_setsPackageOnIntent() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val packageManager = shadowOf(appContext.packageManager)

        val appInfo =
            android.content.pm.ApplicationInfo().apply {
                packageName = "com.android.chrome"
                flags = android.content.pm.ApplicationInfo.FLAG_INSTALLED
            }
        val pkgInfo =
            android.content.pm.PackageInfo().apply {
                packageName = "com.android.chrome"
                applicationInfo = appInfo
            }
        packageManager.installPackage(pkgInfo)

        val serviceResolveInfo =
            ResolveInfo().apply {
                serviceInfo =
                    ServiceInfo().apply {
                        packageName = "com.android.chrome"
                        name = "com.android.chrome.CustomTabsService"
                        applicationInfo = appInfo
                    }
            }
        val activityResolveInfo =
            ResolveInfo().apply {
                activityInfo =
                    ActivityInfo().apply {
                        packageName = "com.android.chrome"
                        name = "com.android.chrome.Main"
                        applicationInfo = appInfo
                    }
            }

        packageManager.addResolveInfoForIntent(
            Intent("android.support.customtabs.action.CustomTabsService"),
            serviceResolveInfo,
        )
        packageManager.addResolveInfoForIntent(
            Intent("android.support.customtabs.action.CustomTabsService").setPackage("com.android.chrome"),
            serviceResolveInfo,
        )
        packageManager.addResolveInfoForIntent(
            Intent(Intent.ACTION_VIEW, "http://www.example.com".toUri()),
            activityResolveInfo,
        )
        packageManager.addResolveInfoForIntent(
            Intent(Intent.ACTION_VIEW, "http://".toUri()),
            activityResolveInfo,
        )

        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val editText = activity.findViewById<EditText>(R.id.mediaSearch)
                val button = activity.findViewById<Button>(R.id.mediaButton)

                editText.setText("Inception")
                button.performClick()

                intended(
                    allOf(
                        hasAction(Intent.ACTION_VIEW),
                        hasData("https://letterboxd.com/search/Inception"),
                        hasPackage("com.android.chrome"),
                    ),
                )
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

                intended(
                    allOf(
                        hasAction(Intent.ACTION_VIEW),
                        hasData("https://www.enchor.us/?name=Stay"),
                    ),
                )
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

                intended(
                    allOf(
                        hasAction(Intent.ACTION_VIEW),
                        hasData("https://www.backloggd.com/search/games/Doom"),
                    ),
                )
            }
        }
    }

    @Test
    fun emptySearch_doesNotOpenIntent() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val mediaButton = activity.findViewById<Button>(R.id.mediaButton)
                val songButton = activity.findViewById<Button>(R.id.songButton)
                val gameButton = activity.findViewById<Button>(R.id.gameButton)

                mediaButton.performClick()
                songButton.performClick()
                gameButton.performClick()

                assertTrue(Intents.getIntents().isEmpty())
            }
        }
    }
}
