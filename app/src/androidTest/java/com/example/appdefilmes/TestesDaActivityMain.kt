package com.example.appdefilmes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.appdefilmes.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TestesDaActivityMain {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerViewSucessoCarregada() {
        onView(withId(R.id.rv_sucesso))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewNovidadesCarregada() {
        onView(withId(R.id.rv_novidades))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewExclusivosCarregada() {
        onView(withId(R.id.fragment_inicio)).perform(swipeUp())
        onView(withId(R.id.rv_exclusivos)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewMinhaListaCarregada() {
        onView(withId(R.id.minhaListaFragment2)).perform(click())
        onView(withId(R.id.rv_minha_lista))
            .check(matches(isDisplayed()))
    }

    @Test
    fun bottomNavigationCarregada() {
        onView(withId(R.id.bottom_navegacao_inicio)).check(matches(isDisplayed()))
    }

    @Test
    fun inicioFragramentCarregada() {
        onView(withId(R.id.inicioFragment2)).perform(click())
        recyclerViewNovidadesCarregada()
        recyclerViewSucessoCarregada()
        recyclerViewExclusivosCarregada()
    }
}