package com.example.judahstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.judahstore.databinding.JudahProductCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

            public class ProductModelAdapter extends RecyclerView.Adapter<ProductModelAdapter.productModelHolder> {

                private Context mContext;
                private List<productModel> promodel ;
                private itemCliclistener click;

                private productModel prodel;

                public ProductModelAdapter(Context context, List<productModel> mod){
                    mContext = context;
                    promodel = mod;
                }

                @NonNull
                @Override
                public productModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    JudahProductCardBinding judahProductCardBinding = DataBindingUtil.inflate(inflater,R.layout.judah_product_card,parent,false);
                    return new productModelHolder(judahProductCardBinding);
                }

                @Override
                public void onBindViewHolder(@NonNull productModelHolder holder, int position) {
                    productModel proCurrent = promodel.get(position);

                    //The code below will get the naming and the price from the layout file and use it to update the corresponding values of the productModel
                    // We update the name and the price of our productmodel through the ProductViewModel Class

                    holder.mJudahProductCardBinding.getProcard().setProdel(proCurrent);
                    holder.mJudahProductCardBinding.executePendingBindings();

                    YoYo.with(Techniques.RubberBand)
                            .duration(2000)
                            .repeat(2)
                            .playOn(holder.mJudahProductCardBinding.button);

                    Picasso.get()
                            .load(proCurrent.getImageUrl())
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.shr_logo)
                            .into(holder.mJudahProductCardBinding.productImage);
                }

                @Override
                public int getItemCount() {
                    return promodel.size();
                }

                public class productModelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                    JudahProductCardBinding mJudahProductCardBinding;

                    private productModelHolder(@NonNull JudahProductCardBinding judahProductCardBinding ) {
                        super(judahProductCardBinding.getRoot());


                        mJudahProductCardBinding = judahProductCardBinding;
                        mJudahProductCardBinding.setProcard(new ProductViewModel(prodel));

                       mJudahProductCardBinding.button.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               if ( click != null ) {
                                   int position = getAdapterPosition();
                                   if(position != RecyclerView.NO_POSITION){
                                       click.onShortClick(position);
                                   }
                               }
                           }
                       });

                       judahProductCardBinding.getRoot().setOnClickListener(this);

                    }

                    @Override
                    public void onClick(View view) {
                        if ( click != null ) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                click.showFull(position);
                            }
                        }

                    }

                }

                public interface  itemCliclistener{
                    void onShortClick(int position);
                    void showFull(int position);

                }

                public void setItemClickListener( itemCliclistener listener){
                    click = listener ;

                }

            }
