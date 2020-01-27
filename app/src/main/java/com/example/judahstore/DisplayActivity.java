package com.example.judahstore;

import androidx.fragment.app.Fragment;


public class DisplayActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

       return DisplayFragment.newInstance();
    }
}
