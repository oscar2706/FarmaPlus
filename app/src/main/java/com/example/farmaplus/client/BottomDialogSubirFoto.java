package com.example.farmaplus.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;

import com.example.farmaplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;

public class BottomDialogSubirFoto extends BottomSheetDialogFragment {
    Uri uri_imagen;

    public BottomDialogSubirFoto() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_subir_foto, container, false);
        Button btn_camara = view.findViewById(R.id.button2);
        Button btn_galeria = view.findViewById(R.id.button3);

        btn_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                File foto = new File(getActivity().getExternalFilesDir(null), "test.jpg");

                uri_imagen = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".provider", foto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_imagen);
                startActivityForResult(intent, PrincipalCliente.CODE_CAMERA);
            }
        });

        btn_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PrincipalCliente.CODE_GALLERY);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PrincipalCliente.CODE_GALLERY:
                if(data != null){
                    uri_imagen = data.getData();
                    String foto = String.valueOf(uri_imagen);
                 //   Toast.makeText(getContext(), foto, Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("receta", foto);
                    bundle.putInt("codigo", 0);
                    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                            R.id.action_dialogSubirFoto_to_nuevoPedido, bundle);
                 /*   try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri_imagen);
                        img.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }*/
                }
                break;

            case PrincipalCliente.CODE_CAMERA:
                Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getExternalFilesDir(null)+"/test.jpg");
               // img.setImageBitmap(bitmap);
                String foto = getActivity().getExternalFilesDir(null)+"/test.jpg";
                //   Toast.makeText(getContext(), foto, Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putInt("codigo", 1);
                bundle.putString("receta", foto);
                bundle.putString("uri", String.valueOf(uri_imagen));
                Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                        R.id.action_dialogSubirFoto_to_nuevoPedido, bundle);
                break;
        }
    }
}