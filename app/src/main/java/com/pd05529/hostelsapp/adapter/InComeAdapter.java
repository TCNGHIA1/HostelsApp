package com.pd05529.hostelsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.InCome;
import com.pd05529.hostelsapp.support.Converter;

import java.util.List;

public class InComeAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    public List<InCome> list ;
    public InComeAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    public List<InCome> getList() {
        return list;
    }

    public void setList(List<InCome> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list==null){
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class InComeViewHolder{
        TextView tvName,tvTotal,tvPay;
        public InComeViewHolder(View view){
            tvName = view.findViewById(R.id.tvName);
            tvTotal = view.findViewById(R.id.tvTotal);
            tvPay = view.findViewById(R.id.tvPay);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InComeViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_item_turnover,null,false);
            holder = new InComeViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder =(InComeViewHolder) convertView.getTag();
        }
        holder.tvTotal.setText(Converter.toStr(list.get(position).getTotal()));
        holder.tvName.setText(list.get(position).getName());
        holder.tvPay.setText(Converter.toStr(list.get(position).getPay()));
        return convertView;
    }
}
