package com.example.judahstore;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.judahstore.databinding.PicturemodalsheetXmlBinding;
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


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PicturemodalsheetXmlBinding picturemodalsheetXmlBinding = DataBindingUtil.inflate(inflater,R.layout.picturemodalsheet_xml,container,false);

        assert getArguments() != null;
        String url = getArguments().getString(IMAGE);
        productModel model = new productModel(getArguments().getString(NAME));
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(picturemodalsheetXmlBinding.fullImage);

        picturemodalsheetXmlBinding.setPicturMod(new ProductViewModel(model));
        return picturemodalsheetXmlBinding.getRoot();


    }
}
