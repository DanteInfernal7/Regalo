package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecordViewer extends AppCompatActivity {
    int year, month, day;
    String ViewName, ViewDate ;
    EditText viewrecordname, viewrecorddate, dateformat;
    TextView viewrecord;
    Button viewrec;
    DatabaseReference databaseviewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_viewer);
        viewrecordname =findViewById(R.id.viewername);
        viewrecorddate =findViewById(R.id.viewerdate);
        ViewName= viewrecordname.getText().toString();
        ViewDate= viewrecorddate.getText().toString();
        viewrecord = findViewById(R.id.viewerdisplay);
        findViewById(R.id.RecCheckProg).setVisibility(View.GONE);
        viewrec = findViewById(R.id.request);
        dateformat = findViewById(R.id.viewerdate);
        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                dateformat.setText("");
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RecordViewer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DATE,day);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        dateformat.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        viewrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.RecCheckProg).setVisibility(View.VISIBLE);
                viewrecordname =findViewById(R.id.viewername);
                viewrecorddate =findViewById(R.id.viewerdate);
                ViewName= viewrecordname.getText().toString();
                ViewDate= viewrecorddate.getText().toString();
                final String childValue= ViewName + ViewDate;
                databaseviewer = FirebaseDatabase.getInstance().getReference().child("Attendance").child(childValue);
                databaseviewer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String a=dataSnapshot.child("attendanceName").getValue().toString();

                            findViewById(R.id.RecCheckProg).setVisibility(View.GONE);
                            viewrecord.setText(a);
                        }
                        else{

                            findViewById(R.id.RecCheckProg).setVisibility(View.GONE);
                            viewrecord.setText("Absent");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}