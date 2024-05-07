package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.util.HashMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

public class ProfileRegistration extends AppCompatActivity {
    EditText signupOfficerID, signupOfficerName, signupOfficerPosition;
    Button registerProfileButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    private FirebaseAuth test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_registration);
        test = FirebaseAuth.getInstance();
        FirebaseUser currentUser = test.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        registerProfileButton = findViewById(R.id.register_profile_button);
        signupOfficerID = findViewById(R.id.signup_officerID);
        signupOfficerName = findViewById(R.id.signup_officerName);
        signupOfficerPosition = findViewById(R.id.signup_officerPosition);

        registerProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser!=null) {
                    String uid = currentUser.getUid();
                    String officerID = signupOfficerID.getText().toString();
                    String officerName = signupOfficerName.getText().toString();
                    String officerPosition = signupOfficerPosition.getText().toString();

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    HelperClass3 helperClass3 = new HelperClass3(officerID, officerName, officerPosition, uid);
                    reference.child(uid).setValue(helperClass3);
                    Toast.makeText(ProfileRegistration.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileRegistration.this, LoginActivity.class));
                }
                else {
                }
            }
        });
    }
}