package com.pd05529.hostelsapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.OwnerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Owner;


public class PassChangeFragment extends Fragment {

    public static PassChangeFragment newInstance() {
        PassChangeFragment fragment = new PassChangeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextInputLayout tilPass,tilNewPass,tilCheckNewPass;
    Button btnSave,btnCancel;
    public OwnerDAO dao;
    Owner obj;
    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TextLayout
        tilPass = view.findViewById(R.id.tilPass);
        tilNewPass = view.findViewById(R.id.tilNewPass);
        tilCheckNewPass = view.findViewById(R.id.tilCheckNewPass);
        //Buton
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);

        dao = new OwnerDAO(getActivity());
        bundle = getActivity().getIntent().getExtras();
        //Thông tin quản lí
        obj = dao.getId(String.valueOf(bundle.getInt("id")));
        btnCancel.setOnClickListener(v ->{
            Cancel();
        });
        btnSave.setOnClickListener(v->{
            ChangePass();
        });

    }
    public void ChangePass(){
        if(!validate(tilPass)||!validate(tilNewPass)||!validate(tilCheckNewPass)||
                !validate(tilNewPass,tilCheckNewPass)||!validate(obj.getPassword(),tilPass)) {
            return;
        }
        //Thay đổi mật khẩu
        obj.setPassword(tilNewPass.getEditText().getText().toString().trim());
        dao.update(obj);
        if (dao.update(obj)>0){
            Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            Cancel();
        }else{
            Toast.makeText(getActivity(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
        }

    }
    public void Cancel(){
        tilPass.getEditText().setText("");
        tilNewPass.getEditText().setText("");
        tilCheckNewPass.getEditText().setText("");
        tilPass.setError("");
        tilNewPass.setError("");
        tilCheckNewPass.setError("");
    }
    //Kiểm tra dữ liệu nhập vào
    public boolean validate(TextInputLayout input){
        String str = input.getEditText().getText().toString().trim();
        if(str.isEmpty()){
            input.setError("Không để trống dữ liệu");
            return false;
        }else{
            input.setError("");
            return true;
        }
    }
    public boolean validate(String str,TextInputLayout input){
        if(!input.getEditText().getText().toString().trim().equals(str)){
            input.setError("Không trùng khớp! Vui lòng nhập lại");
            return false;
        }else{
            input.setError("");
            return true;
        }
    }
    public boolean validate(TextInputLayout input1,TextInputLayout input2){
        String str1 = input1.getEditText().getText().toString().trim();
        String str2 = input2.getEditText().getText().toString().trim();
        if(!str1.equals(str2)){
            input2.setError("Không trùng khớp! Vui lòng nhập lại");
            return false;
        }else{
            input2.setError("");
            return true;
        }
    }

}