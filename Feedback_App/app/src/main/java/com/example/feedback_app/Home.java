package com.example.feedback_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity implements Form.DiaglogListener {

    private TextView fcode;
    Firebase url;
    public void createFeedback(View view)
    {
        Form form = new Form();
        form.show(getSupportFragmentManager(), "formDialog");
    }

    public void listFeed(View view)
    {
        Intent intent = new Intent(Home.this, feedbackList.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Firebase.setAndroidContext(this);
        fcode = findViewById(R.id.fCode);
        url = new Firebase("https://feedbackapp-3a9e9.firebaseio.com/");
    }

    @Override
    public void applyTexts(String facName, String subCode, String subName) {

        String code = getAlphaNumericString();
        Firebase firebase = url.child("Form").child(code);
        firebase.child("faName").setValue(facName);
        firebase.child("suCode").setValue(subCode);
        firebase.child("suName").setValue(subName);
        firebase.child("+ve").setValue("0");
        firebase.child("-ve").setValue("0");
        String timestamp = getCurrentTimeStamp();
        firebase.child("timeStamp").setValue(timestamp);
        Toast.makeText(this, "Form created successfully", Toast.LENGTH_SHORT).show();
        fcode.setText("Code is : " + code);
    }

    static String getAlphaNumericString()
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
