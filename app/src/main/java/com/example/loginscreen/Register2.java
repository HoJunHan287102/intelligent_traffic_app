package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.util.Log;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register2 extends AppCompatActivity {
    String[] type = {"Student", "Staff"};
    AutoCompleteTextView autoCompleteTextView1;
    ArrayAdapter<String> adapterItems1;
    String[] summonDetail = {"Park at Staff Place", "Reckless Driving", "Unauthorized Parking"};
    AutoCompleteTextView autoCompleteTextView3;
    ArrayAdapter<String> adapterItems3;
    String[] location = {"DKG 1", "DKG 2", "DKG 3", "DKG 4", "DKG 5", "DKG 6", "DKG 7", "DKG 8"};
    AutoCompleteTextView autoCompleteTextView4;
    ArrayAdapter<String> adapterItems4;
    String[] status = {"Unpaid", "Paid"};
    AutoCompleteTextView autoCompleteTextView5;
    ArrayAdapter<String> adapterItems5;
    EditText id, carplate, cartype, carcolour, summonrate;
    Button registerButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private DatePickerDialog datePickerDialog1, datePickerDialog2;
    private Button dateButton, paymentDatelineButton;
    Button timeButton;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        autoCompleteTextView1 = findViewById(R.id.auto_complete_text);
        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_menu, type);
        autoCompleteTextView1.setAdapter(adapterItems1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = adapterItems1.getItem(position);
            }
        });

        autoCompleteTextView3 = findViewById(R.id.auto_complete_text3);
        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_menu, summonDetail);
        autoCompleteTextView3.setAdapter(adapterItems3);
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String summonDetail = adapterItems3.getItem(position);
            }
        });

        autoCompleteTextView4 = findViewById(R.id.auto_complete_text4);
        adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_menu, location);
        autoCompleteTextView4.setAdapter(adapterItems4);
        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String location = adapterItems4.getItem(position);
            }
        });

        autoCompleteTextView5 = findViewById(R.id.auto_complete_text5);
        adapterItems5 = new ArrayAdapter<String>(this, R.layout.list_menu, status);
        autoCompleteTextView5.setAdapter(adapterItems5);
        autoCompleteTextView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String status = adapterItems5.getItem(position);
            }
        });

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        paymentDatelineButton = findViewById(R.id.payment_button);
        paymentDatelineButton.setText("Payment Dateline");

        timeButton = findViewById(R.id.timePickerButton);

        carplate = findViewById(R.id.car_plate);
        id = findViewById(R.id.ID);
        cartype = findViewById(R.id.car_type);
        carcolour = findViewById(R.id.car_colour);
        summonrate = findViewById(R.id.summon_rate);
        registerButton = findViewById(R.id.register_button);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("summon record");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerFirstSummon();
                registerSummon();
            }
        });
    }

    private void registerFirstSummon() {
        // Check if the summonID node exists
        reference.child("summonID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // If summonID node does not exist, set it to 1
                    reference.child("summonID").setValue(1, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                // First summon registration successful
                                String carPlate = carplate.getText().toString();
                                String iD = id.getText().toString();
                                String type = autoCompleteTextView1.getText().toString();
                                String carType = cartype.getText().toString();
                                String carColour = carcolour.getText().toString();
                                String summonRate = summonrate.getText().toString();
                                String summonDetail = autoCompleteTextView3.getText().toString();
                                String paymentDateline = paymentDatelineButton.getText().toString();
                                String date = dateButton.getText().toString();
                                String time = timeButton.getText().toString();
                                String location = autoCompleteTextView4.getText().toString();
                                String status = autoCompleteTextView5.getText().toString();
                                HelperClass helperClass = new HelperClass(carPlate, carType, carColour, iD, type, summonDetail, summonRate, paymentDateline, date, time, location, status);
                                reference.child("1").setValue(helperClass);
                                Toast.makeText(Register2.this, "Summon Registered Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // First summon registration failed
                                Toast.makeText(Register2.this, "Failed to Register Summon", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(Register2.this, "Failed to check summonID existence!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerSummon() {
        // Increment summonID by 1
        reference.child("summonID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the current value of summonID
                Long currentID = dataSnapshot.getValue(Long.class);
                if (currentID != null) {
                    // Increment the summonID
                    Long newID = currentID + 1;
                    // Update the summonID in the database
                    reference.child("summonID").setValue(newID, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                // Registration successful
                                Toast.makeText(Register2.this, "Summon Registered Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Registration failed
                                Toast.makeText(Register2.this, "Failed to Register Summon", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String carPlate = carplate.getText().toString();
                    String iD = id.getText().toString();
                    String type = autoCompleteTextView1.getText().toString();
                    String carType = cartype.getText().toString();
                    String carColour = carcolour.getText().toString();
                    String summonRate = summonrate.getText().toString();
                    String summonDetail = autoCompleteTextView3.getText().toString();
                    String paymentDateline = paymentDatelineButton.getText().toString();
                    String date = dateButton.getText().toString();
                    String time = timeButton.getText().toString();
                    String location = autoCompleteTextView4.getText().toString();
                    String status = autoCompleteTextView5.getText().toString();
                    HelperClass helperClass = new HelperClass(carPlate, carType, carColour, iD, type, summonDetail, summonRate, paymentDateline, date, time, location, status);

                    reference.child(String.valueOf(newID)).setValue(helperClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(Register2.this, "Failed to read summonID value!", Toast.LENGTH_SHORT).show();
            }
        });
    }
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
            dateButton.setText(date);
        };

        DatePickerDialog.OnDateSetListener dateSetListener2 = (datePicker, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String date = makeDateString(dayOfMonth, month1, year1);
            paymentDatelineButton.setText(date);
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
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}