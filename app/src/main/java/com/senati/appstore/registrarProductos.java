package com.senati.appstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class registrarProductos extends AppCompatActivity {

   TextInputLayout textInputLayout;
   AutoCompleteTextView autoCompleteTextView;
   TextInputEditText etcategoria, etdescripcion, etprecio, etgarantia, etfotografia;
   Button btGuardarProducto;

   //  Almacear en una constante la URL al WS
   //  http = protocolo de transferencia de hipertexto
   //final String URL = "http://192.168.1.100/appstore/controllers/producto.controller.php";
   final String URL = "http://192.168.1.43/appstore/controllers/producto.controller.php";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_registrar_productos);

      loadUI();

      btGuardarProducto.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            validarCampo();
         }
      });

      //  Crear un array de nombres/items para el adaptador del autocompleteview
      String[] nombres = new String[]{
          "Freddy",
          "Juan",
          "Amanda",
          "William",
          "Otros"
      };

      //  Crear arrayadpater y configurarlo
      ArrayAdapter<String> adapter = new ArrayAdapter<>(
          registrarProductos.this,
          R.layout.drowpdown_item,
          nombres
      );
      //  Setear el adaptador al autocompletetextview
      autoCompleteTextView.setAdapter(adapter);



   }

   private void validarCampo() {

      String categoria, descripcion, garantia;
      int precio;
/*
        int categoria =  Integer.perseInt(etCategoria.getText().toString());
        String descripcion = etDescripcion.getText().toString();
        double precio = Double.parseInt(etPrecio.getText().toString());
        int garantia = etGarantia.getText().toString.isEmpty ? 0 : Integer.perseInt(etGarantia.getText().toString());
        String fotografia = etFotografia.getText().toString()

        if(){
            notificar("Completar el formulario correctamente por favor");
            etCategoria.requestFocus();
        }else{
            preguntarRegistraProducto();
        }

        */

      categoria = etcategoria.getText().toString();
      descripcion = etdescripcion.getText().toString();
      garantia = etgarantia.getText().toString();


      precio = (etprecio.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etprecio.getText().toString());

      if (categoria.isEmpty() || descripcion.isEmpty() || garantia.isEmpty() || precio == 0) {
         notificar("Complete el Formulario 🤨");
         etcategoria.requestFocus();
      } else {
         preguntar();
      }
   }

   private void preguntar() {

      AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
      dialogo.setTitle("AppStore");
      dialogo.setMessage("¿Está seguro de registrar el producto?");
      dialogo.setCancelable(false);

      dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
            enviarWS();
         }
      });

      dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {

         }
      });

      dialogo.show();

      enviarWS();
   }

   private void enviarWS() {
      //notificar("envio WS");
      //  Paso 1: Un tipo de objeto para la comunicación
      //  onResponse = la comunicación se logra concretar
      //  Response.ErrorListener = error comunicación
      StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
            notificar("Mensaje obtenido:" + response);
            limpiarCampos();
            etcategoria.requestFocus();
         }
      }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            notificar("No hay comunicación con el servidor");
         }
      }) {
         @Nullable
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
            //  Enviar los datos
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("operacion", "registrar");
            parametros.put("idcategoria", etcategoria.getText().toString());
            parametros.put("descripcion", etdescripcion.getText().toString());
            parametros.put("precio", etprecio.getText().toString());
            parametros.put("garantia", etgarantia.getText().toString());
            parametros.put("fotografia", etfotografia.getText().toString());
            return parametros;
         }
      };
      //  Enviar la solicitud al WS
      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(request);


   }

   /* private void enviarWS() {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // En esta parte, puedes verificar si la respuesta del servidor indica éxito
                // Si es así, entonces los datos se han enviado correctamente
                if (response.equals("success")) {
                    notificar("Datos enviados con éxito.");
                    limpiarCampos(); // Limpia los campos después del éxito
                } else {
                    notificar("Error en el servidor.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificar("No hay comunicación con el servidor");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("operacion", "registrar");
                parametros.put("idcategoria", etcategoria.getText().toString());
                parametros.put("descripcion", etdescripcion.getText().toString());
                parametros.put("precio", etprecio.getText().toString());
                parametros.put("garantia", etgarantia.getText().toString());
                parametros.put("fotografia", etfotografia.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
*/

   private void limpiarCampos() {
      etcategoria.setText(null);
      etdescripcion.setText(null);
      etprecio.setText(null);
      etgarantia.setText(null);
      etfotografia.setText(null);
   }

   private void notificar(String ms) {
      Toast.makeText(getApplicationContext(), ms, Toast.LENGTH_SHORT).show();
   }

   private void loadUI() {

      etcategoria = findViewById(R.id.etCategoria);
      etdescripcion = findViewById(R.id.etDescripcion);
      etprecio = findViewById(R.id.etPrecio);
      etgarantia = findViewById(R.id.etGarantia);
      etfotografia = findViewById(R.id.etFotografia);

      btGuardarProducto = findViewById(R.id.btGuardarProducto);

      textInputLayout = findViewById(R.id.text_input_layout);
      autoCompleteTextView = findViewById(R.id.autoCompletetexview);
   }


}
