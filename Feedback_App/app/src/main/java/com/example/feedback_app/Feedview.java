package com.example.feedback_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedview extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public String details[] = new String[6];;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedview);

        TextView cde = findViewById(R.id.code);
        final TextView fname = findViewById(R.id.fname);
        final TextView sname = findViewById(R.id.sname);
        final TextView scode = findViewById(R.id.scode);
        final TextView pve = findViewById(R.id.pve);
        final TextView nve = findViewById(R.id.nve);
        final TextView ts = findViewById(R.id.ts);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String code = bundle.getString("code").substring(0,4);
        cde.setText("Code : " + code);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Form/" + code);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Log.e("form details", dataSnapshot.toString());
                    int n = (int) dataSnapshot.getChildrenCount();
                    int i=0;
                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        details[i++] = d.getValue(String.class);
                    }
                    fname.setText("Faculty Name : " + details[2]);
                    sname.setText("Subject Name : " + details[4]);
                    scode.setText("Subject Code : " + details[3]);
                    pve.setText("Positive : " + details[0]);
                    nve.setText("Negative : " + details[1]);
                    ts.setText("Timestamp : " + details[5]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });

    }
}
