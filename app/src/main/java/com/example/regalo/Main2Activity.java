package com.example.regalo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button recordentr;
    Button recordview;
    Button usermanage;
    Button emailup;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        recordentr = (Button) findViewById(R.id.recordentry);
        recordentr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, RecordEntry.class));
            }
        });
        recordview = (Button) findViewById(R.id.recordviewer);
        recordview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, RecordViewer.class));
            }
        });
        usermanage = (Button) findViewById(R.id.usermanagement);
        usermanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, UserManagement.class));
            }
        });
        emailup = (Button) findViewById(R.id.emailupdates);
        emailup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, EmailUpdates.class));
            }
        });
        logout = (Button) findViewById(R.id.logoutbutton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(Main2Activity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
    }
}
