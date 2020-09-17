package com.example.vigil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.bumptech.glide.Glide;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MoreInfo extends AppCompatActivity {
    private StorageReference mStorageRef;
    DatabaseReference reff;
    ImageView imageView;
    TextView stat;
    Button pmap, fmap, mmap;
    String lat, lon;
    String imgurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_more_info);
        imageView = findViewById(R.id.img);
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        reff = FirebaseDatabase.getInstance().getReference();
        stat = findViewById(R.id.status);
        pmap = findViewById(R.id.policemap);
        fmap = findViewById(R.id.firemap);
        mmap = findViewById(R.id.medicalmap);
        imgurl = "images/foobar.jpg";

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pstatus, fstatus, mstatus;
                pstatus = dataSnapshot.child("help").child("police").getValue().toString();
                fstatus = dataSnapshot.child("help").child("fire").getValue().toString();
                mstatus = dataSnapshot.child("help").child("medical").getValue().toString();
                if(pstatus.equals("Unassigned") && fstatus.equals("Unassigned") && mstatus.equals("Unassigned")){
                    stat.setText("Help hasn't been assigned by the authority yet");
                }
                else{
                    stat.setText("Help has been assigned, stay assured");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mStorageRef.child(imgurl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MoreInfo.this, "Couldn't load data, retry later.", Toast.LENGTH_SHORT);
                finish();
            }
        });

        pmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"?q=Police Station Nearby");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        fmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"?q=Fire Station Nearby");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        mmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"?q=nearby hospitals");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }
}
