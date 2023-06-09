package com.example.tastyfoods.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tastyfoods.R;
import com.example.tastyfoods.mvvm.model.HomeSliderItem;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.SlideViewHolder> {

    private final List<HomeSliderItem> SLIDER_ITEMS;
    private ViewPager2 viewPager2;

    public HomeSliderAdapter(List<HomeSliderItem> sliderItems, ViewPager2 viewPager2) {
        this.SLIDER_ITEMS = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImage(SLIDER_ITEMS.get(position));
    }

    @Override
    public int getItemCount() {
        return SLIDER_ITEMS.size();
    }

    static class SlideViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(HomeSliderItem homeSliderItem){
            imageView.setImageResource(homeSliderItem.getIMAGE());
        }
    }
}
