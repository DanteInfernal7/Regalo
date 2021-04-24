package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {
    String ID, Pass;
    EditText adminid, password;
    Button button;
    private FirebaseAuth mAuth;
    @Override
    public void onBackPressed(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth =FirebaseAuth.getInstance();
        adminid = findViewById(R.id.adminemail);
        password = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        findViewById(R.id.LoginProg).setVisibility(View.GONE);
        ID = adminid.getText().toString();
        Pass = password.getText().toString();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(adminid.getText().toString().isEmpty()||password.getText().toString().isEmpty()||adminid.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please do not leaves fields empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    startSignIn();
                    findViewById(R.id.LoginProg).setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void startSignIn(){
        ID = adminid.getText().toString();
        Pass = password.getText().toString();
        mAuth.signInWithEmailAndPassword(ID,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    findViewById(R.id.LoginProg).setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                    Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                }
                else {
                    findViewById(R.id.LoginProg).setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login(View v){
    }
}
