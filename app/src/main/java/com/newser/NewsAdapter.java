package com.newser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.newser.Data.Constants;
import com.newser.Data.News;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sahni on 15/8/18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    ArrayList<News> news;

    public NewsAdapter(Context context, ArrayList<News> news){
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = layoutInflater.inflate(R.layout.news_item,parent,false);
        ViewHolder holder = new ViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        News newsItem = news.get(position);

        holder.toggleProgress();

        holder.title.setText(newsItem.getTitle());
        holder.content.setText(newsItem.getContent());

        Date date = new Date(newsItem.getTime().getSeconds()*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd-MMMM");
        holder.time.setText(simpleDateFormat.format(date));

        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(newsItem.getImg_link());
        imageRef.getBytes(Constants.ONE_MEGABYTE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes=task.getResult();
                    ByteArrayInputStream inputStream= new ByteArrayInputStream(bytes);
                    Bitmap bmp= BitmapFactory.decodeStream(inputStream);
                    holder.image.setImageBitmap(bmp);
                }
                holder.toggleProgress();
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView content;
        TextView title;
        TextView time;
        ConstraintLayout root;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            root = itemView.findViewById(R.id.rootLayout);
            progressBar = itemView.findViewById(R.id.progress);
        }

        public void toggleProgress() {
            if(progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.GONE);
                root.setAlpha(1f);
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                root.setAlpha(.3f);
            }
        }
    }
}
