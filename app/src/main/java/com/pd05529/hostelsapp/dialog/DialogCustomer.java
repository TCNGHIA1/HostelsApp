package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Calendar;
import java.util.Date;

public class DialogCustomer {
    private AlertDialog dialog;
    public TextInputLayout tilId;
    private TextInputLayout tilName, tilPhone, tilCmnd, tilAddress, tilEmail;
    private EditText edBirthDay;
    private Spinner spnGender;
    private Button btnBirthDay, btnSave, btnCancel;
    private boolean mode;
    private CustomerDAO customerDAO;

    public DialogCustomer(Context context, Customer... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_customer, null);
        tilId = view.findViewById(R.id.tilId);
        tilName = view.findViewById(R.id.tilName);
        tilPhone = view.findViewById(R.id.tilPhone);
        tilCmnd = view.findViewById(R.id.tilCmnd);
        tilAddress = view.findViewById(R.id.tilAddress);
        tilEmail = view.findViewById(R.id.tilEmail);
        spnGender = view.findViewById(R.id.spnGender);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnBirthDay = view.findViewById(R.id.btnBirthDay);
        //dao
        customerDAO = new CustomerDAO(context);
        //spinner
        String[] spnItem = new String[]{"Nam", "Nữ"};
        spnGender.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, spnItem));
        if (args.length > 0 && args != null) {
            Customer obj = args[0];
            tilId.getEditText().setText(obj.getIdCus());
            tilName.getEditText().setText(obj.getName());
            tilPhone.getEditText().setText(String.valueOf(obj.getPhone()));
            tilCmnd.getEditText().setText(String.valueOf(obj.getCmnd()));
            tilAddress.getEditText().setText(obj.getAddress());
            tilEmail.getEditText().setText(obj.getEmail());
            spnGender.setSelection(obj.getGender());
            btnBirthDay.setText(Converter.toTimestamp(obj.getBirthDay()));
            mode = true;
        } else {
            mode = false;
        }


        //add dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view);
        dialog = builder.create();
        //Event click button
        //BirthDay
        btnBirthDay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mMonth = calendar.get(Calendar.MONTH);
            int mYear = calendar.get(Calendar.YEAR);
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    btnBirthDay.setText(dayOfMonth + "-" + (month + 1) +"-" +(year));
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
            if (!validate(tilId) ||!checkValue(tilId) ||!validate(tilName) || !validate(tilCmnd) || !validate(tilPhone)||!validate(btnBirthDay)) {
                return;
            }
            String id = tilId.getEditText().getText().toString().trim();
            String name = tilName.getEditText().getText().toString().trim();
            String address = tilAddress.getEditText().getText().toString().trim();
            String email = tilEmail.getEditText().getText().toString().trim();
            long phone = Long.parseLong(tilPhone.getEditText().getText().toString().trim());
            long cmnd = Long.parseLong(tilCmnd.getEditText().getText().toString().trim());
            Date birthDay = Converter.toDate(btnBirthDay.getText().toString().trim());
            int gender = spnGender.getSelectedItemPosition();
            //BroadCast
            Customer obj = new Customer(id, name, gender, email, phone, cmnd, address, birthDay);
            Intent intentBR = new Intent();
            Bundle bundle = new Bundle();
            if (mode) {
                bundle.putBoolean("MODE", true);
                dialog.dismiss();
            } else {
                bundle.putBoolean("MODE", false);
            }
            bundle.putSerializable("_CUSTOMER", obj);
            intentBR.putExtras(bundle);
            intentBR.setAction("CHECK_CUSTOMER");
            context.sendBroadcast(intentBR);
            dialog.dismiss();

        });

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

    public void show() {
        dialog.show();
    }

    private boolean validate(Button editText) {
        String str = editText.getText().toString().trim();
        if (str.isEmpty()) {
            editText.setError("Không để trống dữ liệu!");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }
    private boolean checkValue(TextInputLayout inputLayout){
        String str = inputLayout.getEditText().getText().toString().trim().toUpperCase();
        if(customerDAO.getId(str)!=null){
            inputLayout.setError("Mã khách hàng đã tồn tại!");
            return false;
        }else{
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
