package com.example.loginscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment extends Fragment {

    Button myProfileButton, contactUsButton, faqButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        myProfileButton = view.findViewById(R.id.myProfileButton);
        contactUsButton = view.findViewById(R.id.contactUsButton);
        faqButton = view.findViewById(R.id.faqButton);

        myProfileButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProfileBottom.class);
                startActivity(intent);
            }
        });
        contactUsButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUsBottom.class);
                startActivity(intent);
            }
        });
        faqButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), faqBottom.class);
                startActivity(intent);
            }
        });
        return view;
    }
}