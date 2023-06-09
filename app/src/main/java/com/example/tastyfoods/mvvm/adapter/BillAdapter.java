package com.example.tastyfoods.mvvm.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastyfoods.R;
import com.example.tastyfoods.mvvm.model.Bill;

import java.util.List;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.billViewAdapter>{
   private List<Bill> listBills;
   private final Context mContext;

    public BillAdapter(List<Bill> listBills, Context mContext) {
        this.listBills = listBills;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public billViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bill, parent, false);
        return new billViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull billViewAdapter holder, int position) {
        Bill bill = listBills.get(position);
        if (bill == null)
        {
            return;
        }
        holder.txtDateTime.setText(String.valueOf(bill.getDateTime()));

        holder.txtTotal.setText(String.valueOf(bill.getTotalMoney()));
    }

    @Override
    public int getItemCount() {
        if(listBills != null)
        {
            return listBills.size();
        }
        return 0;
    }

    public static class billViewAdapter extends RecyclerView.ViewHolder{
        private TextView  txtTotal,  txtDateTime;
        private Button btnReset;
        public billViewAdapter(@NonNull View itemView) {
            super(itemView);
            txtTotal =itemView.findViewById(R.id.txt_total);
            txtDateTime=itemView.findViewById(R.id.txt_datetime);
            btnReset =itemView.findViewById(R.id.btnReset);
        }
    }
}
