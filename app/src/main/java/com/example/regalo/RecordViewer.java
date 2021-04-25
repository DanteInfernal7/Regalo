package com.example.regalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.w3c.dom.Text;
public class RecordViewer extends AppCompatActivity {
    String ViewName, ViewDate ;
    EditText viewrecordname, viewrecorddate;
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