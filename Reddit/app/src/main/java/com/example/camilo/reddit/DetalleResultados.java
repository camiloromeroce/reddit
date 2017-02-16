package com.example.camilo.reddit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleResultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_resultados);

        setupGUI();
        initLogic();
    }

    private void initLogic() {
        Bundle data = getIntent().getExtras();
        if(data != null){
            descripcionResultados = data.getString("keyDetallada");
            tvDescripcionResultados.setText(descripcionResultados);
        }
    }

    private void setupGUI() {
        tvDescripcionResultados  = (TextView) findViewById(R.id.tvDescripcionResultados);
    }

    private TextView tvDescripcionResultados;
    private String descripcionResultados;
}
