package com.example.judahstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class PictureModalsheet extends BottomSheetDialogFragment {

    private static final String IMAGE ="image" ;
    private static final String NAME = "name" ;


    public static PictureModalsheet newInstance(String imageUrl, String name){

        Bundle mystic = new Bundle();
        mystic.putString(IMAGE,imageUrl);
        mystic.putString(NAME,name);

        PictureModalsheet sheet = new PictureModalsheet();
        sheet.setArguments(mystic);
        return  sheet;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.picturemodalsheet_xml,container,false);
        ImageView mage = view.findViewById(R.id.full_image);
        TextView text = view.findViewById(R.id.product_nam);

        assert getArguments() != null;
        String url = getArguments().getString(IMAGE);
        String name = getArguments().getString(NAME);

        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(mage);
        text.setText(name);


        return view;


    }
}
