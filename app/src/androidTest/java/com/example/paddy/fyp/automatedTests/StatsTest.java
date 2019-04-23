package com.example.paddy.fyp.automatedTests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.paddy.fyp.R;
import com.example.paddy.fyp.routines.RoutinesActivity;
import com.example.paddy.fyp.stats.StatsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StatsTest {

    @Rule
    public ActivityTestRule<StatsActivity> activityRule = new ActivityTestRule<>(StatsActivity.class);

    @Test
    public void scrollToPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.rvStatsHome)).perform
                (RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }


    @Test
    public void homeNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_log)).perform(click());
    }

    @Test
    public void routinesNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_routines)).perform(click());
    }
}
