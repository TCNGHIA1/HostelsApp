package com.pd05529.hostelsapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pd05529.hostelsapp.DAO.OwnerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.dialog.DialogOwner;
import com.pd05529.hostelsapp.models.Owner;
import com.pd05529.hostelsapp.support.Converter;


public class InforFragment extends Fragment {

    public static InforFragment newInstance() {
        InforFragment fragment = new InforFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView tvName, tvEmail, tvUsername, tvPassword, tvGender, tvPhone, tvCmnd, tvBirthDay, tvAddress;
    Button change_btn;
    Owner obj;
    public OwnerDAO ownerDAO;
    Bundle data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_infor, container, false);
        intentFilter = new IntentFilter();
        ownerDAO = new OwnerDAO(getActivity());
        data = getActivity().getIntent().getExtras();


        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvPassword = view.findViewById(R.id.tvPassword);
        tvGender = view.findViewById(R.id.tvGender);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvCmnd = view.findViewById(R.id.tvCmnd);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvBirthDay = view.findViewById(R.id.tvBirthDay);
        change_btn = view.findViewById(R.id.change_btn);

        return view;
    }

    IntentFilter intentFilter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obj = ownerDAO.getId(String.valueOf(data.getInt("id")));
        updateView(obj);

        intentFilter.addAction("_OWNER_ACTION");
        change_btn.setOnClickListener(v->{
            showDialogOwner();
        });

    }

    InforFragment current = this;
    private void showDialogOwner() {
        DialogOwner dialogOwner = new DialogOwner(getActivity(),current,obj);
        dialogOwner.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "_OWNER_ACTION":{
                    Bundle bundle = intent.getExtras();
                    boolean mode = bundle.getBoolean("MODE");
                    Owner owner = (Owner) bundle.getSerializable("_OWNER_OBJ");
                    if (mode) {
                        if (ownerDAO.update(obj) != -1) {
                            updateView(obj);
                            Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    };
    public void updateView(Owner obj){
        tvName.setText(obj.getName());
        tvEmail.setText(obj.getEmail());
        tvUsername.setText(obj.getUsername());
        tvPassword.setText(obj.getPassword());
        if(obj.getGender()==0){
            tvGender.setText("Nam");
        }else{
            tvGender.setText("Nữ");
        }
        tvPhone.setText(obj.getPhone()+"");
        tvCmnd.setText(obj.getCmnd()+"");
        tvBirthDay.setText(Converter.toTimestamp(obj.getDate()));
        tvAddress.setText(obj.getAddress());
    }
}