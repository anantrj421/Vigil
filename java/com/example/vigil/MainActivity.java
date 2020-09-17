package com.example.vigil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText edt;
//    String app_server_url = "http://192.168.43.11/5000/submit";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        edt = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
//                Toast toast=Toast. makeText(getApplicationContext(),"Your Token "+token,Toast. LENGTH_LONG);
//                toast. show();
                String uid = edt.getText().toString();
                try{
                    myRef.child("users").child(token).child("fcm_token").setValue(token);
                    myRef.child("users").child(token).child("email").setValue(uid);
                    Toast toast=Toast. makeText(getApplicationContext(),"Successfully registered user",Toast. LENGTH_SHORT);
                    toast. show();
                }
                catch (Error error){
                    Toast toast=Toast. makeText(getApplicationContext(),"Error occurred "+error,Toast. LENGTH_SHORT);
                    toast. show();
                }
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        })
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("fcm_token",token);
//
//                        return params;
//                }
//                };
//                MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
            }
        });
    }
}
