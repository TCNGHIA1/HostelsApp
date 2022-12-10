package com.pd05529.hostelsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter implements Filterable {
    private List<Customer> list;
    private List<Customer> newList;
    private Context context;
    private int mode;

    //    public static int TYPE_VERTICAL = 1;
//    public static int TYPE_HOR = 2;
    public CustomerAdapter(Context context, List<Customer> list, int mode) {
        this.list = list;
        this.context = context;
        this.mode = mode;
    }

    public static ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        CustomerAdapter.itemClickListener = itemClickListener;
    }

    public void setList(List<Customer> list) {
        list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mode) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return -1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_customer_full, parent, false);
                return new CustomerOneViewHolder(view);
            }
            case 2: {
                View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_customer, parent, false);
                return new CustomerTwoViewHolder(view);
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        switch (mode) {
            case 1: {
                Customer obj = list.get(position);
                ((CustomerOneViewHolder) holder).tvName.setText(obj.getName());
                ((CustomerOneViewHolder) holder).tvPhone.setText("0" + obj.getPhone());
                ((CustomerOneViewHolder) holder).tvCmnd.setText(String.valueOf(obj.getCmnd()));
                ((CustomerOneViewHolder) holder).tvId.setText(obj.getIdCus());
                ((CustomerOneViewHolder) holder).itemView.setOnClickListener(v -> {
                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(v, position);
                    }
                });
                break;
            }
            case 2: {
                Customer obj = list.get(position);
                ((CustomerTwoViewHolder) holder).tvName.setText(obj.getName());
                ((CustomerTwoViewHolder) holder).tvPhone.setText("0" + obj.getPhone());
                ((CustomerTwoViewHolder) holder).tvCmnd.setText(String.valueOf(obj.getCmnd()));
                ((CustomerTwoViewHolder) holder).itemView.setOnClickListener(v -> {
                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(v, position);
                    }
                });
                break;
            }
        }

    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Customer getItem(int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }


    public class CustomerOneViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCmnd, tvPhone, tvId;

        public CustomerOneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCmnd = itemView.findViewById(R.id.tvCmnd);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvId = itemView.findViewById(R.id.tvId);
        }

    }

    public class CustomerTwoViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCmnd, tvPhone;

        public CustomerTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCmnd = itemView.findViewById(R.id.tvCmnd);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Customer item : newList) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getIdCus().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}