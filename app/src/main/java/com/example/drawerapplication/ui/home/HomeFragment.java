package com.example.drawerapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drawerapplication.R;
import com.example.drawerapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    TextView dataAct;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dataAct = binding.dataAct;

        dataAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Thank you for using this application. Your privacy " +
                        "is important to us. As such, we provide this privacy policy (‘Privacy Policy’) " +
                        "explaining our online information practices and the choices you can make about the way your information is " +
                        "collected and used by this app. ‘Users’ are persons that use the Services.\n" +
                        "By using the app and submitting personal information through the app, the user expressly " +
                        "consents to the collection, use, and disclosure of the user’s personal information in accordance " +
                        "with this privacy policy.", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}