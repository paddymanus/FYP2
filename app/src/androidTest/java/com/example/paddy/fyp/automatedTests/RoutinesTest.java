package com.example.paddy.fyp.automatedTests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.paddy.fyp.ExerciseLogListActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.home.HomeActivity;
import com.example.paddy.fyp.routines.RoutinesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoutinesTest {

    @Rule
    public ActivityTestRule<RoutinesActivity> activityRule = new ActivityTestRule<>(RoutinesActivity.class);

    @Test
    public void scrollToPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.rvRoutinesHome)).perform
                (RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    @Test
    public void newActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(click());
    }

    @Test
    public void homeNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_log)).perform(click());
    }

    @Test
    public void statsNavigate(){
        Espresso.onView(ViewMatchers.withId(R.id.navigation_Stats)).perform(click());
    }
}
