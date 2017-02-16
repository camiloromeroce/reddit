package com.example.camilo.reddit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.camilo.reddit.utilities.AdapterJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Resultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        setupGUI();
        initLogic();
    }

    private void initLogic() {
        SharedPreferences sharedPref = getSharedPreferences("appData",(Context.MODE_PRIVATE));
        jsonData = sharedPref.getString(getString(R.string.json_file), "");
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
            final JSONArray jsonArray = jsonObjectData.getJSONArray("children");
            final JSONObject[] jsonObjectChil = {null};
//            List<Map<String, String>> productsList = new ArrayList<Map<String,String>>();
//
//            int sizeJson =jsonArray.length();
//            List<String> listListView = new ArrayList<>(sizeJson);
//            for (int i=0; i< sizeJson ; i++){
//                JSONObject childJSONObject = jsonArray.getJSONObject(i);
//                jsonObjectChil = childJSONObject.getJSONObject("data");
//                String name = jsonObjectChil.getString("display_name");
//                listListView.add(name);
//            }

            adapterJSON = new AdapterJSON(Resultados.this, jsonArray);
            lstResultados.setAdapter(adapterJSON);
            lstResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        JSONObject childJSONObject = jsonArray.getJSONObject(position);
                        jsonObjectChil[0] = childJSONObject.getJSONObject("data");
                        String detallada = jsonObjectChil[0].getString("description");

                        if(detallada !=null){
                            Intent i = new Intent(Resultados.this, DetalleResultados.class);
                            i.putExtra("keyDetallada", detallada);
                            startActivity(i);
                        }else{
                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Lo Sentimos")
                                    .setMessage("Esta sección no posee una descripción detallada")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupGUI() {
        lstResultados = (ListView) findViewById(R.id.lstResultados);
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, getString(R.string.salir_app), Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    private int backButtonCount =0;
    private ListView lstResultados;
    private String jsonData;
    AdapterJSON adapterJSON;
}
