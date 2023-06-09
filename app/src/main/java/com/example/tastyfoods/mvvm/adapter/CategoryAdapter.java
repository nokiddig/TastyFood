package com.example.tastyfoods.mvvm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastyfoods.R;
import com.example.tastyfoods.mvvm.model.Category;
import com.example.tastyfoods.mvvm.view.home.HomeFragment;
import com.example.tastyfoods.mvvm.view.search.SearchFragment;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mCategories;
    private Context mContext;

    public CategoryAdapter(Context context, List<Category> categories) {
        mContext = context;
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mCategories.get(position);

        holder.textViewName.setText(category.getName());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        Glide.with(mContext)
                .load(category.getImage())
                .centerCrop()
                .placeholder(R.drawable.anh)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show product by category
                Bundle bundle = new Bundle();
                bundle.putSerializable("search", String.valueOf(category.getCategoryId()));
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, searchFragment)
                        .addToBackStack("search")
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.productImage);
            textViewName = itemView.findViewById(R.id.textviewPrice);
        }
    }

    public List<Category> getMCategories() {
        return mCategories;
    }

    public void setMCategories(List<Category> mCategories) {
        this.mCategories = mCategories;
    }
}