package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.loginscreen.Register2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.loginscreen.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Locale;
import java.util.HashMap;

public class Update extends AppCompatActivity {
    String[] type = {"Student", "Staff"};
    AutoCompleteTextView edit_auto_complete_text;
    ArrayAdapter<String> adapterItems1;
    String[] summonDetail = {"Park at Staff Place", "Reckless Driving", "Unauthorized Parking"};
    AutoCompleteTextView edit_auto_complete_text3;
    ArrayAdapter<String> adapterItems3;
    String[] location = {"DKG 1", "DKG 2", "DKG 3", "DKG 4", "DKG 5", "DKG 6", "DKG 7", "DKG 8"};
    AutoCompleteTextView edit_auto_complete_text4;
    ArrayAdapter<String> adapterItems4;
    String[] status = {"Unpaid", "Paid"};
    AutoCompleteTextView edit_auto_complete_text5;
    ArrayAdapter<String> adapterItems5;
    private DatePickerDialog datePickerDialog1, datePickerDialog2;
    private Button edit_datePickerButton, edit_paymentDatelineButton;
    Button edit_timePickerButton;
    int hour, minute;
    String summonID;
    EditText edit_car_plate, edit_ID, edit_car_type, edit_car_colour, edit_summon_rate;
    Button update_button;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edit_auto_complete_text = findViewById(R.id.edit_auto_complete_text);
        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_menu, type);
        edit_auto_complete_text.setAdapter(adapterItems1);
        edit_auto_complete_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = adapterItems1.getItem(position);
            }
        });

        edit_auto_complete_text3 = findViewById(R.id.edit_auto_complete_text3);
        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_menu, summonDetail);
        edit_auto_complete_text3.setAdapter(adapterItems3);
        edit_auto_complete_text3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String summonDetail = adapterItems3.getItem(position);
            }
        });

        edit_auto_complete_text4 = findViewById(R.id.edit_auto_complete_text4);
        adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_menu, location);
        edit_auto_complete_text4.setAdapter(adapterItems4);
        edit_auto_complete_text4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String location = adapterItems4.getItem(position);
            }
        });

        edit_auto_complete_text5 = findViewById(R.id.edit_auto_complete_text5);
        adapterItems5 = new ArrayAdapter<String>(this, R.layout.list_menu, status);
        edit_auto_complete_text5.setAdapter(adapterItems5);
        edit_auto_complete_text5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String status = adapterItems5.getItem(position);
            }
        });

        initDatePicker();
        edit_datePickerButton = findViewById(R.id.edit_datePickerButton);
        edit_datePickerButton.setText("");
        edit_paymentDatelineButton = findViewById(R.id.edit_payment_button);
        edit_paymentDatelineButton.setText("");
        edit_timePickerButton = findViewById(R.id.edit_timePickerButton);
        update_button = findViewById(R.id.update_button);
        edit_car_plate = findViewById(R.id.edit_car_plate);
        edit_car_type = findViewById(R.id.edit_car_type);
        edit_car_colour = findViewById(R.id.edit_car_colour);
        edit_summon_rate = findViewById(R.id.edit_summon_rate);
        edit_ID = findViewById(R.id.edit_ID);
