package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
public class EmailUpdates extends AppCompatActivity {
    String EmailUpName, EmailUpDate ;
    EditText emailupdatesname, emailupdatesdate;
    Button EmailUpButton;
    Boolean counter = true;
    DatabaseReference databaseEmailup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_updates);
        emailupdatesname =findViewById(R.id.emailname);
        emailupdatesdate =findViewById(R.id.fromdate);
        EmailUpButton=findViewById(R.id.sharebutton);
        EmailUpName= emailupdatesname.getText().toString();
        EmailUpDate= emailupdatesdate.getText().toString();
        findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
        counter=true;
        EmailUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceFile();
                findViewById(R.id.EmailUpProg).setVisibility(View.VISIBLE);
            }
        });
    }
    public void AttendanceFile(){
        emailupdatesname =findViewById(R.id.emailname);
        emailupdatesdate =findViewById(R.id.fromdate);
        EmailUpButton=findViewById(R.id.sharebutton);
        EmailUpName= emailupdatesname.getText().toString();
        EmailUpDate= emailupdatesdate.getText().toString();
        int i;
        String compareString =(EmailUpDate.substring(0,2));
        String compareDate =(EmailUpDate.substring(5,7));
        int compdate = Integer.parseInt(compareDate);
        if(compareString.equals("01") || compareString.equals ("03")||
                compareString.equals("05") || compareString.equals("07")
                ||compareString.equals("08")||compareString.equals("10")||compareString.equals("12"
        )) {
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            for (i = 1; i<=31 ; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup=
                        FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue)
                ;
                databaseEmailup.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String a1
                                    =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String a2 ="Present";
                            data.append("\n"+a1+","+a2);
                        }
                        else{
                            String a3=("\n"+ childInsert+ ",Absent");
                            data.append(a3);
                        }
                        shareCSV(data);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            shareCSV(data);
        }
        else if(compareString.equals("04")|| compareString.equals("06")||
                compareString.equals("09")||compareString.equals("11")) {
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            for (i = 1; i <= 30; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" +
                        EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup=
                        FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue)
                ;
                databaseEmailup.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot
                                                     dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String b1
                                    =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String b2 ="Present";
                            data.append("\n"+b1+","+b2);
                        }
                        else{
                            String b3=("\n"+ childInsert+ ",Absent");
                            data.append(b3);
                        }
                        shareCSV(data);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError
                                                    databaseError) {
                    }
                });
            }
        }
        else if(compareString.equals("02")&& compdate % 4 != 0) {
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            for (i = 1; i <= 28; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" +
                        EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup=
                        FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue)
                ;
                databaseEmailup.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot
                                                     dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String c1
                                    =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String c2 ="Present";
                            data.append("\n"+c1+","+c2);
                        }
                        else{
                            String c3=("\n"+ childInsert+ ",Absent");
                            data.append(c3);
                        }
                        shareCSV(data);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError
                                                    databaseError) {
                    }
                });
            }
        }
        else if(compareString.equals("02") && compdate % 4 == 0){
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            for (i = 1; i <= 29; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup=
                        FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue)
                ;
                databaseEmailup.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String d1
                                    =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String d2 ="Present";
                            data.append("\n"+d1+","+d2);
                        }
                        else{
                            String d3=("\n"+ childInsert+ ",Absent");
                            data.append(d3);
                        }
                        shareCSV(data);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }
        else{
            Toast.makeText(EmailUpdates.this, "Invalid Name or Date Entered",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void shareCSV(StringBuilder data) {
        try {
            FileOutputStream out =
                    openFileOutput(EmailUpName+EmailUpDate+"Attendance.csv",MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(),
                    EmailUpName+EmailUpDate+"Attendance.csv");
            Uri path = FileProvider.getUriForFile(context,
                    "com.example.attendancemanagementsystem.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
            startActivity(Intent.createChooser(fileIntent, "Send Attendance"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}