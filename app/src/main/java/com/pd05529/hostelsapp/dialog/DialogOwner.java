package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.OwnerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Owner;
import com.pd05529.hostelsapp.support.Converter;
import com.pd05529.hostelsapp.ui.InforFragment;

import java.util.Calendar;
import java.util.Date;

public class DialogOwner {
    public AlertDialog dialog;
    TextInputLayout tilName, tilPhone, tilCmnd, tilEmail, tilAddress;
    Spinner spnGender;
    Button btnSave, btnCancel, date_btn;
    OwnerDAO ownerDAO;
    boolean mode;
    Owner owner;
    public DialogOwner(Context context, InforFragment fragment, Owner... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_owner, null);
        tilName = view.findViewById(R.id.tilName);
        tilPhone = view.findViewById(R.id.tilPhone);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilAddress = view.findViewById(R.id.tilAddress);
        tilCmnd = view.findViewById(R.id.tilCmnd);
        spnGender = view.findViewById(R.id.spnGender);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        date_btn = view.findViewById(R.id.date_btn);


        if (args.length > 0) {
            tilName.getEditText().setText(args[0].getName());
            tilPhone.getEditText().setText("" + args[0].getPhone());
            tilEmail.getEditText().setText(args[0].getEmail());
            tilAddress.getEditText().setText(args[0].getAddress());
            tilCmnd.getEditText().setText(args[0].getCmnd() + "");
            date_btn.setText(Converter.toTimestamp(args[0].getDate()));
            owner = args[0];
            mode = true;
        } else {
            date_btn.setText(getToday());
            mode = false;
        }
        //spinner
        String[] spnItem = new String[]{"Nam", "Nữ"};
        spnGender.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, spnItem));

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        dialog = builder.create();

        //BirthDay
        date_btn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mMonth = calendar.get(Calendar.MONTH);
            int mYear = calendar.get(Calendar.YEAR);
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date_btn.setText(dayOfMonth + "-" + (month + 1) +"-"+ year);
                }
            };
            int style = AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            if (!validate(tilName) || !validate(tilPhone) || !validate(tilEmail)) {
                return;
            }
            String name = tilName.getEditText().getText().toString().trim();
            String address = tilAddress.getEditText().getText().toString().trim();
            String email = tilEmail.getEditText().getText().toString().trim();
            long cmnd = Long.parseLong(tilCmnd.getEditText().getText().toString().trim());
            int phone = Integer.parseInt(tilPhone.getEditText().getText().toString().trim());
            Date birthDay = Converter.toDate(date_btn.getText().toString().trim());
            int gender = spnGender.getSelectedItemPosition();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            if (mode) {
                owner.setName(name); owner.setAddress(address);owner.setEmail(email);
                owner.setCmnd(cmnd);owner.setPhone(phone);owner.setDate(birthDay);
                owner.setGender(gender);
                    if (fragment.ownerDAO.update(owner) != -1) {
                        Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                    }
                    fragment.updateView(owner);
                    dialog.dismiss();
            }

        });
    }

    public void show() {
        dialog.show();
    }

    private boolean validate(TextInputLayout input) {
        String str = input.getEditText().getText().toString().trim();
        if (str.isEmpty()) {
            input.setError("Không để trống dữ liệu");
            return false;
        } else {
            input.setError("");
            return true;
        }
    }

    private String getToday() {
        Calendar calendar = Calendar.getInstance();
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = calendar.get(Calendar.MONTH);
        mMonth = mMonth + 1;
        int mYear = calendar.get(Calendar.YEAR);
        return mDay + "-" + mMonth + "-" + mYear;
    }
}
