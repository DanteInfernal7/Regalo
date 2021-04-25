package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageUser extends AppCompatActivity {
    TextView name, fingerid, emailid;
    Button adduser, deleteuser;
    String Name, Fingerid, Emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        adduser = findViewById(R.id.adduser);
        deleteuser = findViewById(R.id.deleteuser);
        name = findViewById(R.id.name);
        fingerid = findViewById(R.id.fingerid);
        emailid = findViewById(R.id.emailid);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Fingerid = fingerid.getText().toString();
                Emailid = emailid.getText().toString();
                if (Name.isEmpty()|| Fingerid.isEmpty()|| Emailid.isEmpty()) {
                    Toast.makeText(ManageUser.this, "Please do not leaves fields empty", Toast.LENGTH_SHORT).show();
                } else {
                    NewUser newuser = new NewUser(Name,Fingerid,Emailid);

                    databaseReference.child(Fingerid).setValue(newuser);
                    Toast.makeText(ManageUser.this, "User added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Fingerid = fingerid.getText().toString();
                Emailid = emailid.getText().toString();
                if (Name.isEmpty()||Fingerid.isEmpty()||Emailid.isEmpty()) {
                    Toast.makeText(ManageUser.this, "Please do not leaves fields empty", Toast.LENGTH_SHORT).show();
                } else {
                    NewUser newuser = new NewUser(Name,Fingerid,Emailid);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ManageUser.this);
                    dialog.setTitle("Confim your choice");
                    dialog.setMessage("Are you sure you want to delete your account?");
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.child(Fingerid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ManageUser.this, "User Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(ManageUser.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }
}