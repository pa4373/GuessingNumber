package com.pa4373.guessingnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Integer targetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();
        this.targetNumber = rand.nextInt(1000);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onOKButtonClicked(View view) {
        EditText numberView = (EditText) findViewById(R.id.numberField);
        String numberString = numberView.getText().toString();

        numberView.setText("");

        String message;
        if(!numberString.matches("\\d+")) {
            message = "The input isn't an unsigned integer.";
        } else {
            Integer number = Integer.parseInt(numberString);
            if(number > this.targetNumber) {
                message = String.format("smaller than %d.", number);
            } else if (number < this.targetNumber) {
                message = String.format("larger than %d.", number);
            } else {
                message = "Boom! You can try again.";
                Random rand = new Random();
                this.targetNumber = rand.nextInt(1000);
            }
        }

        TextView tv = (TextView) findViewById(R.id.msg);
        tv.setText(message);
    }
}
