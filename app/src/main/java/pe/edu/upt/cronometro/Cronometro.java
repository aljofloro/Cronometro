package pe.edu.upt.cronometro;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Cronometro extends ActionBarActivity {

    Button b1,b2;
    TextView tiempo;
    int h=0,m=0,s=0;
    boolean bandera = true;
    Handler handler = new Handler();

    Runnable proceso = new Runnable() {
        @Override
        public void run() {
            try{
                tiempo.setText(" "+h+" : "+m+" : "+s);
                s++;
                if(s==60){m++;s=0;}
                if(m==60){h++;m=0;}
            }
            catch(Exception e){
                Toast b = Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                b.show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //Aplicacion
        b1 = (Button)findViewById(R.id.btn1);
        b2 = (Button)findViewById(R.id.btn2);
        tiempo = (TextView)findViewById(R.id.crono);


        b1.setOnClickListener(Empezar);
        b2.setOnClickListener(Parar);


    }

    View.OnClickListener Empezar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bandera = true;
            hilador();
            b1.setOnClickListener(Pausa);
            b1.setText("Pausa");
        }
    };

    View.OnClickListener Pausa = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            bandera = false;
            tiempo.setText("-->"+h+" : "+m+" : "+s);
            b1.setOnClickListener(Continuar);
            b1.setText("Continuar");
        }
    };

    View.OnClickListener Continuar = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            bandera = true;
            hilador();
            tiempo.setText("-->"+h+" : "+m+" : "+s);
            b1.setOnClickListener(Pausa);
            b1.setText("Pausa");
        }
    };

    View.OnClickListener Parar = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            bandera = false;
            tiempo.setText("Tiempo");
            m=0;s=m;h=m;
            b1.setOnClickListener(Empezar);
            b1.setText("Empezar");
        }
    };


    public void hilador(){
        Thread cronometro = new Thread(){
            public void run(){
                try{
                    while(bandera==true){
                        handler.post(proceso);
                        Thread.sleep(1000);
                    }
                }
                catch(Exception e){
                    Toast b = Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                    b.show();
                }
            }
        };
        cronometro.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cronometro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
