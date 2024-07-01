package com.example.loginscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteFragment extends Fragment {
    DatabaseReference reference;
    Button delete_button;
    EditText deleteSummonID;
    String summonID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        deleteSummonID = view.findViewById(R.id.delete_SummonID);
        delete_button = view.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summonID = deleteSummonID.getText().toString();
                if (!summonID.isEmpty()) {
                    showConfirmationDialog();
                } else {
                    Toast.makeText(getActivity(), "Please Enter Summon ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this record?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteData() {
        reference = FirebaseDatabase.getInstance().getReference("summon record");
        reference.child(summonID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    reference.child(summonID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Decrease the summonID value
                                reference.child("summonID").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Long currentID = dataSnapshot.getValue(Long.class);
                                        if (currentID != null) {
                                            Long newID = currentID - 1;
                                            reference.child("summonID").setValue(newID);
                                            Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            deleteSummonID.setText("");
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle potential errors here
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "This Record Doesn't Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors here
            }
        });
    }
}