package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    EditText editOfficerID, editOfficerName, editOfficerPosition;
    Button editProfileButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    String officerID, officerName, officerPosition;
    private FirebaseAuth test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        test=FirebaseAuth.getInstance();
        FirebaseUser currentUser = test.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        editOfficerID = findViewById(R.id.edit_officerID);
        editOfficerName = findViewById(R.id.edit_officerName);
        editOfficerPosition = findViewById(R.id.edit_officerPosition);
        editProfileButton = findViewById(R.id.edit_profile_button);
        showData();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!officerID.equals(editOfficerID.getText().toString()) || !officerName.equals(editOfficerName.getText().toString()) || !officerPosition.equals(editOfficerPosition.getText().toString()) ){
//                    String uid = currentUser.getUid();
//                    officerID = editOfficerID.getText().toString();
//                    officerName = editOfficerName.getText().toString();
//                    officerPosition = editOfficerPosition.getText().toString();
//                    updatedata(officerID, officerName, officerPosition);
//                    HelperClass3 helperClass3 = new HelperClass3(officerID, officerName, officerPosition, uid);
//                    reference.child(uid).setValue(helperClass3);
//                    Toast.makeText(EditProfile.this, "Edit Successful", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EditProfile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
//                }
                String uid = currentUser.getUid();
                officerID = editOfficerID.getText().toString();
                officerName = editOfficerName.getText().toString();
                officerPosition = editOfficerPosition.getText().toString();
                updatedata(officerID, officerName, officerPosition);
                HelperClass3 helperClass3 = new HelperClass3(officerID, officerName, officerPosition, uid);
                reference.child(uid).setValue(helperClass3);
                Toast.makeText(EditProfile.this, "Edit Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updatedata(String officerID, String officerName, String officerPosition) {

        HashMap User = new HashMap();
        User.put("officerID",officerID);
        User.put("officerName",officerName);
        User.put("officerPosition",officerPosition);
        test=FirebaseAuth.getInstance();
        FirebaseUser currentUser = test.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        String uid = currentUser.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reference.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    editOfficerID.setText("");
                    editOfficerName.setText("");
                    editOfficerPosition.setText("");
                    Toast.makeText(EditProfile.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditProfile.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void showData(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String officerID = userSnapshot.child("officerID").getValue(String.class);
                    String officerName = userSnapshot.child("officerName").getValue(String.class);
                    String officerPosition = userSnapshot.child("officerPosition").getValue(String.class);
                    editOfficerID.setText(officerID);
                    editOfficerName.setText(officerName);
                    editOfficerPosition.setText(officerPosition);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}