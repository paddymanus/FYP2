package com.example.paddy.fyp.automatedTests;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.example.paddy.fyp.ExerciseLogListActivity;
import com.example.paddy.fyp.R;
import com.example.paddy.fyp.home.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExerciseLogListTest {

    @Rule
    public ActivityTestRule<ExerciseLogListActivity> activityRule2 = new ActivityTestRule<>(ExerciseLogListActivity.class);

    @Test
    public void isVisible(){
        onView(ViewMatchers.withId(R.id.name_edit_text)).check(matches(isCompletelyDisplayed()));
    }
}
