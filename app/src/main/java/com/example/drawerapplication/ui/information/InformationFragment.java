package com.example.drawerapplication.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.drawerapplication.databinding.FragmentInformationBinding;

public class InformationFragment extends Fragment {

    private FragmentInformationBinding binding;

    private EditText name, address, age, contact;
    private Button btnAdd, btnViewData;
    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        name = binding.name;
        address = binding.address;
        age = binding.age;
        contact = binding.contact;

        btnAdd = binding.add;
        btnViewData = binding.viewdata;

        mDatabaseHelper = new DatabaseHelper(getActivity());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.length() != 0 && address.length() != 0
                && age.length() != 0 && contact.length() != 0){

                    mDatabaseHelper.addData(name.getText().toString(), address.getText().toString(),
                            age.getText().toString().trim(), Long.valueOf(contact.getText().toString().trim()));
                    toastMessages("Data Successfully Inserted!");

                }else {
                    toastMessages("Please complete all the requirements needed");
                }

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void toastMessages(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

}