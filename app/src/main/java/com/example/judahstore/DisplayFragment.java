package com.example.judahstore;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.judahstore.databinding.FragmentDisplayBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements ProductModelAdapter.itemCliclistener {

    private List<productModel> model;
    private ProductModelAdapter adapter;

    private DatabaseReference mDatabaseReference;

    private ShareActionProvider shareActionProvider;

    public static Fragment newInstance() {
        return new DisplayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("goods");

        FirebaseMessaging.getInstance().subscribeToTopic("updates");
        setHasOptionsMenu(true);
    }

    public DisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentDisplayBinding fragmentDisplayBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_display,container,false);

        model = new ArrayList<>();
        adapter = new ProductModelAdapter(getActivity(), model);

        adapter.setItemClickListener(this);
        Toolbar toolbar = fragmentDisplayBinding.toolbar;

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model.clear();
                for (DataSnapshot individualShot : dataSnapshot.getChildren()) {
                    productModel mosey = individualShot.getValue(productModel.class);
                    assert mosey != null;
                    mosey.setUniqueId(individualShot.getKey());
                    model.add(mosey);
                }
                adapter.notifyDataSetChanged();
                fragmentDisplayBinding.recyclerView.setAdapter(adapter);
                fragmentDisplayBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "An error occured", Toast.LENGTH_SHORT).show();
            }
        });

        return fragmentDisplayBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setActionIntent();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setActionIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Join me in using judha store download from");
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public void onShortClick(int position) {
        productModel currentPro = model.get(position);
        ProductModalSheet modalSheet = ProductModalSheet.newInstance(currentPro.getName(),
                currentPro.getDescription(),currentPro.getImageUrl(),currentPro.getPrice(),
                currentPro.getDelivery_fee(),currentPro.getTotalCost());
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        modalSheet.show(fragmentManager,"Show");
    }


   /* @Override
    public void ondelete(int position) {
        productModel currentPro = model.get(position);
        final String id = currentPro.getUniqueId();

        StorageReference imageRef = mStorage.getReferenceFromUrl(currentPro.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseReference.child(id).removeValue();
                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    @Override
    public void showFull(int position) {
        productModel currentPro = model.get(position);
        PictureModalsheet pictureModalsheet = PictureModalsheet.newInstance(currentPro.getImageUrl(),currentPro.getName());
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        pictureModalsheet.show(fragmentManager,"Show");

    }
}
