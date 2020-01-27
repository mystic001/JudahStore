package com.example.judahstore;

import androidx.fragment.app.Fragment;


public class UploadActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return UploadFragment.newInstance();
    }

}
