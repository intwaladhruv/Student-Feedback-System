package com.example.student_feedback;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentFeedback extends AppCompatActivity implements FeedForm.DialogListener {

    TextView codeTextView;
    Button sendCode;
    DatabaseReference ref;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentfeed);

        codeTextView = findViewById(R.id.codesTV);
        sendCode = findViewById(R.id.sendcode);
        ref = FirebaseDatabase.getInstance().getReference().child("Form");

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = codeTextView.getText().toString();
                Log.w("Code: ", code );
                if (code.equals(""))
                {
                    Toast.makeText(StudentFeedback.this, "Please enter code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(code))
                            {
                                Toast.makeText(StudentFeedback.this, "Default is Good", Toast.LENGTH_LONG).show();
                                openDialog();
                                ref.removeEventListener(this);
                            }
                            else
                            {
                                Toast.makeText(StudentFeedback.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }
    public void openDialog()
    {
        FeedForm feedForm = new FeedForm();
        feedForm.show(getSupportFragmentManager(),"Feedback form");
    }

    @Override
    public void reply(final String state) {
        Toast.makeText(this, "Your feed : " + state, Toast.LENGTH_SHORT).show();
        ref.child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(state.equals("good"))
                {
                    int pve = Integer.parseInt(dataSnapshot.child("+ve").getValue().toString());
                    String p = (pve + 1) + "";
                    ref.child(code).child("+ve").setValue(p);
                    ref.child(code).removeEventListener(this);
                }
                else
                {
                    int nve = Integer.parseInt(dataSnapshot.child("-ve").getValue().toString());
                    String n = (nve + 1) + "";
                    ref.child(code).child("-ve").setValue(n);
                    ref.child(code).removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void positive(View view)
    {
        FeedForm.state = "good";
        view.setPressed(true);
    }
    public void negative(View view)
    {
        FeedForm.state = "bad";
        view.setPressed(true);
    }
}
