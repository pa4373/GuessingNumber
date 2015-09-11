package com.pa4373.guessingnumber;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pa4373.guessingnumber.logic.NumberGuesser;
import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends AppCompatActivity {

    private NumberGuesser ng;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onOKButtonClicked(View view) {
        if (sharedPref == null) {
            this.sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        }

        if (ng == null) {
            Integer maxBound = Integer.parseInt(sharedPref.getString("max_bound", "1000"));
            this.ng = new NumberGuesser(maxBound);
        }

        EditText numberView = (EditText) findViewById(R.id.numberField);
        String numberString = numberView.getText().toString();

        numberView.setText("");

        String message;
        if(!numberString.matches("\\d+")) {
            message = "The input isn't an unsigned integer.";
        } else {
            Integer number = Integer.parseInt(numberString);
            NumberGuesser.Outcome oc = this.ng.guess(number);
            switch (oc) {
                case LARGER:
                    message = String.format("larger than %d.", number);
                    break;
                case SMALLER:
                    message = String.format("smaller than %d.", number);
                    break;
                default:
                    message = "Boom! You can try again.";
                    this.ng.reset();
                    if (sharedPref.getBoolean("is_animation_enable", true)) {
                        new ParticleSystem(this, 100, R.drawable.star_pink, 800)
                                .setSpeedRange(0.2f, 0.5f)
                                .oneShot(view, 100);
                    }
                    break;
            }
        }
        TextView tv = (TextView) findViewById(R.id.msg);
        tv.setText(message);
    }
}
