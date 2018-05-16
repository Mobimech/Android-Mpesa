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
import ke.co.mobimech.mpesab2c2.API.Models.AccessToken;
import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentRequest;
import ke.co.mobimech.mpesab2c2.API.Models.B2CPaymentResponse;
import ke.co.mobimech.mpesab2c2.Mpesa;
import ke.co.mobimech.mpesab2c2.MpesaLib;

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
         * Use your Daraja credentials to initiate the mpesa module.
         */
        mpesa=Mpesa.with("", "", new MpesaLib<AccessToken>() {
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
        B2CPaymentRequest request= new B2CPaymentRequest(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        mpesa.B2CMpesaPayment(request, new MpesaLib<B2CPaymentResponse>() {
            @Override
            public void onResult(@NonNull B2CPaymentResponse b2CPaymentResponse) {
                Log.wtf(MainActivity.this.getClass().getSimpleName(), b2CPaymentResponse.ResponseDescription);

            }

            @Override
            public void onError(String error) {
                Log.wtf(MainActivity.this.getClass().getSimpleName(), error);

            }
        });

    }
}
