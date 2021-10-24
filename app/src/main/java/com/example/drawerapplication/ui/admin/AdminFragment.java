package com.example.drawerapplication.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drawerapplication.R;
import com.example.drawerapplication.ui.information.ListDataActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button login;
    EditText username, password;
    String email = "admin";
    String pass = "admin123";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(getActivity(), "Empty data provided",Toast.LENGTH_SHORT).show();
                }else if (username.getText().toString().equals(email)){

                    if (password.getText().toString().equals(pass)){
                        Toast.makeText(getActivity(), "Success LogIn", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), TimeDateActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getActivity(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}