//        showData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("summon record");
                String carPlate = edit_car_plate.getText().toString();
                String carType = edit_car_type.getText().toString();
                String carColour = edit_car_colour.getText().toString();
                String iD = edit_ID.getText().toString();
                String type = edit_auto_complete_text.getText().toString();
                String summonDetail = edit_auto_complete_text3.getText().toString();
                String summonRate = edit_summon_rate.getText().toString();
                String paymentDateline = edit_paymentDatelineButton.getText().toString();
                String date = edit_datePickerButton.getText().toString();
                String time = edit_timePickerButton.getText().toString();
                String location = edit_auto_complete_text4.getText().toString();
                String status = edit_auto_complete_text5.getText().toString();
                updatedata(carPlate, carType, carColour, iD, type, summonDetail, summonRate, paymentDateline, date, time, location, status);
                HelperClass helperClass = new HelperClass(carPlate, carType, carColour, iD, type, summonDetail, summonRate, paymentDateline, date, time, location, status);
                reference.child(summonID).setValue(helperClass);
                Toast.makeText(Update.this, "Edit Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updatedata(String carPlate, String carType, String carColour, String iD, String type,
                            String summonDetail, String summonRate, String paymentDateline,
                            String date, String time, String location, String status) {

        HashMap User = new HashMap();
        User.put("carPlate", carPlate);
        User.put("carType", carType);
        User.put("carColour", carColour);
        User.put("id", iD);
        User.put("type", type);
        User.put("summonDetail", summonDetail);
        User.put("summonRate", summonRate);
        User.put("paymentDateline", paymentDateline);
        User.put("date", date);
        User.put("time", time);
        User.put("location", location);
        User.put("status", status);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("summon record");

        reference.child("summonID").updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    edit_car_plate.setText("");
                    edit_car_type.setText("");
                    edit_car_colour.setText("");
                    edit_ID.setText("");
                    edit_auto_complete_text.setText("");
                    edit_auto_complete_text3.setText("");
                    edit_summon_rate.setText("");
                    edit_paymentDatelineButton.setText("");
                    edit_datePickerButton.setText("");
                    edit_timePickerButton.setText("");
                    edit_auto_complete_text4.setText("");
                    edit_auto_complete_text5.setText("");
                    Toast.makeText(Update.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Update.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//    public void showData(){
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference("summon record");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String carPlate = userSnapshot.child("carPlate").getValue(String.class);
//                    String carType = userSnapshot.child("carType").getValue(String.class);
//                    String carColour = userSnapshot.child("carColour").getValue(String.class);
//                    String iD = userSnapshot.child("id").getValue(String.class);
//                    String type = userSnapshot.child("type").getValue(String.class);
//                    String summonDetail = userSnapshot.child("summonDetail").getValue(String.class);
//                    String summonRate = userSnapshot.child("summonRate").getValue(String.class);
//                    String paymentDateline = userSnapshot.child("paymentDateline").getValue(String.class);
//                    String date = userSnapshot.child("date").getValue(String.class);
//                    String time = userSnapshot.child("time").getValue(String.class);
//                    String location = userSnapshot.child("location").getValue(String.class);
//                    String status = userSnapshot.child("status").getValue(String.class);
//                    edit_car_plate.setText(carPlate);
//                    edit_car_type.setText(carType);
//                    edit_car_colour.setText(carColour);
//                    edit_ID.setText(iD);
//                    edit_auto_complete_text.setText(type);
//                    edit_auto_complete_text3.setText(summonDetail);
//                    edit_summon_rate.setText(summonRate);
//                    edit_paymentDatelineButton.setText(paymentDateline);
//                    edit_datePickerButton.setText(date);
//                    edit_timePickerButton.setText(time);
//                    edit_auto_complete_text4.setText(location);
//                    edit_auto_complete_text5.setText(status);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
        //        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    if (task.getResult().exists()) {
//                        DataSnapshot userSnapshot = task.getResult();
//                        String carPlate = userSnapshot.child("carPlate").getValue(String.class);
//                        String carType = userSnapshot.child("carType").getValue(String.class);
//                        String carColour = userSnapshot.child("carColour").getValue(String.class);
//                        String iD = userSnapshot.child("id").getValue(String.class);
//                        String type = userSnapshot.child("type").getValue(String.class);
//                        String summonDetail = userSnapshot.child("summonDetail").getValue(String.class);
//                        String summonRate = userSnapshot.child("summonRate").getValue(String.class);
//                        String paymentDateline = userSnapshot.child("paymentDateline").getValue(String.class);
//                        String date = userSnapshot.child("date").getValue(String.class);
//                        String time = userSnapshot.child("time").getValue(String.class);
//                        String location = userSnapshot.child("location").getValue(String.class);
//                        String status = userSnapshot.child("status").getValue(String.class);
//                        edit_car_plate.setText(carPlate);
//                        edit_car_type.setText(carType);
//                        edit_car_colour.setText(carColour);
//                        edit_ID.setText(iD);
//                        edit_auto_complete_text.setText(type);
//                        edit_auto_complete_text3.setText(summonDetail);
//                        edit_summon_rate.setText(summonRate);
//                        edit_paymentDatelineButton.setText(paymentDateline);
//                        edit_datePickerButton.setText(date);
//                        edit_timePickerButton.setText(time);
//                        edit_auto_complete_text4.setText(location);
//                        edit_auto_complete_text5.setText(status);
//                    } else {
//                    }
//                } else {
//                }
//            }
//        });
//    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog.OnDateSetListener dateSetListener1 = (datePicker, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = makeDateString(dayOfMonth, month1, year1);
            edit_datePickerButton.setText(date);
        };

        DatePickerDialog.OnDateSetListener dateSetListener2 = (datePicker, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = makeDateString(dayOfMonth, month1, year1);
            edit_paymentDatelineButton.setText(date);
        };

        datePickerDialog1 = new DatePickerDialog(this, style, dateSetListener1, year, month, day);
        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener2, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog1.show();
    }
    public void openPaymentDatePicker(View view) {
        datePickerDialog2.show();
    }

    public void openTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                edit_timePickerButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}
