package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmailUpdates extends AppCompatActivity {
    int year, month, day;
    String EmailUpName, EmailUpDate ;
    EditText emailupdatesname, emailupdatesdate,dateformat1;
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
        dateformat1 = findViewById(R.id.fromdate);
        dateformat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                dateformat1.setText("");
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EmailUpdates.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DATE,day);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy");
                        dateformat1.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

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
        if(compareString.equals("01") || compareString.equals ("03")|| compareString.equals("05") || compareString.equals("07") ||compareString.equals("08")||compareString.equals("10")||compareString.equals("12")) {
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            final int[] temp = {1};
            for (i = 1; i<=31 ; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup= FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue);
                databaseEmailup.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String a1 =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String a2 ="Present";
                            data.append("\n"+a1+","+a2);
                            temp[0] = temp[0]+1;
                        }
                        else{
                            String a3=("\n"+ childInsert+ ",Absent");
                            data.append(a3);
                            temp[0] = temp[0]+1;
                        }

                        if(temp[0]==31){
                            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
                            shareCSV(data);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        }
        else if(compareString.equals("04")|| compareString.equals("06")|| compareString.equals("09")||compareString.equals("11")) {
            final StringBuilder data = new StringBuilder();
            data.append(EmailUpName + "," + EmailUpDate);
            data.append("\nDate,Attendance");
            final int[] temp = {1};
            for (i = 1; i <= 30; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup= FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue);
                databaseEmailup.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String b1 =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String b2 ="Present";
                            data.append("\n"+b1+","+b2);
                            temp[0] = temp[0]+1;
                        }
                        else{
                            String b3=("\n"+ childInsert+ ",Absent");
                            data.append(b3);
                            temp[0] = temp[0]+1;
                        }
                        if(temp[0]==30){
                            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
                            shareCSV(data);
                        }
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
            final int[] temp = {1};
            for (i = 1; i <= 28; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup= FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue);
                databaseEmailup.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String c1 =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String c2 ="Present";
                            data.append("\n"+c1+","+c2);
                            temp[0] = temp[0]+1;
                        }
                        else{
                            String c3=("\n"+ childInsert+ ",Absent");
                            data.append(c3);
                            temp[0] = temp[0]+1;
                        }
                        if(temp[0]==28){
                            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
                            shareCSV(data);
                        }
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
            final int[] temp = {1};
            for (i = 1; i <= 29; i++) {
                final DecimalFormat twonum = new DecimalFormat("00");
                final String childInsert = twonum.format(i) + "-" + EmailUpDate;
                final String childValue = EmailUpName + childInsert;
                databaseEmailup= FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue);
                databaseEmailup.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String d1 =dataSnapshot.child("attendanceAttend").getValue().toString();
                            String d2 ="Present";
                            data.append("\n"+d1+","+d2);
                            temp[0] = temp[0]+1;
                        }
                        else{
                            String d3=("\n"+ childInsert+ ",Absent");
                            data.append(d3);
                            temp[0] = temp[0]+1;
                        }
                        if(temp[0]==29){
                            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
                            shareCSV(data);
                        }
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
            FileOutputStream out = openFileOutput(EmailUpName+EmailUpDate+"Attendance.csv",Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), EmailUpName+EmailUpDate+"Attendance.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.regalo.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Attendance Report");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            findViewById(R.id.EmailUpProg).setVisibility(View.GONE);
            startActivity(Intent.createChooser(fileIntent, "Send Attendance"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}