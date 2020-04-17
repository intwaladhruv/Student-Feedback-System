package com.example.feedback_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Form extends AppCompatDialogFragment {

    private TextView fName, sCode, sName;
    private DiaglogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.form, null);

        builder.setView(view)
                .setTitle("Create form")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String facName = fName.getText().toString();
                        String subCode = sCode.getText().toString();
                        String subName = sName.getText().toString();
                        listener.applyTexts(facName, subCode, subName);
                    }
                });

        fName = view.findViewById(R.id.name);
        sCode = view.findViewById(R.id.subjectCode);
        sName = view.findViewById(R.id.subjectName);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DiaglogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DiaglogListener");
        }
    }

    public interface DiaglogListener {
        void applyTexts(String facName, String subCode, String subName);
    }
}
