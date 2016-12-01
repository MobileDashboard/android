package com.ondrejd.mobilninastenka;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    //Called when Continue button is clicked.
    public void onClickButton(View view) {
        EditText dashboardCodeView = (EditText)findViewById(R.id.welcome_activity_input);
        String dashboardCode = dashboardCodeView.getText().toString();

        if (dashboardCode.length() == 0) {
            //TODO Show alert message!
            return;
        }

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.DASHBOARD_CODE, dashboardCode);
        startActivity(intent);
    }
}
