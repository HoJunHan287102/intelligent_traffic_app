package com.example.loginscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RegisterFragment extends Fragment {

    Button manualEntryButton,smartScanButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        manualEntryButton = view.findViewById(R.id.manualEntryButton);
        smartScanButton = view.findViewById(R.id.smartScanButton);
        manualEntryButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Register2.class);
                startActivity(intent);
            }
        });

        smartScanButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SmartScanner.class);
                startActivity(intent);
            }
        });
        return view;
    }

}