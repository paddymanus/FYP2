package com.example.paddy.fyp.automatedTests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;


import com.example.paddy.fyp.R;
import com.example.paddy.fyp.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogTest {

    // Rule used to launch activity for testing
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    // Scroll to position 0 on recyclerview and click, navigates to new activity
    @Test
    public void scrollToPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.rvLogItem)).perform
                (RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    // Click on fab button to navigate to new ExerciseLogListActivity
    @Test
    public void newActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(click());
    }

    // Click on routines in navigation bar to navigate to routines activity
    @Test
    public void routinesNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_routines)).perform(click());
    }

    // Click on stats in navigation bar to navigate to stats activity
    @Test
    public void statsNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_Stats)).perform(click());
    }


}
