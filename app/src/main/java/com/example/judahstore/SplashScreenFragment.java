package com.example.judahstore;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.judahstore.databinding.FragmentSplashScreenBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {


    FragmentSplashScreenBinding fragmentSplashScreenBinding;
    public static Fragment newInstance(){
        return new SplashScreenFragment();
    }

    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        long TIME_OUT = 5000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(CheckNetworkStatus.CheckNetwork(getActivity())){
                    Intent intent = new Intent(getActivity(),DisplayActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getActivity(),"No network connection",Toast.LENGTH_LONG).show();
                    fragmentSplashScreenBinding.probar.setVisibility(View.GONE);
                    return ;
                }

                //the getActivity method used below gives us the access to activity's method
                //You realise that finish can be called directly within the Activity class
                //But we can only access it here using in the fragment class using getActivity method
                getActivity().finish();
            }
        }, TIME_OUT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSplashScreenBinding fragmentSplashScreenBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash_screen,container,false);
        return  fragmentSplashScreenBinding.getRoot();

    }

}
