package com.healthcare.docconect.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.TextView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.healthcare.docconect.R;

public class MessagesMainFragment extends Fragment {

    public ContactsMainFragment(){
        // Required empty  constructor
    }
    
    public static ContactsMainFragment newInstance(){
        ContactsMainFragment hf = new ContactsMainFragment();
        return hf;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle onSavedInstanceState){
        super.onCreateView(layoutInflater, container, onSavedInstanceState);
        ViewGroup rootView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_main_messages, container, false);
        
        
        return rootView;
    }
}