package mobimech.co.mpesab2c;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

    private ProgressDialog dialog;

    /**
     * Declare Mpesa
     */
    Mpesa mpesa;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edPhone)
    EditText editText;
    @BindView(R.id.edAmount)
    EditText edAmount;

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
         * Use your Daraja app credentials to initialize the mpesa module.
         * Make sure you specify whether you are on sandbox or production(Third parameter).
         * I don't recommend this kind of implementation. (This is just for testing purposes)
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

    @OnClick(R.id.stkPush)
    public void makeSTKPayment(){

        String phoneNumber = editText.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            editText.setError("Please Provide a Phone Number");
            return;
        }else {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait");
            dialog.setCancelable(false);
            dialog.show();
            C2BPaymentRequest request = new C2BPaymentRequest(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                    "100",
                    "254708374149",
                    "174379",
                    phoneNumber,
                    "http://mycallbackurl.com/checkout.php",
                    "001ABC",
                    "Goods Payment"
            );

            mpesa.C2BStkPushPayment(request, new MpesaLib<C2BPaymentResponse>() {
                        @Override
                        public void onResult(@NonNull C2BPaymentResponse c2BPaymentResponse) {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Success",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            dialog.dismiss();
                            Log.wtf("Button","Fail: "+error);
                            Toast.makeText(MainActivity.this, "Fail",Toast.LENGTH_SHORT).show();
                        }
                    }

            );

        }


    }

    @OnClick(R.id.b2csend)
    public void makeB2CPayment() {
        String amount = edAmount.getText().toString().trim();

        if (TextUtils.isEmpty(amount)) {
            editText.setError("Please Provide a Phone Number");
            return;
        } else {
            B2CPaymentRequest request = new B2CPaymentRequest(
                    amount,
                    "BusinessPayment",
                    "600251",
                    "254708374149",
                    "http://mycallbackurl.com/checkout.php",
                    "Good",
                    "testapi251",
                    "Safaricom251!",
                    "http://mycallbackurl.com/checkout.php",
                    "Good"
            );

            mpesa.B2CMpesaPayment(request, new MpesaLib<B2CPaymentResponse>() {
                @Override
                public void onResult(@NonNull B2CPaymentResponse b2CPaymentResponse) {
                    Log.wtf("Button", "success");
                }

                @Override
                public void onError(String error) {
                    Log.wtf("Button", "Fail: " + error);

                }
            });

        }

    }
}
