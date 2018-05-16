package mobimech.co.mpesab2c;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.mobimech.mpesa.API.Models.AccessToken;
import ke.co.mobimech.mpesa.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesa.API.Models.B2CPaymentResponse;
import ke.co.mobimech.mpesa.API.Models.C2BPaymentRequest;
import ke.co.mobimech.mpesa.API.Models.C2BPaymentResponse;
import ke.co.mobimech.mpesa.Mpesa;
import ke.co.mobimech.mpesa.MpesaLib;

import static ke.co.mobimech.mpesa.Utils.Enumerations.SANDBOX;

public class MainActivity extends AppCompatActivity {

    Mpesa mpesa;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * Use your Daraja app credentials to initiate the mpesa module.
         * Make sure you specify whether you are on sandbox or production.
         */

        mpesa=Mpesa.with("oTyqtS9FNz2pSGRagaam5Kw2PkInboUF", "Bkf6Aok2zYx6H92i",SANDBOX, new MpesaLib<AccessToken>() {

            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Toast.makeText(MainActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
                Log.wtf(MainActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());

            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error,Toast.LENGTH_SHORT).show();
                Log.wtf("AUTHENTICATION ERROR: ",error);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @OnClick(R.id.pay)
    public void makePayment(){
        Log.wtf("Button","Click pay");
        C2BPaymentRequest request = new C2BPaymentRequest(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                "100",
                "254708374149",
                "174379",
                "254713197277",
                "http://mycallbackurl.com/checkout.php",
                "001ABC",
                "Goods Payment"
        );

        mpesa.C2BStkPushPayment(request, new MpesaLib<C2BPaymentResponse>() {
            @Override
            public void onResult(@NonNull C2BPaymentResponse c2BPaymentResponse) {
                Log.wtf("Button","success");
            }

            @Override
            public void onError(String error) {
                Log.wtf("Button","Fail: "+error);
            }
        }

        );

    }
}
