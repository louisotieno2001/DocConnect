package com.healthcare.docconect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        // Create an instance of the adapter that will control the fragments shown in each tab
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        // Add the fragments to the adapter
        tabAdapter.addFragment(new ContactsFragment(), "Contacts");
        tabAdapter.addFragment(new MessagesFragment(), "Messages");
        tabAdapter.addFragment(new AppointmentsFragment(), "Appointments");

        // Set the adapter to the ViewPager
        viewPager.setAdapter(tabAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
