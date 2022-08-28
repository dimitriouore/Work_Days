package com.dimitriou.workdays.AdapterViewModel;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dimitriou.workdays.Database.Days;
import com.dimitriou.workdays.R;

public class DaysAdapter extends ListAdapter<Days, DaysAdapter.DaysHolder> {

    public DaysAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Days> DIFF_CALLBACK = new DiffUtil.ItemCallback<Days>() {
        @Override
        public boolean areItemsTheSame(@NonNull Days oldItem, @NonNull Days newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Days oldItem, @NonNull Days newItem) {
            return oldItem.getDay().equals(newItem.getDay()) &&
                    oldItem.getMonth().equals(newItem.getMonth()) &&
                    oldItem.getYear().equals(newItem.getMonth()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getPaid().equals(newItem.getPaid());
        }
    };

    @NonNull
    @Override
    public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new DaysHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull DaysHolder holder, int position) {
        Days currentDay = getItem(position);
        String fullDate = currentDay.getDay() + "/" + currentDay.getMonth() + "/" + currentDay.getYear();
        holder.date.setText(fullDate);
        String type = currentDay.getType();
        if (type.equals("Double")){
            holder.type.setText(R.string.Double);
        }else if(type.equals("Half")){
            holder.type.setText(R.string.half);
        }else {
            holder.type.setText(currentDay.getType());
        }
        String pay = currentDay.getPaid();
        if(pay.equals("Yes")){
            holder.pay.setVisibility(View.VISIBLE);
        }
        if(pay.equals("No")){
            holder.pay.setVisibility(View.GONE);
        }
    }

    public Days getDaysAt(int position){
        return getItem(position);
    }

    static class DaysHolder extends RecyclerView.ViewHolder{
        private final TextView date;
        private final TextView type;
        private final TextView pay;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.datePick);
            type = itemView.findViewById(R.id.shiftShow);
            pay = itemView.findViewById(R.id.paid);
        }
    }
}
