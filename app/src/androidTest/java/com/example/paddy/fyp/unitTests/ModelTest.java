package com.example.paddy.fyp.unitTests;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.paddy.fyp.models.Exercise;
import com.example.paddy.fyp.models.ExerciseSet;
import com.example.paddy.fyp.models.LogItem;
import com.example.paddy.fyp.models.RoutineExercise;
import com.example.paddy.fyp.models.RoutineHome;
import com.example.paddy.fyp.models.StatsHome;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ModelTest {

    @Test
    public void test_exercise_is_parcelable() {
        Exercise exercise = new Exercise("Bench Press", "Chest");

        Parcel parcel = Parcel.obtain();
        exercise.writeToParcel(parcel, exercise.describeContents());
        parcel.setDataPosition(0);

        Exercise createdFromParcel = Exercise.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getName(), is("Bench Press"));
        assertThat(createdFromParcel.getCategory(), is("Chest"));
    }

    @Test
    public void test_exerciseset_is_parcelable() {
        ExerciseSet exerciseSet = new ExerciseSet(1, "Bench Press", 1, "100 x 10", "1", 100, 10, "10 Jan", 1000, 110);

        Parcel parcel = Parcel.obtain();
        exerciseSet.writeToParcel(parcel, exerciseSet.describeContents());
        parcel.setDataPosition(0);

        ExerciseSet createdFromParcel = ExerciseSet.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getId(), is(1));
        assertThat(createdFromParcel.getName(), is("Bench Press"));
        assertThat(createdFromParcel.getWorkoutID(), is(1));
        assertThat(createdFromParcel.getParameters(), is("100 x 10"));
        assertThat(createdFromParcel.getNumber(), is("1"));
        assertThat(createdFromParcel.getWeight(), is(100));
        assertThat(createdFromParcel.getReps(), is(10));
        assertThat(createdFromParcel.getTimestamp(), is("10 Jan"));
        assertThat(createdFromParcel.getVolume(), is(1000));
        assertThat(createdFromParcel.getOnerepmax(), is(110));
    }

    @Test
    public void test_logitem_is_parcelable() {
        LogItem logItem = new LogItem("Push", "Bench Press, Lat Pulldown, Laterial Raises", "10-Jan");

        Parcel parcel = Parcel.obtain();
        logItem.writeToParcel(parcel, logItem.describeContents());
        parcel.setDataPosition(0);

        LogItem createdFromParcel = LogItem.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getTitle(), is("Push"));
        assertThat(createdFromParcel.getContent(), is("Bench Press, Lat Pulldown, Laterial Raises"));
        assertThat(createdFromParcel.getTimestamp(), is("10-Jan"));
    }

    @Test
    public void test_routine_home_is_parcelable() {
        RoutineHome routineHome = new RoutineHome("Push");

        Parcel parcel = Parcel.obtain();
        routineHome.writeToParcel(parcel, routineHome.describeContents());
        parcel.setDataPosition(0);

        RoutineHome createdFromParcel = RoutineHome.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getTitle(), is("Push"));
    }

    @Test
    public void test_routine_exercise_is_parcelable() {
        RoutineExercise routineExercise = new RoutineExercise("Squat", "Legs", 2);

        Parcel parcel = Parcel.obtain();
        routineExercise.writeToParcel(parcel, routineExercise.describeContents());
        parcel.setDataPosition(0);

        RoutineExercise createdFromParcel = RoutineExercise.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getName(), is("Squat"));
        assertThat(createdFromParcel.getCategory(), is("Legs"));
        assertThat(createdFromParcel.getLogId(), is(2));
    }

    @Test
    public void test_stats_is_parcelable() {
        StatsHome statsHome = new StatsHome("Bodyweight");

        Parcel parcel = Parcel.obtain();
        statsHome.writeToParcel(parcel, statsHome.describeContents());
        parcel.setDataPosition(0);

        StatsHome createdFromParcel = StatsHome.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getTitle(), is("Bodyweight"));
    }

}
