package jp.syned.galleryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private List<Images> imagesList;
    private Context context;

    public RecycleAdapter(List<Images> imagesList,Context context){
        this.imagesList = imagesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Images images = imagesList.get(position);
        holder.AlbumTitle.setText("Image "+images.getImageid());
        Glide.with(context).load(images.getImagePath()).into(holder.Album);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView Album;
        TextView AlbumTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            Album = itemView.findViewById(R.id.album_image);
            AlbumTitle = itemView.findViewById(R.id.album_title);
        }
    }

    public void addImages(List<Images> images){
        for (Images im : images){
            imagesList.add(im);
        }
        notifyDataSetChanged();
    }
}
