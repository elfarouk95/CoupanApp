package com.coupanapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coupanapp.R;

import java.util.ArrayList;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Model> modelList;

    private OnItemClickListener mItemClickListener;


    public RecyclerViewAdapter(Context context, ArrayList<Model> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<Model> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newitem, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Model model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            Glide.with(mContext).load("http://www.coupons-codes.website/t/DashBoard/" + model.getImgurl()).into(((ViewHolder) holder).imgUser);
            ((ViewHolder) holder).itemTxtTitle.setText(model.getCopuntxt());
            ((ViewHolder) holder).value.setText(model.getvalue());
            ((ViewHolder) holder).siteUrl.setText(model.getsiteUrl());


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Model getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Model model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView value;
        private TextView siteUrl;
        private Button Copy;
        private ImageView Share;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.imgUser = (ImageView) itemView.findViewById(R.id.ImgAds);
            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.code);
            this.value = (TextView) itemView.findViewById(R.id.value);
            this.siteUrl = (TextView) itemView.findViewById(R.id.siteurl);
            this.Copy = itemView.findViewById(R.id.copy);
            this.Share = itemView.findViewById(R.id.share);


            Copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });


            Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoWeb(view.getContext(), "https://play.google.com/store/apps/details?id=com.coupanapp");
                    // mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

            siteUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoWeb(view.getContext(), modelList.get(getAdapterPosition()).getsiteUrl());
                    // mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });


        }
    }


    void gotoWeb(Context c, String url) {
        try {
            // String url = "http://www.example.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            mContext.startActivity(i);
        } catch (Exception e) {
            Toast.makeText(mContext, "", Toast.LENGTH_LONG).show();
        }
    }
}

