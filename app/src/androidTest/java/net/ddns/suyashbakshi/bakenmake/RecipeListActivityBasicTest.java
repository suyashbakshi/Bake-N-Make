package net.ddns.suyashbakshi.bakenmake;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.ddns.suyashbakshi.bakenmake.Activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by suyas on 7/3/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void RecipeListClickTest(){
        //1. Find the view
        //2. Perform action on the view
        //3. Check if the view does what you expected

        onData(anything()).inAdapterView(withId(R.id.recipeListView)).atPosition(0).perform(click());
        onView(withId(R.id.recipe_step_list)).check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView(withId(R.id.recipe_step_list)).atPosition(0).perform(click());
        onView(withId(R.id.video_description_tv)).check(matches(isDisplayed()));

    }
}
