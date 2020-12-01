package com.example.farmaplus.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.farmaplus.LogginHandler;
import com.example.farmaplus.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalCliente extends Fragment implements View.OnClickListener {
    Button button_NuevoPedido;
    NavController navController; //1 NavController para poder navegar

    Fragment np;

    //CONSTANTES DE ACCION
    public static final int CODE_CAMERA = 21;
    public static final int CODE_GALLERY = 22;

    public static String idUser;
    public static boolean enCurso = false;

    public PrincipalCliente() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        np = new NuevoPedido();

        validaUser();
        revisarPedidoEnCurso();

     //   insertaSucursales();
    }

    private void insertaSucursales() {
        Sucursal sucursal = new Sucursal();
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("SUCURSAL");

        sucursal.setDireccionSuc("Avenida Filomeno Escamilla, 72320 Puebla de Zaragoza, PUE, México");
        sucursal.setLatitud("19.057056642500726");
        sucursal.setLongitud("-98.1444130887897");
        sucursal.setNombreSuc("Sucursal Amalucan");
        sucursal.setEstado("Puebla");
        reference.push().setValue(sucursal);


        sucursal.setDireccionSuc("Parque Puebla, Circuito Monte Líbano, 72220 Puebla de Zaragoza, PUE, México");
        sucursal.setLatitud("19.0694212");
        sucursal.setLongitud("-98.1725397");
        sucursal.setNombreSuc("Sucursal Parque Puebla");
        sucursal.setEstado("Puebla");
        reference.push().setValue(sucursal);

        sucursal.setDireccionSuc("Fuente de San Miguel, Avenida 3 Oriente, Centro Histórico de Puebla, 72000 Puebla de Zaragoza, PUE, México");
        sucursal.setLatitud("19.04321088858059");
        sucursal.setLongitud("-98.1978148546105");
        sucursal.setNombreSuc("Sucursal Zocalo");
        sucursal.setEstado("Puebla");
        reference.push().setValue(sucursal);

        sucursal.setDireccionSuc("Calzada Ignacio Zaragoza, Centro Histórico de Puebla, 72290 Puebla de Zaragoza, PUE, México");
        sucursal.setLatitud("19.0601700871197");
        sucursal.setLongitud("-98.18609852547395");
        sucursal.setNombreSuc("Sucursal Loreto");
        sucursal.setEstado("Puebla");
        reference.push().setValue(sucursal);

        sucursal.setDireccionSuc("Paseo San Francisco, De los Pescaditos, Centro Histórico de Puebla, 72290 Puebla de Zaragoza, PUE, México");
        sucursal.setLatitud("19.043199645106554");
        sucursal.setLongitud("-98.19090101037271");
        sucursal.setNombreSuc("Sucursal San Fransisco");
        sucursal.setEstado("Puebla");
        reference.push().setValue(sucursal);
    }


    private void validaUser() {
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        String correo = sp.getString("idUser", "na");
        idUser = correo;
        System.out.println("id User: "+idUser);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal_cliente, container, false);
        button_NuevoPedido = view.findViewById(R.id.button_NuevoPedido);
        button_NuevoPedido.setOnClickListener(this);
        view.findViewById(R.id.cardView_Historial).setOnClickListener(this);
        view.findViewById(R.id.cardView_Medicamentos).setOnClickListener(this);
        view.findViewById(R.id.cardView_PedidoActual).setOnClickListener(this);
        view.findViewById(R.id.cardView_Sucursales).setOnClickListener(this);

        return view;
    }

    private void revisarPedidoEnCurso() {
        final FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PEDIDO");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Pedido pedido = snapshot.getValue(Pedido.class);

                pedido.setId(snapshot.getKey());

                if(pedido.getUser().equals(PrincipalCliente.idUser)){
                    System.out.println("Pedidos: "+pedido.getEnCurso());
                    if(pedido.getEnCurso().equals("true")){
                         enCurso = true;
                       /* System.out.println("Vista");
                         System.out.println(getView()); */
                         if(getView() != null) {
                             navController.navigate(R.id.action_principalCliente_to_pedidoActual);
                           //  System.out.println("Esoty en la vista");
                         }
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_NuevoPedido:
                //3 Método para navegar a otro fragmento (el action se puede ver en navigation.xml)
                if(enCurso == true){
                    Toast.makeText(getActivity(), "Tienes un pedido en curso", Toast.LENGTH_SHORT).show();
                }else {
                    navController.navigate(R.id.action_principalCliente_to_nuevoPedido);
                }
                break;
            case R.id.cardView_PedidoActual:
                navController.navigate(R.id.action_principalCliente_to_pedidoActual);
                break;
            case R.id.cardView_Historial:
                navController.navigate(R.id.action_principalCliente_to_historialPedidos);
                break;
            case R.id.cardView_Medicamentos:
                navController.navigate(R.id.action_principalCliente_to_medicamentos);
                break;
            case R.id.cardView_Sucursales:
                navController.navigate(R.id.action_principalCliente_fragment_to_mapsFragment);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cliente, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_direcciones:
                navController.navigate(R.id.action_principalCliente_to_direcciones);
                break;
            case R.id.menu_informacion:
                navController.navigate(R.id.action_principalCliente_fragment_to_aboutUs);
                break;
            case R.id.menu_logout:
                enCurso = false;
                DireccionService.vaciarLista();
                navController.navigate(R.id.login_fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void redirectIfNotLoggedIn() {
        if(!LogginHandler.isLoggedIn(getActivity())){
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_navigation);
            navController.navigate(R.id.login_fragment) ;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CODE_GALLERY:
                np.onActivityResult(requestCode, resultCode, data);
                break;

            case CODE_CAMERA:
                np.onActivityResult(requestCode, resultCode, data);
                break;

        }
    }
}