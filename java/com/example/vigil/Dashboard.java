package com.example.vigil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    private Button pbtn, mbtn, fbtn, more;
    DatabaseReference reff;
    String count1, count2, count3, lat, lon;
    Integer c1, c2, c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_dashboard);

        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        pbtn = findViewById(R.id.pbtn);
        mbtn = findViewById(R.id.mbtn);
        fbtn = findViewById(R.id.fbtn);
        more = findViewById(R.id.more);
        reff = FirebaseDatabase.getInstance().getReference();

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MoreInfo.class);
                i.putExtra("lat", lat);
                i.putExtra("lon", lon);
                startActivity(i);
                finish();
            }
        });
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count1 = dataSnapshot.child("Police").getValue().toString();
                c1 = Integer.parseInt(count1);
                count2 = dataSnapshot.child("Medical").getValue().toString();
                c2 = Integer.parseInt(count2);
                count3 = dataSnapshot.child("FireDept").getValue().toString();
                c3 = Integer.parseInt(count3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1 = c1+1;
                more.setVisibility(View.VISIBLE);
                reff.child("Police").setValue(c1);
                pbtn.setClickable(false);
                Toast toast=Toast. makeText(getApplicationContext(),"Police Help request has been sent",Toast. LENGTH_SHORT);
                toast. show();
            }
        });
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2 = c2+1;
                more.setVisibility(View.VISIBLE);
                reff.child("Medical").setValue(c2);
                mbtn.setClickable(false);
                Toast toast=Toast. makeText(getApplicationContext(),"Medical Help request has been sent",Toast. LENGTH_SHORT);
                toast. show();
            }
        });
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c3 = c3+1;
                more.setVisibility(View.VISIBLE);
                reff.child("FireDept").setValue(c3);
                fbtn.setClickable(false);
                Toast toast=Toast. makeText(getApplicationContext(),"Fire Dept. Help request has been sent",Toast. LENGTH_SHORT);
                toast. show();
            }
        });
    }
}
