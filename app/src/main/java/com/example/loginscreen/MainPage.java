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
    TextView tvNumberSummon, tvUnpaidStatusSummon, tvPaidStatusSummon, tvStaffDetailSummon,
            tvRecklessDetailSummon, tvUnauthorizedDetailSummon, tvStudentSummon, tvStaffSummon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        searchButton = findViewById(R.id.searchButton);
        registerButton = findViewById(R.id.registerButton);
        settingButton = findViewById(R.id.settingButton);
        tvNumberSummon = findViewById(R.id.tvNumberSummon);
        tvStaffDetailSummon = findViewById(R.id.tvStaffDetailSummon);
        tvRecklessDetailSummon = findViewById(R.id.tvRecklessDetailSummon);
        tvUnauthorizedDetailSummon = findViewById(R.id.tvUnauthorizedDetailSummon);
        tvStudentSummon = findViewById(R.id.tvStudentSummon);
        tvStaffSummon = findViewById(R.id.tvStaffSummon);
        tvUnpaidStatusSummon = findViewById(R.id.tvUnpaidStatusSummon);
        tvPaidStatusSummon = findViewById(R.id.tvPaidStatusSummon);
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
        summonRecordReference.orderByChild("summonDetail").equalTo("Park at Staff Place").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvStaffDetailSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("summonDetail").equalTo("Reckless Driving").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvRecklessDetailSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("summonDetail").equalTo("Unauthorized Parking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvUnauthorizedDetailSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("type").equalTo("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvStudentSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("type").equalTo("Staff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvStaffSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("status").equalTo("Paid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long paidCount = dataSnapshot.getChildrenCount();
                tvPaidStatusSummon.setText(String.valueOf(paidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        summonRecordReference.orderByChild("status").equalTo("Unpaid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long unPaidCount = dataSnapshot.getChildrenCount();
                tvUnpaidStatusSummon.setText(String.valueOf(unPaidCount));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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