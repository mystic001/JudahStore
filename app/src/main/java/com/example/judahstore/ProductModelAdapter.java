package com.example.judahstore;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

            public class ProductModelAdapter extends RecyclerView.Adapter<ProductModelAdapter.productModelHolder> {

                private Context mContext;
                private List<productModel> promodel ;
                private itemCliclistener click;

                public ProductModelAdapter(Context context, List<productModel> mod){
                    mContext = context;
                    promodel = mod;
                }

                @NonNull
                @Override
                public productModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.judah_product_card,parent,false);
                    return new productModelHolder(view);
                }

                @Override
                public void onBindViewHolder(@NonNull productModelHolder holder, int position) {
                    productModel proCurrent = promodel.get(position);
                    holder.name.setText(proCurrent.getName());
                    holder.price.setText(String.valueOf(proCurrent.getPrice()));
                    YoYo.with(Techniques.RubberBand)
                            .duration(2000)
                            .repeat(2)
                            .playOn(holder.but);

                    Picasso.get()
                            .load(proCurrent.getImageUrl())
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.shr_logo)
                            .into(holder.image_view);
                }

                @Override
                public int getItemCount() {
                    return promodel.size();
                }

                public class productModelHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
                    ImageView image_view ;
                    TextView name;
                    TextView price;
                    Button but;

                    private productModelHolder(@NonNull View itemView) {
                        super(itemView);
                       image_view = itemView.findViewById(R.id.product_image);
                       name = itemView.findViewById(R.id.product_name);
                       price = itemView.findViewById(R.id.product_price);
                       but = itemView.findViewById(R.id.button);

                       but.setOnClickListener(new View.OnClickListener() {
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

                       itemView.setOnClickListener(this);
                       itemView.setOnCreateContextMenuListener(this);

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

                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                       contextMenu.setHeaderTitle("Select any action below");
                        MenuItem ondelete = contextMenu.add(Menu.NONE, 1, 1, "Delete");
                        ondelete.setOnMenuItemClickListener(this);

                    }

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if ( click != null ) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                click.ondelete(position);
                            }
                        }

                        return false;
                    }
                }

                public interface  itemCliclistener{
                    void onShortClick(int position);
                    void ondelete (int position);
                    void showFull(int position);

                }

                public void setItemClickListener( itemCliclistener listener){
                    click = listener ;

                }
            }
