package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {

    private FirebaseAuth mAuth;//chuc nang xac thuc nguoi dung
    public FirebaseDatabase database;//co so du lieu duoc cung cap boi Google va duoc luu tru tren nen tang cloud
    MainActivity mainActivity;

    public SigninFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SigninFragment.
     */
    public static SigninFragment newInstance() {
        SigninFragment fragment = new SigninFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();//chi cho phep mot thread chay mot phuong thuc tai cung mot thoi diem
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        Button register = view.findViewById(R.id.buttonRegister);
        register.setOnClickListener(v -> {

            EditText nameTextView = view.findViewById(R.id.editTextPersonName);
            EditText emailTextView = view.findViewById(R.id.editTextTextEmailAddressSignin);
            EditText passwordTextView = view.findViewById(R.id.editTextTextPasswordSignin);
            EditText phoneTextView = view.findViewById(R.id.editTextPhone);

            String name = nameTextView.getText().toString();
            String email = emailTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            String phone = phoneTextView.getText().toString();
            //get tra ve Editable, nen muon ra chuoi thuan thi phai them buoc toString

            mainActivity = (MainActivity) getActivity();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty())
            {
                Toast.makeText(mainActivity, "\n" +
                        "Please fill in all required information", Toast.LENGTH_LONG).show();
            }   // mot thong bao nho ma ung dung gui toi nguoi dung, no xuat hien gan phia cuoi man hinh mot khoang thoi gian dai 3,5s
            else {
                User user = new User(name, email, password, phone);
                Register(user);
            }
        });

        return view;
    }

    public void Register(User user) {
        String userName = user.getEmail();
        String password = user.getPassword();
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(mainActivity, task -> {
                    if (task.isSuccessful()) {
                        // task la mot tap hop cac activity ma nguoi dung tuong tac khi thuc hien mot cong viec nhat dinh
                        //isSuccessful() de tim ra code trang thai torng khoang 200-300 xac dinh mot yeu cau thanh cong
                        DatabaseReference myRef = database.getReference("users").child(user.getPhone());
                        myRef.setValue(user);
                        mainActivity.Login(userName, password);
                    } else {
                        // If sign in fails, display a message to the user.
                        mainActivity.setFragment(new SigninFragment());
                        Toast.makeText(mainActivity, "Something went wrong, Please try again.\nNote: \n" +
                                "A valid email and password with at least 6 characters is required.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}