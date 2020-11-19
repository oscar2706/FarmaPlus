package com.example.farmaplus.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.farmaplus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class NuevoPedido extends Fragment {
    ImageView img;
    EditText txtCom;
    RadioGroup radioGroup_tipoEnvio;
    RadioButton radDomicilio, radSucursal;
    Button btn_camara, enviar, btn_galeria, btn_subirFoto;

    DatabaseReference reference;
    long maxId = 0;

    Uri uri_imagen;

    ProgressDialog progressDialog;

    public NuevoPedido() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevo_pedido, container, false);

        img = view.findViewById(R.id.imageView5);
        txtCom = view.findViewById(R.id.editTextTextMultiLine);
        radSucursal = view.findViewById(R.id.radioButton_sucursal);
        radDomicilio = view.findViewById(R.id.radioButton_domicilio);


        //btn_camara = view.findViewById(R.id.button_login);
        //btn_galeria = view.findViewById(R.id.button_galeria);
        enviar = view.findViewById(R.id.button_enviar);
        btn_subirFoto = view.findViewById(R.id.button_subirReceta);

        try {
            switch(getArguments().getInt("codigo"))
            {
                case 0 :
                    Uri fot = Uri.parse(getArguments().getString("receta"));
                    uri_imagen = fot;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri_imagen);
                    img.setImageBitmap(bitmap);
                    break;
                case 1:
                    String tv = getArguments().getString("receta");
                    uri_imagen = Uri.parse(getArguments().getString("uri"));
                    Bitmap bitmap2 = BitmapFactory.decodeFile(tv);
                    img.setImageBitmap(bitmap2);
                    break;
            }
          //  Toast.makeText(getContext(), tv, Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {

        }

        btn_subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_subirReceta:
                        Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                                R.id.action_nuevoPedido_to_dialogSubirFoto);
                        break;
                }
            }
        });
        enviar = view.findViewById(R.id.button_enviar);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("PEDIDO");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxId = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*btn_camara.setOnClickListener(new View.OnClickListener() {
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
        });*/

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                String fecha = String.valueOf(mDay)+"/"+String.valueOf(mMonth+1)+"/"+String.valueOf(mYear);

                Bundle bundle = new Bundle();
                bundle.putString("com", txtCom.getText().toString());
                bundle.putString("enCurso", "true");
                bundle.putString("estadoPedido", "Enviado");
                bundle.putString("fechaPedido", fecha);
                bundle.putString("repartidor", "Sin Asignar");
                bundle.putString("url", String.valueOf(uri_imagen));
                bundle.putString("user", PrincipalCliente.idUser);

                if(radDomicilio.isChecked())
                {
                    bundle.putString("tipoEntrega", "Domicilio");
                    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                            R.id.action_nuevoPedido_to_dialogSeleccionaDireccion, bundle);
                }
                else{
                    bundle.putString("tipoEntrega", "En Sucursal");
                    Navigation.findNavController(getActivity(), R.id.fragment_navigation).navigate(
                            R.id.action_nuevoPedido_to_dialogSleccionaSucursal, bundle);
                }
            }
        });

      /*  enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("REALIZANDO PEDIDO");
                progressDialog.show();
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                String fecha = String.valueOf(mDay)+"/"+String.valueOf(mMonth+1)+"/"+String.valueOf(mYear);
              //  System.out.println("Fecha: "+fecha);
                final Pedido pedido = new Pedido();

                pedido.setComentarios(txtCom.getText().toString());
                pedido.setEstadoPedido("Enviado");
                pedido.setUser(PrincipalCliente.idUser);
                pedido.setEnCurso("true");
                pedido.setRepartidor("Sin Asignar");
                pedido.setFechaPedido(fecha);

                if(radDomicilio.isChecked())
                {
                    pedido.setTipoEntrega("Domicilio");
                }
                else{
                    pedido.setTipoEntrega("En Sucursal");
                }

                //GUARDAR IMAGEN FB
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference folderRef = storageReference.child("RECETAS_IMG");
                StorageReference fotoRef = folderRef.child(new Date().toString());

                fotoRef.putFile(uri_imagen).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        PrincipalCliente.enCurso = true;
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        pedido.setUrl(downloadUri.toString());

                        //INSERT OBJETO PEDIDO FIREBASE
                        reference.child(String.valueOf(maxId+1)).setValue(pedido);

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Pedido Recibido Por Farmacia", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }); */

        return view;
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PrincipalCliente.CODE_GALLERY:
                if(data != null){
                    uri_imagen = data.getData();
                    try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri_imagen);
                        img.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;

            case PrincipalCliente.CODE_CAMERA:
                Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getExternalFilesDir(null)+"/test.jpg");
                img.setImageBitmap(bitmap);
                break;
        }
    } */
}