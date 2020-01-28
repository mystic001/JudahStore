package com.example.judahstore;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.judahstore.databinding.FragmentProductModalSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductModalSheet extends BottomSheetDialogFragment {

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String url = "imageUrl";
    private static final String PRICE = "price";
    private static final String DELIVERY_FEE = "fee";
    private static final String TOTAL = "cost";
    private static final String TAG = "ProductModalSheet";
    private static final String HOME = "https://www.paypal.com/au/home";

    public ProductModalSheet() {
        // Required empty public constructor
    }

    public static ProductModalSheet newInstance(String name,String description,String imageUrl, int price, int delivery , int total_cost){
        Bundle bundle = new Bundle();
        bundle.putString(NAME,name);
        bundle.putString(DESCRIPTION,description);
        bundle.putString(url,imageUrl);
        bundle.putInt(PRICE,price);
        bundle.putInt(DELIVERY_FEE,delivery);
        bundle.putInt(TOTAL,total_cost);

        ProductModalSheet modalSheet = new ProductModalSheet();
        modalSheet.setArguments(bundle);
        return modalSheet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProductModalSheetBinding fragmentProductModalSheetBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_modal_sheet,container,false);
        assert getArguments() != null;
        String name = getArguments().getString(NAME);
        String description = getArguments().getString(DESCRIPTION);
        String link = getArguments().getString(url);
        int price = getArguments().getInt(PRICE);
        int delivery = getArguments().getInt(DELIVERY_FEE);
        int total = getArguments().getInt(TOTAL);

        productModel prodel = new productModel(name,description,link,price,delivery,total);
        fragmentProductModalSheetBinding.setProMod(new ProductViewModel(prodel));

        fragmentProductModalSheetBinding.buttonii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri myUri = Uri.parse(HOME);
                Intent intent = PayPalActivity.newIntent(getActivity(),myUri);
                startActivity(intent);
            }
        });

        fragmentProductModalSheetBinding.chatNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PackageManager pm = getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    String number = "+2348155640049";
                    String url = "https://api.whatsapp.com/send?phone=" + number;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(getContext(), "Whatsapp app not installed on your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        });



        Log.d(TAG,"the link is"+link);




        Picasso.get()
                .load(link)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.shr_logo)
                .into(fragmentProductModalSheetBinding.proImage);

        return fragmentProductModalSheetBinding.getRoot();

    }



}
