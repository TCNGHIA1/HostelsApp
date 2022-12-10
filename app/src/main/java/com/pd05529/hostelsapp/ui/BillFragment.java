package com.pd05529.hostelsapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pd05529.hostelsapp.DAO.BillDAO;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.adapter.BillAdapter;
import com.pd05529.hostelsapp.dialog.DetailBill;
import com.pd05529.hostelsapp.dialog.DetailCustomer;
import com.pd05529.hostelsapp.dialog.DialogBill;
import com.pd05529.hostelsapp.dialog.DialogCustomer;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.models.Customer;


public class BillFragment extends Fragment {


    public static BillFragment newInstance() {
        BillFragment fragment = new BillFragment();

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
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    private FloatingActionButton open_btn, add_btn, del_btn;
    private RecyclerView recyclerView;
    private TextView tv_add_btn, tv_del_btn;

    private boolean clicked = false;


    private BillDAO billDAO;
    private RoomDAO roomDAO;
    private BillAdapter billAdapter;
    private IntentFilter intentFilter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        //intentFilter
        intentFilter = new IntentFilter();
        intentFilter.addAction("_BILL_ACTION");
        //mapping
        open_btn = view.findViewById(R.id.open_btn);
        add_btn = view.findViewById(R.id.add_btn);
        del_btn = view.findViewById(R.id.del_btn);
        tv_del_btn = view.findViewById(R.id.tv_del_btn);
        tv_add_btn = view.findViewById(R.id.tv_add_btn);
        //dao 
        billDAO = new BillDAO(getActivity());
        roomDAO = new RoomDAO(getActivity());
        
        //button floating
        open_btn.setOnClickListener(v -> {
            onAddButtonClicked();
        });

        add_btn.setOnClickListener(v -> {
            if(roomDAO.showRoom() ==null){
                Toast.makeText(getActivity(), "Tất cả phòng không có khách trọ", Toast.LENGTH_SHORT).show();
            }else{
                dialogBill = new DialogBill(getActivity(),null);
                dialogBill.show();
            }
        });
        del_btn.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Xóa tất cả dữ liệu")
                    .setMessage("Bạn có chắc chắn không?")
                    .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            billDAO.deleteAll();
                            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            resetAdapter();
                        }
                    })
                    .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        });
        //recyclerview
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        resetAdapter();
        billAdapter.setItemClickListener((v,position)->{
            showPopupMenu(v,position);
        });
    }
    BillFragment current = this;
    DialogBill dialogBill;
    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(current.getActivity(), v);
        popupMenu.inflate(R.menu.popup_menu);
        Bill obj =billAdapter.getItem(position);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_info: {
                        DetailBill dialog = new DetailBill(getActivity(), obj);
                        dialog.show();
                        return true;
                    }
                    case R.id.popup_update: {
                        dialogBill = new DialogBill(getActivity(),null, obj);
                        dialogBill.show();
                        return true;
                    }
                    case R.id.popup_delete: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn là muốn xóa?")
                                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //delete room from table
                                        billDAO.delete(String.valueOf(obj.getIdBill()));
                                        resetAdapter();
                                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    //BroadcastReceiver
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "_BILL_ACTION":{
                    Bundle bundle = intent.getExtras();
                    boolean mode = bundle.getBoolean("_MODE_BILL");
                    Bill obj =(Bill) bundle.getSerializable("_BILL_OBJ");
                    Log.i("//CHECK_BILL",obj.toString());
                    if(mode){
                        billDAO.update(obj);
                        Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(billDAO.insert(obj)!=1){
                            Toast.makeText(context, "Thêm mới thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            resetAdapter();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Thông tin cần tìm");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                billAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void resetAdapter() {
        billAdapter = new BillAdapter(getActivity().getApplicationContext(), billDAO.getAll());
        recyclerView.setAdapter(billAdapter);
    }

    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }


    private void setVisibility(boolean clicked) {
        if (!clicked) {
            add_btn.setVisibility(View.VISIBLE);
            del_btn.setVisibility(View.VISIBLE);
            tv_add_btn.setVisibility(View.VISIBLE);
            tv_del_btn.setVisibility(View.VISIBLE);
        } else {
            add_btn.setVisibility(View.INVISIBLE);
            del_btn.setVisibility(View.INVISIBLE);
            tv_add_btn.setVisibility(View.INVISIBLE);
            tv_del_btn.setVisibility(View.INVISIBLE);

        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            Animation rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);
            Animation fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
            add_btn.setAnimation(fromBottom);
            del_btn.setAnimation(fromBottom);
            tv_add_btn.setAnimation(fromBottom);
            tv_del_btn.setAnimation(fromBottom);
            open_btn.setAnimation(rotateOpen);
        } else {
            Animation rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
            Animation toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
            add_btn.setAnimation(toBottom);
            del_btn.setAnimation(toBottom);
            tv_add_btn.setAnimation(toBottom);
            tv_del_btn.setAnimation(toBottom);
            open_btn.setAnimation(rotateClose);
        }
    }

    private void setClickable(boolean clicked) {
        if (!clicked) {
            add_btn.setClickable(true);
            del_btn.setClickable(true);
        } else {
            add_btn.setClickable(false);
            del_btn.setClickable(false);
        }
    }


}