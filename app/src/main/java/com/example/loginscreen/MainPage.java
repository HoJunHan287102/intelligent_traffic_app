package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {
    DatabaseReference summonRecordReference;
    Button searchButton,registerButton,settingButton;
    TextView profileName, tvNumberSummon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        searchButton = findViewById(R.id.searchButton);
        registerButton = findViewById(R.id.registerButton);
        settingButton = findViewById(R.id.settingButton);
        tvNumberSummon = findViewById(R.id.tvNumberSummon);
        summonRecordReference = FirebaseDatabase.getInstance().getReference("summon record");
        summonRecordReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String summonID = String.valueOf(dataSnapshot.child("summonID").getValue());
                        tvNumberSummon.setText(summonID);
                    } else {
                    }
                } else {
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivitySearch1();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityRegister1();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivitySetting1();
            }
        });
    }
    public void openActivitySearch1() {
        Intent intent = new Intent(this, Search1.class);
        startActivity(intent);
    }
    public void openActivityRegister1() {
        Intent intent = new Intent(this, Register1.class);
        startActivity(intent);
    }
    public void openActivitySetting1() {
        Intent intent = new Intent(this, Setting1.class);
        startActivity(intent);
    }
}