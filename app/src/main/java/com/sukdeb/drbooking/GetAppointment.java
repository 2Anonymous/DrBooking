package com.sukdeb.drbooking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GetAppointment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetAppointment() {

    }


    // TODO: Rename and change types and number of parameters
    public static GetAppointment newInstance(String param1, String param2) {
        GetAppointment fragment = new GetAppointment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    EditText etdate,etsec,etsdr,etpage,etrp,etpname;
    Button btnsubmit;
    RadioGroup redgp;
    RadioButton redb1,redb2;
    String Patient;
    DatePickerDialog.OnDateSetListener setListener;
    DatabaseReference mydata;
    ArrayList<String> List;
    ArrayAdapter<String> Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_appointment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etdate = getActivity().findViewById(R.id.etDate);
        etsec = getActivity().findViewById(R.id.etSec);
        etsdr = getActivity().findViewById(R.id.etSdr);
        etpage = getActivity().findViewById(R.id.etPage);
        etrp = getActivity().findViewById(R.id.etRtp);
        etpname = getActivity().findViewById(R.id.etPname);
        redgp = getActivity().findViewById(R.id.redGp);
        btnsubmit = getActivity().findViewById(R.id.btnSub1);
        redb1 = getActivity().findViewById(R.id.red1);
        redb2 = getActivity().findViewById(R.id.red2);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment();

            }
        });

        redgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.red1:
                        etpage.setVisibility(View.INVISIBLE);
                        etpname.setVisibility(View.INVISIBLE);
                        etrp.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Switched Own", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.red2:
                        etpage.setVisibility(View.VISIBLE);
                        etpname.setVisibility(View.VISIBLE);
                        etrp.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Switched Family", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        Calendar myCalendar = Calendar.getInstance();
        final int year = myCalendar.get(Calendar.YEAR);
        final int month = myCalendar.get(Calendar.MONTH);
        final int day = myCalendar.get(Calendar.DAY_OF_MONTH);


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        etdate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


       /*  class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            }

            public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                populateSetDate(yy, mm+1, dd);
            }
            public void populateSetDate(int year, int month, int day) {
                etdate.setText(month+"/"+day+"/"+year);
            }

        }

        etdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });*/

    }
    public void Appointment(){
        String date = etdate.getText().toString().trim();
        String section = etsec.getText().toString().trim();
        String selectDr = etsdr.getText().toString().trim();
        int Patientage = Integer.parseInt(etpage.getText().toString().trim());
        String relation = etrp.getText().toString().trim();
        String Patientname = etpname.getText().toString().trim();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Appointment").child("patient");
        Map map = new HashMap();
        map.put("date",date);
        map.put("section",section);
        map.put("selectDr",selectDr);
        map.put("pataientage",Patientage);
        map.put("relation",relation);
        map.put("patientname",Patientname);
        myRef.setValue(map);

    }

}