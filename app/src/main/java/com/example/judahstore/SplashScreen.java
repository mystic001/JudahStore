package com.example.judahstore;


import androidx.fragment.app.Fragment;



public class SplashScreen extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SplashScreenFragment.newInstance();
    }

}
