package com.pd05529.hostelsapp.ui;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.SerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Ser;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Date;


public class ServiceFragment extends Fragment {

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    private Toolbar toolbar;
    private TextInputLayout tilWater, tilElec;
    private TextView tvDateElec, tvDateWater;
    private SerDAO dao;
    private Ser objWater, objElec;
    private Button btnCancel, btnChange, btnSave;

    int water, elec;
    IntentFilter intentFilter;
    private boolean mode = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //maping
        tilWater = view.findViewById(R.id.tilWater);
        tilElec = view.findViewById(R.id.tilElec);
        tvDateElec = view.findViewById(R.id.tvDateElec);
        tvDateWater = view.findViewById(R.id.tvDateWater);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnChange = view.findViewById(R.id.btnChange);
        btnSave = view.findViewById(R.id.btnSave);

        //call service dao
        dao = new SerDAO(getActivity());
        objElec = dao.getAll().get(0);
        objWater = dao.getAll().get(1);
        //input data
        tilElec.getEditText().setText(String.valueOf(objElec.getPrice()));
        tilWater.getEditText().setText(String.valueOf(objWater.getPrice()));
        tvDateWater.setText(Converter.toTimestamp(objWater.getDate()));
        tvDateElec.setText(Converter.toTimestamp(objElec.getDate()));
        //Show button cancel, save -- hide button change
        btnChange.setOnClickListener(v -> {
            addOnClickButton(mode);
            mode = !mode;
        });
        btnCancel.setOnClickListener(v -> {
            addOnClickButton(mode);
            mode = !mode;
        });

        btnSave.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Bạn chắc muốn thay đổi?")
                    .setTitle("Xác nhận")
                    .setPositiveButton("Đóng", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setNegativeButton("Đồng ý", (dialog, which) -> {
                        saveService();
                    });
            builder.create().show();
        });
    }

    private void saveService() {
        water = Integer.parseInt(tilWater.getEditText().getText().toString().trim());
        elec = Integer.parseInt(tilElec.getEditText().getText().toString().trim());
        //data
        objElec.setDate(new Date());
        objWater.setDate(new Date());
        objElec.setPrice(elec);
        objWater.setPrice(water);
        //update
        if (dao.update(objElec) == 1 && dao.update(objWater) == 1) {
            Toast.makeText(getActivity(), "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
            Log.i("//CHECK", dao.getId(1 + "").toString());
        }
    }

    private void addOnClickButton(boolean mode) {
        if (mode) {
            btnCancel.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
            btnChange.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            tilElec.setEnabled(true);
            tilWater.setEnabled(true);
        } else {
            btnCancel.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            btnChange.setVisibility(View.VISIBLE);
            tilElec.setEnabled(false);
            tilWater.setEnabled(false);
        }
    }
}