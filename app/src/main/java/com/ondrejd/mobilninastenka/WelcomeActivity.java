package com.ondrejd.mobilninastenka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Welcome activity with input for dashboard hash.
 * @todo When is there any dashboard hash already saved it should be used so this activity can be skipped.
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    //Called when Continue button is clicked.
    public void onClickButton(View view) {
        EditText dashboardCodeView = (EditText)findViewById(R.id.welcome_activity_input);
        String dashboardHash = dashboardCodeView.getText().toString();

        if (dashboardHash.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.welcome_activity_error_msg, Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.DASHBOARD_HASH, dashboardHash);
        startActivity(intent);
    }
}
