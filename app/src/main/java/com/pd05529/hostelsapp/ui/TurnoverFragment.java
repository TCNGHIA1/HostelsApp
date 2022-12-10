package com.pd05529.hostelsapp.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pd05529.hostelsapp.DAO.InComeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.adapter.InComeAdapter;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Calendar;


public class TurnoverFragment extends Fragment {

    public static TurnoverFragment newInstance() {
        TurnoverFragment fragment = new TurnoverFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView tvTotal,tvPay;
    Spinner spnSort;
    ListView lvTurnover;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turnover, container, false);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvPay = view.findViewById(R.id.tvPay);
        spnSort = view.findViewById(R.id.spnSort);
        lvTurnover = view.findViewById(R.id.lvTurnover);
        return view;
    }
    InComeDAO inComeDAO;
    InComeAdapter inComeAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inComeDAO = new InComeDAO(getActivity());
        tvTotal.setText(Converter.toStr(inComeDAO.getTotal()));
        tvPay.setText(Converter.toStr(inComeDAO.getPay()));

        inComeAdapter = new InComeAdapter(getActivity());

        String[] strings = {"Tất cả","Theo tháng"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,strings);
        spnSort.setAdapter(spnAdapter);
        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        inComeAdapter.setList(inComeDAO.getInCome());
                        lvTurnover.setAdapter(inComeAdapter);
                        break;
                    }
                    case 1:{
                            Calendar calendar = Calendar.getInstance();
                            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                            int mMonth = calendar.get(Calendar.MONTH);
                            int mYear = calendar.get(Calendar.YEAR);
                            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    String str = (month+1)+"-"+year;
                                    inComeAdapter.setList(inComeDAO.getInComeRoom(str));
                                    lvTurnover.setAdapter(inComeAdapter);
                                }
                            };
                            int style = AlertDialog.THEME_HOLO_LIGHT;
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, mYear, mMonth, mDay);
                            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(getResources().getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                            datePickerDialog.show();
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}