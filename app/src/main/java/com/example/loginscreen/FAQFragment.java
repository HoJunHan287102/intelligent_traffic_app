package com.example.loginscreen;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FAQFragment extends Fragment {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

        createGroupList();
        createCollection();
        expandableListView = view.findViewById(R.id.elvMobiles);
        expandableListAdapter = new MyExpandableListAdapter(getActivity(), groupList, mobileCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                return true;
            }
        });
        return view;
    }

    private void createCollection() {
        String[] ques1 = {"The notifications will send through student email, and the notifications cannot be customized."};
        String[] ques2 = {"You can access the record of all valid users and you need to approach UUMIT for  detailed data."};
        mobileCollection = new HashMap<String, List<String>>();
        for(String group : groupList){
            if (group.equals("Q1. How are notifications sent to traffic offenders, and can I customize them?")){
                loadChild(ques1);
            } else if (group.equals("Q2. What information can I access about traffic violations through the application?"))
                loadChild(ques2);
            mobileCollection.put(group, childList);
        }
    }

    private void loadChild(String[] mobileModels) {
        childList = new ArrayList<>();
        for(String model : mobileModels){
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Q1. How are notifications sent to traffic offenders, and can I customize them?");
        groupList.add("Q2. What information can I access about traffic violations through the application?");
    }
}