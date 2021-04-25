package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.jar.Attributes;
public class RecordEntry extends AppCompatActivity {
    public String RecName, RecAttend;
    EditText recordname;
    Button enterrecordbutton;
    Spinner spinner;
    DatabaseReference databaseReference;
    DatabaseReference databaseAttedance;
    ValueEventListener listener;
    ArrayAdapter<String> adpt;
    ArrayList<String> spinnerdatalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_entry);
        databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
        databaseAttedance = FirebaseDatabase.getInstance().getReference("Attendance");
        spinnerdatalist= new ArrayList<>();
        adpt= new ArrayAdapter<String>(RecordEntry.this, android.R.layout.simple_spinner_dropdown_item,spinnerdatalist);
        spinner= findViewById(R.id.recordentryname);
        enterrecordbutton = findViewById(R.id.recordentrysubmit);
        spinner.setAdapter(adpt);
        retreivedata();
        enterrecordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordname = findViewById(R.id.recordentertext);
                RecName = recordname.getText().toString();
                RecAttend = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String presenttext= "Present";
                if (RecName.isEmpty()||RecAttend.isEmpty()) {
                    Toast.makeText(RecordEntry.this, "Please do not leaves fields empty", Toast.LENGTH_SHORT).show();
                } else {
                    Attendance attendance = new Attendance(presenttext,RecAttend);

                    databaseAttedance.child(RecName.concat(RecAttend)).setValue(attendance);
                    Toast.makeText(RecordEntry.this, "Record entry success",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void retreivedata(){
        listener = databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    spinnerdatalist.add(item.getValue().toString());
                }
                adpt.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}