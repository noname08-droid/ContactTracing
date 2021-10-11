package com.example.drawerapplication.ui.generator;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.drawerapplication.databinding.FragmentGeneratorBinding;
import com.example.drawerapplication.ui.information.CustomAdapter;
import com.example.drawerapplication.ui.information.DatabaseHelper;
import com.example.drawerapplication.ui.information.ListDataActivity;
import com.example.drawerapplication.ui.information.showInfoActivity;
import com.google.zxing.Result;

public class GeneratorFragment extends Fragment {


    ListDataActivity listDataActivity = new ListDataActivity();
    public TextView tv_View;
    private FragmentGeneratorBinding binding;
    private CodeScanner mCode_Scanner;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGeneratorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        CodeScannerView codeScanner = binding.scannerView;
        tv_View = binding.tvView;

        mCode_Scanner = new CodeScanner(getContext(), codeScanner);
        mCode_Scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_View.setText(result.getText());

                        Intent intent = new Intent(getContext(), showInfoActivity.class);
                        intent.putExtra("ayDi", tv_View.getText());
                        startActivity(intent);

                    }
                });

            }
        });


        codeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCode_Scanner.startPreview();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCode_Scanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCode_Scanner.releaseResources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}