package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.BillDAO;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.DAO.SerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.support.Converter;

public class DetailBill {
    TextView tvId,tvNumElec,tvElec,tvNumWater,tvWater,tvPriceMore,tvPrice,tvPay,tvOwe,tvPriceOwe;
    TextInputLayout tilNote;
    AlertDialog dialog;
    BillDAO billDAO;
    public DetailBill(Context context, Bill... bills){
        View view = LayoutInflater.from(context).inflate(R.layout.detail_dialog_bill,null);
        tvId =view.findViewById(R.id.tvId);
        tvNumElec =view.findViewById(R.id.tvNumElec);
        tvElec =view.findViewById(R.id.tvElec);
        tvNumWater =view.findViewById(R.id.tvNumWater);
        tvWater =view.findViewById(R.id.tvWater);
        tvPriceMore =view.findViewById(R.id.tvPriceMore);
        tvPrice =view.findViewById(R.id.tvPrice);
        tvPay =view.findViewById(R.id.tvPay);
        tvOwe =view.findViewById(R.id.tvOwePrice);
        tvPriceOwe =view.findViewById(R.id.tvPriceOwe);
        tilNote = view.findViewById(R.id.tilNote);

        billDAO = new BillDAO(context);
        Bill bill = bills[0];

        tvId.setText(bill.getIdRoom());
        int elec = bill.getNewElec()- bill.getOldElec();
        int water = bill.getNewWater()- bill.getOldWater();

        SerDAO serDAO = new SerDAO(context);
        RoomTypeDAO roomTypeDAO = new RoomTypeDAO(context);
        RoomDAO roomDAO = new RoomDAO(context);
        long priceRoom = roomTypeDAO.getId(String.valueOf(roomDAO.getId(bill.getIdRoom()).getIdType())).getPrice();

        long amountWater = water * (serDAO.getId(String.valueOf(2)).getPrice());
        long amountElec = elec * (serDAO.getId(String.valueOf(1)).getPrice());
        tvNumElec.append("\n("+bill.getOldElec()+" - "+ bill.getNewElec()+")");
        tvNumWater.append("\n("+bill.getOldWater()+" - "+ bill.getNewWater()+")");
        tvElec.setText(Converter.toStr(amountElec) +" VN??");
        tvWater.setText(Converter.toStr(amountWater)+" VN??");
        tvPrice.setText(Converter.toStr(priceRoom)+" VN??");
        tvPay.setText(Converter.toStr(bill.getPrePay())+" VN??");
        tvPriceMore.setText(Converter.toStr(bill.getMore())+" VN??");
        String priceOwe;
        if(bill.getOwe()>=0){
            tvOwe.setText("Ti???n kh??ch n???:");
            priceOwe = Converter.toStr(bill.getOwe())+" VN??";
        }else {
            tvOwe.setText("Ti???n ch??? n???:");
            long owe = -(bill.getOwe());
            priceOwe = Converter.toStr(owe)+" VN??";
            tvPriceOwe.setTextColor(Color.parseColor("#FA312A"));
        }
        tvPriceOwe.setText(priceOwe);

        tilNote.getEditText().setText(bill.getNote());
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view)
                .setNegativeButton("????ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
    }
    public void show(){
        dialog.show();
    }
}
