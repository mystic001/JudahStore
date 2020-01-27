package com.example.judahstore;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



/**
 * A simple {@link Fragment} subclass.
 */
public class PayPalFragment extends Fragment {

    private static final String HOME = "home";
    private Uri mUri;
    private ProgressBar mProgressBar;
    private static final String TAG = "PayPalFragment";


    public static  PayPalFragment newInstance(Uri uri){
        Bundle mystic = new Bundle();
        mystic.putParcelable(HOME,uri);
        PayPalFragment frag =new PayPalFragment();
        frag.setArguments(mystic);
        return frag;
    }


    public PayPalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mUri = getArguments().getParcelable(HOME);
        assert mUri != null;
        Log.d(TAG,"The url is " + mUri.toString());




    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_pal, container, false);
        WebView mWeb = view.findViewById(R.id.web);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar bar = view.findViewById(R.id.toolbar);
        assert activity != null;
        activity.setSupportActionBar(bar);


        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        mProgressBar = view.findViewById(R.id.progressBar);
        mProgressBar.setMax(100); // WebChromeClient reports in range 0-100
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            public void onReceivedTitle(WebView webView, String title) {

                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.loadUrl(mUri.toString());
        return view ;
    }




}
