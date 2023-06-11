package com.healthcare.docconect;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.healthcare.docconect.fragments.AppointmentsMainFragment;
import com.healthcare.docconect.fragments.MessagesMainFragment;
import com.healthcare.docconect.ContactsMainFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private ViewPager viewPager;
    private ArrayList<FragmentItem> tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        viewPager.addOnPageChangeListener(this);

        tabLayout = new ArrayList<FragmentItem>();

        tabLayout.add(FragmentItem.newItem("Contacts", ContactsMainFragment.newInstance()));
        tabLayout.add(FragmentItem.newItem("Messages", MessagesMainFragment.newInstance()));
        tabLayout.add(FragmentItem.newItem("Appointments", AppointmentsMainFragment.newInstance()));

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
    

        
    }
}
