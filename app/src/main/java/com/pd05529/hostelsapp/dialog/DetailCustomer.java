package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.support.Converter;

public class DetailCustomer {
    private AlertDialog dialog;
    TextView tvId, tvName, tvGender, tvEmail, tvPhone, tvAddress, tvBirthDay, tvCmnd;

    public DetailCustomer(Context context, Customer... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_dialog_customer, null);
        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvGender = view.findViewById(R.id.tvGender);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvBirthDay = view.findViewById(R.id.tvBirthDay);
        tvCmnd = view.findViewById(R.id.tvCmnd);
        //
        Customer obj = args[0];
        tvId.setText(obj.getIdCus());
        tvName.setText(obj.getName());
        if (obj.getGender() == 0) {
            tvGender.setText("Nam");
        } else {
            tvGender.setText("Nữ");
        }
        tvEmail.setText(obj.getEmail());
        tvPhone.setText("0" + obj.getPhone());
        tvAddress.setText(obj.getAddress());
        tvBirthDay.setText(Converter.toTimestamp(obj.getBirthDay()));
        tvCmnd.setText(String.valueOf(obj.getCmnd()));

        //AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }
}
