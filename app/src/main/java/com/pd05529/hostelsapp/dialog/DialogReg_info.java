package com.pd05529.hostelsapp.dialog;

import static android.view.LayoutInflater.from;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.models.Reg_info;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Calendar;
import java.util.Date;

public class DialogReg_info {
    private Spinner spnCus;
    private CustomerDAO customerDAO;
    private AlertDialog dialog;
    View view;
    public DialogReg_info(Context context, Register register) {
            view = from(context).inflate(R.layout.dialog_register_info, null);

            spnCus = view.findViewById(R.id.spnCus);

            customerDAO = new CustomerDAO(context);
            ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(context, android.R.layout.simple_list_item_1, customerDAO.showCustomer());
            spnCus.setAdapter(adapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view)
                    .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentBR = new Intent();
                            Bundle bundleBR = new Bundle();
                            Customer customer;
                            Reg_info reg_info;
                            customer = adapter.getItem(spnCus.getSelectedItemPosition());
                            reg_info = new Reg_info(register.getId(), customer.getIdCus());
                            bundleBR.putSerializable("_REG_CUS", reg_info);
                            bundleBR.putSerializable("_REG_ROOM", register);
                            intentBR.setAction("_REG_ACTION");
                            intentBR.putExtras(bundleBR);
                            context.sendBroadcast(intentBR);
                            dialog.dismiss();

                        }
                    })
                    .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
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
