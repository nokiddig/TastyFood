package com.example.tastyfoods.mvvm.view;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tastyfoods.R;
import com.example.tastyfoods.mvvm.adapter.CartAdapter;
import com.example.tastyfoods.mvvm.model.Bill;
import com.example.tastyfoods.mvvm.model.CartDetail;
import com.example.tastyfoods.mvvm.viewmodels.cartdetail.CartViewModel;
import com.example.tastyfoods.mvvm.viewmodels.orders.BillViewModel;
import com.example.tastyfoods.mvvm.viewmodels.profile.ProfileViewModel;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "phoneNumber";
    public static final boolean COMPLETED = true;

    private CartAdapter cartAdapter;
    private String phoneNumber;
    private AppCompatButton buttonCheckout;
    private RecyclerView recyclerView;
    private TextView total;
    public CartFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String phoneNumber) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        this.init(view);
        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartDetails(phoneNumber).observe(getViewLifecycleOwner(), new Observer<List<CartDetail>>() {
            @Override
            public void onChanged(List<CartDetail> cartDetails) {
                cartAdapter = new CartAdapter(getContext(), cartDetails);
                recyclerView.setAdapter(cartAdapter);
            }
        });
        cartViewModel.getTotal().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                total.setText(String.valueOf(integer));
            }
        });
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalBill = Integer.parseInt(total.getText().toString());
                if (ProfileViewModel.getInstance().getUser().getValue().getMoney()>=totalBill) {
                    Bill bill = new Bill(0,totalBill, !COMPLETED, new Date(), phoneNumber, "");
                    BillViewModel billViewModel = new BillViewModel();
                    billViewModel.saveBill(bill, cartViewModel.getListCart().getValue());
                    cartViewModel.clearCart();
                }
            }
        });
        return view;
    }


    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        total = view.findViewById(R.id.textViewTotalMoney);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        buttonCheckout = view.findViewById(R.id.buttonCheckout);
    }
}