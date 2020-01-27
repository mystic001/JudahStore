package com.example.judahstore;


import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


public class PayPalActivity extends SingleFragmentActivity {
    private static final String TAG = "PaypalActivity";

    public static Intent newIntent(Context context, Uri myUri) {
        Intent intent = new Intent(context,PayPalActivity.class);
        intent.setData(myUri);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        Uri uri = getIntent().getData();
        Log.d(TAG,"The url is " + uri.toString());
        return PayPalFragment.newInstance(getIntent().getData());


    }
}
