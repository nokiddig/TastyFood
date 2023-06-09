package com.example.tastyfoods.mvvm.viewmodels.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastyfoods.mvvm.model.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Food>> mFoods = new MutableLiveData<>();

    public MutableLiveData<List<Food>> findByName(String name) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("food").addSnapshotListener((value, error) -> {
            try {
                if (value != null) {
                    List<Food> foods = new ArrayList<>();
                    int sum = 0;
                    for (QueryDocumentSnapshot doc : value) {
                        Food food = doc.toObject(Food.class);
                        if (food.getName().toLowerCase().contains(name.toLowerCase())) {
                            foods.add(food);
                        }
                    }
                    mFoods.postValue(foods);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return mFoods;
    }

    public MutableLiveData<List<Food>> findByCategory(int categoryId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("food").whereEqualTo("categoryId", categoryId)
            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Food> foods = new ArrayList<>();
                    int sum = 0;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Food food = doc.toObject(Food.class);
                        foods.add(food);
                    }
                    mFoods.postValue(foods);
                }
            });
        return mFoods;
    }
}
