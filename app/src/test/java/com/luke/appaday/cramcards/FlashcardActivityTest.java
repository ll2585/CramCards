package com.luke.appaday.cramcards;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml",constants = BuildConfig.class)
public class FlashcardActivityTest {

    @Test
    public void shouldFail() {
        String hello = new CardActivity().getResources().getString(R.string.hello_world);
        assertEquals(hello, "Hello world!");
        assertTrue(true);
    }

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        CardActivity activity = Robolectric.setupActivity(CardActivity.class);

        Button button = (Button) activity.findViewById(R.id.button);
        TextView results = (TextView) activity.findViewById(R.id.textView);

        button.performClick();
        assertThat(results.getText().toString()).isEqualTo("Robolectric Rocks!");
    }
}