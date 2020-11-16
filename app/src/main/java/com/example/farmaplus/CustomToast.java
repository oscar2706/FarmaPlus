package com.example.farmaplus;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmaplus.R;

import java.util.zip.Inflater;

public class CustomToast {

    public static void showOkToast(Activity activity, String mensaje){
        LayoutInflater inflater = activity.getLayoutInflater();
        View toast_layout = inflater.inflate(R.layout.toast_pedido_realizado,
                (ViewGroup)activity.findViewById(R.id.toast_ok));
        final Toast toast = new Toast(activity.getApplicationContext());
        TextView textView = toast_layout.findViewById(R.id.textView_toastOk);
        textView.setText(mensaje);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toast_layout);
        toast.show();
    }
}
