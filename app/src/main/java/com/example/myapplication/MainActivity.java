package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refHome = database.getReference("door");
    DatabaseReference refLights, refButtons, refLightRoom;
    ToggleButton btnToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Firebase connection Success", Toast.LENGTH_LONG).show();

        refLights = refHome.child("doors");
        refLightRoom = refLights.child("door_status");

        //refButtons = refHome.child("buttons");
        //refPushButtonA = refButtons.child("push_button_a");

        btnToggle = (ToggleButton) findViewById(R.id.toggleButton);
        btnToggle.setTextOn("DOOR IS OPEN");
        btnToggle.setTextOff("DOOR CLOSED");

        //textSwitchState = (TextView) findViewById(R.id.textViewPulsador);

        controlLED(refLightRoom, btnToggle);

        //pushButtonStatus(refPushButtonA, textSwitchState);
    }

    private void controlLED(final DatabaseReference refLed, final ToggleButton toggle_btn) {

        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refLed.setValue(isChecked);
            }
        });

        refLed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean led_state = (Boolean) dataSnapshot.getValue();
                toggle_btn.setChecked(led_state);
                if (led_state) {
                    toggle_btn.setTextOn("DOOR IS OPEN");
                } else {
                    toggle_btn.setTextOff("DOOR CLOSED");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

}
    /*private void pushButtonStatus(final DatabaseReference refPushButtonA_a, final TextView textSwitchState) {

        refPushButtonA_a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean push_button_status = (Boolean) dataSnapshot.getValue();
                textSwitchState.setText(push_button_status.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });*/


