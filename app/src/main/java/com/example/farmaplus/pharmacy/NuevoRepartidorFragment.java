package com.example.farmaplus.pharmacy;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.farmaplus.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NuevoRepartidorFragment extends Fragment implements View.OnClickListener {
    NavController navController;
    ImageView img;
    TextInputEditText txt_nombre, txt_user, txt_password;

    DatabaseReference reference;
    Uri uri_imagen;

    ProgressDialog progressDialog;

    public NuevoRepartidorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmacia_nuevo_repartidor, container, false);

        Button buttonCambiarFoto = view.findViewById(R.id.buttonCambiarFoto);
        buttonCambiarFoto.setOnClickListener(this);
        Button buttonRegistrar = view.findViewById(R.id.buttonRegistrarRepartidor);
        buttonRegistrar.setOnClickListener(this);

        img=  view.findViewById(R.id.imageView16);
        txt_nombre= view.findViewById(R.id.nombreC);
        txt_password=  view.findViewById(R.id.contraRep);
        txt_user=  view.findViewById(R.id.userRep);

        try {
            switch(getArguments().getInt("codigo"))
            {
                case 0 :
                    Uri fot = Uri.parse(getArguments().getString("rep"));
                    uri_imagen = fot;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri_imagen);
                    img.setImageBitmap(bitmap);
                    break;
                case 1:
                    String tv = getArguments().getString("rep");
                    uri_imagen = Uri.parse(getArguments().getString("uri"));
                    Bitmap bitmap2 = BitmapFactory.decodeFile(tv);
                    img.setImageBitmap(bitmap2);
                    break;
            }
            //  Toast.makeText(getContext(), tv, Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {

        }

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("REPARTIDOR");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegistrarRepartidor:
                Bundle bundle = new Bundle();
                bundle.putString("nombreC", txt_nombre.getText().toString());
                bundle.putString("usuario", txt_user.getText().toString());
                bundle.putString("contra", txt_password.getText().toString());
                bundle.putString("url", String.valueOf(uri_imagen));
                navController.navigate(R.id.action_nuevoRepartidor_to_bottomDialogConfirmaNuevoRepartidor, bundle);
                break;
            case R.id.buttonCambiarFoto:
                navController.navigate(R.id.action_nuevoRepartidor_to_bottomDialogSubirFotoRepartidor);
                break;
        }
    }
}