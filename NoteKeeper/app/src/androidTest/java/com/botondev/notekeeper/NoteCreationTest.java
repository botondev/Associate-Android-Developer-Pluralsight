package com.botondev.notekeeper;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matcher.*;
//import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.*;


/**
 * Created by Botond Bertalan on 30/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
    static DataManager sDataManager;

    @BeforeClass
    public static void classSetUp() throws Exception{
        sDataManager = DataManager.getInstance();
    }

    //This makes sure that the right activity will be launched
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule = new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        final CourseInfo course = sDataManager.getCourse("java_lang");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of our test note";
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course)))
                .perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(course.getTitle())));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));

        Espresso.pressBack();

        int noteIndex = sDataManager.getNotes().size() - 1;
        NoteInfo note = sDataManager.getNotes().get(noteIndex);

        assertEquals(course, note.getCourse());
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteText, note.getText());

    }

}