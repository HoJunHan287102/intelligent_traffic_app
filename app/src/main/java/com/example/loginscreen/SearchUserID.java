package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;
import java.util.HashMap;

import com.example.loginscreen.databinding.ActivitySearchUserIdBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchUserID extends AppCompatActivity {
    ActivitySearchUserIdBinding binding;
    DatabaseReference usersReference, summonRecordReference;
    Button updateButton, deleteButton;
    String summonID;
    private FirebaseAuth test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_id);

        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        binding = ActivitySearchUserIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summonID = binding.summonID.getText().toString();
                if (!summonID.isEmpty()) {
                    readData();
                } else {
                    Toast.makeText(SearchUserID.this, "Please Enter Summon ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
                openActivityUpdate();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityDelete();
            }
        });
    }

    private void readData(){
        summonRecordReference = FirebaseDatabase.getInstance().getReference("summon record").child(summonID);
        summonRecordReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String carPlate = String.valueOf(dataSnapshot.child("carPlate").getValue());
                        String carType = String.valueOf(dataSnapshot.child("carType").getValue());
                        String carColour = String.valueOf(dataSnapshot.child("carColour").getValue());
                        String type = String.valueOf(dataSnapshot.child("type").getValue());
                        String id = String.valueOf(dataSnapshot.child("id").getValue());
                        String summonDetail = String.valueOf(dataSnapshot.child("summonDetail").getValue());
                        String summonRate = String.valueOf(dataSnapshot.child("summonRate").getValue());
                        String date = String.valueOf(dataSnapshot.child("date").getValue());
                        String time = String.valueOf(dataSnapshot.child("time").getValue());
                        String paymentDateline = String.valueOf(dataSnapshot.child("paymentDateline").getValue());
                        String location = String.valueOf(dataSnapshot.child("location").getValue());
                        String status = String.valueOf(dataSnapshot.child("status").getValue());

                        binding.tvCarPlate.setText(carPlate);
                        binding.tvCarType.setText(carType);
                        binding.tvCarColour.setText(carColour);
                        binding.tvType.setText(type);
                        binding.tvUserID.setText(id);
                        binding.tvSummonDetail.setText(summonDetail);
                        binding.tvSummonRate.setText(summonRate);
                        binding.tvDate.setText(date);
                        binding.tvTime.setText(time);
                        binding.tvPaymentDateline.setText(paymentDateline);
                        binding.tvLocation.setText(location);
                        binding.tvStatus.setText(status);
                        Toast.makeText(SearchUserID.this, "Search Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SearchUserID.this, "Summon Record Doesn't Exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchUserID.this, "Failed to Search", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void passUserData(){
        String summonID = binding.summonID.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("summon record");
        Query checkUserDatabase = reference.orderByChild("summonID").equalTo(summonID);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String carPlateFromDB = snapshot.child(summonID).child("carPlate").getValue(String.class);
                    String carTypeFromDB = snapshot.child(summonID).child("carType").getValue(String.class);
                    String carColourFromDB = snapshot.child(summonID).child("carColour").getValue(String.class);
                    String dateFromDB = snapshot.child(summonID).child("date").getValue(String.class);
                    String idFromDB = snapshot.child(summonID).child("id").getValue(String.class);
                    String locationFromDB = snapshot.child(summonID).child("location").getValue(String.class);
                    String statusFromDB = snapshot.child(summonID).child("status").getValue(String.class);
                    String summonDetailFromDB = snapshot.child(summonID).child("summonDetail").getValue(String.class);
                    String summonRateFromDB = snapshot.child(summonID).child("summonRate").getValue(String.class);
                    String paymentDatelineFromDB = snapshot.child(summonID).child("paymentDateline").getValue(String.class);
                    String timeFromDB = snapshot.child(summonID).child("time").getValue(String.class);
                    String typeFromDB = snapshot.child(summonID).child("type").getValue(String.class);
                    Intent intent = new Intent(SearchUserID.this, Update.class);
                    intent.putExtra("carPlate", carPlateFromDB);
                    intent.putExtra("carType", carTypeFromDB);
                    intent.putExtra("carColour", carColourFromDB);
                    intent.putExtra("date", dateFromDB);
                    intent.putExtra("id", idFromDB);
                    intent.putExtra("location", locationFromDB);
                    intent.putExtra("status", statusFromDB);
                    intent.putExtra("summonDetail", summonDetailFromDB);
                    intent.putExtra("summonRate", summonRateFromDB);
                    intent.putExtra("paymentDateline", paymentDatelineFromDB);
                    intent.putExtra("time", timeFromDB);
                    intent.putExtra("type", typeFromDB);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void openActivityUpdate() {
        Intent intent = new Intent(this, Update.class);
        startActivity(intent);
    }
    public void openActivityDelete() {
        Intent intent = new Intent(this, Delete.class);
        startActivity(intent);
    }
}