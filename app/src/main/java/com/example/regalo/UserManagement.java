package com.example.regalo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserManagement extends AppCompatActivity {
    Button user, admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        user = (Button) findViewById(R.id.adminbutton);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserManagement.this, ManageUser.class));
            }
        });
        admin = (Button) findViewById(R.id.adminbutton);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserManagement.this, ManageAdmin.class));
            }
        });
    }
}
