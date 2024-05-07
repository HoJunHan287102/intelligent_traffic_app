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

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText signupEmail, signupPassword, signupOfficerID, signupOfficerName, signupOfficerPosition;
    Button signupButton;
    TextView loginRedirectText, profileRegistrationText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signUpButton);
//        signupOfficerID = findViewById(R.id.signup_officerID);
//        signupOfficerName = findViewById(R.id.signup_officerName);
//        signupOfficerPosition = findViewById(R.id.signup_officerPosition);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        profileRegistrationText = findViewById(R.id.profileRegistrationText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(currentUser!=null) {
//                    String uid = currentUser.getUid();
                    String email = signupEmail.getText().toString();
                    String password = signupPassword.getText().toString();
//                    String officerID = signupOfficerID.getText().toString();
//                    String officerName = signupOfficerName.getText().toString();
//                    String officerPosition = signupOfficerPosition.getText().toString();

//                    database = FirebaseDatabase.getInstance();
//                    reference = database.getReference("users");
//                    HelperClass4 helperClass4 = new HelperClass4(email, password, officerID, officerName, officerPosition, uid);
//                    reference.child(uid).setValue(helperClass4);

                    if (email.isEmpty()){
                        signupEmail.setError("Email cannot be empty");
                    }
                    if (password.isEmpty()){
                        signupPassword.setError("Password cannot be empty");
                    } else{
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, ProfileRegistration.class));
//                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
//                }
//                else {
//                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        profileRegistrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileRegistration.class);
                startActivity(intent);
            }
        });
    }
}