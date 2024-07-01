package com.example.loginscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;

public class SearchRecordFragment extends Fragment {
    DatabaseReference summonRecordReference;
    Button updateButton, deleteButton, searchButton;
    TextView tvCarPlate, tvCarType, tvCarColour, tvType, tvUserID, tvSummonDetail, tvSummonRate,
            tvDate, tvTime, tvPaymentDateline, tvLocation, tvStatus;
    EditText summon_ID;
    String summonID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_record, container, false);

        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        searchButton = view.findViewById(R.id.search_button);
        summon_ID = view.findViewById(R.id.summon_ID);
        tvCarPlate = view.findViewById(R.id.tvCarPlate);
        tvCarType = view.findViewById(R.id.tvCarType);
        tvCarColour = view.findViewById(R.id.tvCarColour);
        tvType = view.findViewById(R.id.tvType);
        tvUserID = view.findViewById(R.id.tvUserID);
        tvSummonDetail = view.findViewById(R.id.tvSummonDetail);
        tvSummonRate = view.findViewById(R.id.tvSummonRate);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        tvPaymentDateline = view.findViewById(R.id.tvPaymentDateline);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvStatus = view.findViewById(R.id.tvStatus);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summonID = summon_ID.getText().toString();
                if (!summonID.isEmpty()) {
                    readData();
                } else {
                    Toast.makeText(getActivity(), "Please Enter Summon ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                passUserData();
                openActivityUpdate();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityDelete();
            }
        });
        return view;
    }

    private void readData() {
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

                        tvCarPlate.setText(carPlate);
                        tvCarType.setText(carType);
                        tvCarColour.setText(carColour);
                        tvType.setText(type);
                        tvUserID.setText(id);
                        tvSummonDetail.setText(summonDetail);
                        tvSummonRate.setText(summonRate);
                        tvDate.setText(date);
                        tvTime.setText(time);
                        tvPaymentDateline.setText(paymentDateline);
                        tvLocation.setText(location);
                        tvStatus.setText(status);
                        Toast.makeText(getActivity(), "Search Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Summon Record Doesn't Exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to Search", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void passUserData(){
        String summonID = summon_ID.getText().toString();
        Intent intent = new Intent(getActivity(), Update.class);
        intent.putExtra("summonID", summonID); // Pass the summonID to Update activity
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
                    Intent intent = new Intent(getActivity(), Update.class);
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
        Intent intent = new Intent(getActivity(), Update.class);
        startActivity(intent);
    }
    public void openActivityDelete() {
        Intent intent = new Intent(getActivity(), DeleteBottom.class);
        startActivity(intent);
    }
}