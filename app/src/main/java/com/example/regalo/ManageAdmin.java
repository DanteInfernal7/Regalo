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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAdmin extends AppCompatActivity {
    TextView pass, emailid;
    Button Addadmin, Deleteadmin;
    String Pass, Emailid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);
        Addadmin = findViewById(R.id.addadmin);
        Deleteadmin = findViewById(R.id.deleteadmin);
        pass = findViewById(R.id.adminpass);
        emailid = findViewById(R.id.adminemail);


        Addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdmin();
            }
        });

        Deleteadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManageAdmin.this);
                dialog.setTitle("Confim your choice");
                dialog.setMessage("Are you sure you want to delete your account?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ManageAdmin.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ManageAdmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void createAdmin() {
        Pass = pass.getText().toString();
        Emailid = pass.getText().toString();
        if (Pass.isEmpty()|| Emailid.isEmpty()) {
            Toast.makeText(ManageAdmin.this, "Please do not leaves fields empty", Toast.LENGTH_SHORT).show();
    }
        else{
            firebaseAuth.createUserWithEmailAndPassword(Emailid,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ManageAdmin.this, "Account Created", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ManageAdmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}