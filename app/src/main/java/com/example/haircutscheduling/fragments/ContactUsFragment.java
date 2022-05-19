package com.example.haircutscheduling.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends Fragment {

    MainActivity mainActivity;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditAdminContactDetailsFragment.
     */
    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        mainActivity = (MainActivity) getActivity();
        SharedPreferences contact_prefs = mainActivity.getSharedPreferences(MainActivity.SHARED_PREFS_CONTACT, MODE_PRIVATE);
        //cho phep ban lua lai cac thong so ban da thiet lap truoc do
        String phone = contact_prefs.getString(MainActivity.PHONE, "89777402002");
        String email = contact_prefs.getString(MainActivity.EMAIL, "hieuminh735@gmail.com");
        String address = contact_prefs.getString(MainActivity.ADDRESS, "Ha Noi, Viet Nam");

        //hien thi mot van ban
        TextView contactPhone = view.findViewById(R.id.editTextContactPhone);
        contactPhone.setText(phone);

        TextView contactEmail = view.findViewById(R.id.editTextContactEmailAddress);
        contactEmail.setText(email);

        TextView contactAddress = view.findViewById(R.id.editTextContactAddress);
        contactAddress.setText(address);

        return view;
    }
}