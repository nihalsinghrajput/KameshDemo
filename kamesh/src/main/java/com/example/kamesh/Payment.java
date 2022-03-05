package com.example.kamesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Payment extends AppCompatActivity {


    WebView webView;
    RelativeLayout layoutProgress;
    byte[] encryptedText = null;
    IvParameterSpec ivspec = null;
    SecretKeySpec skeySpec = null;
    Cipher cipher = null;
    byte[] text = null;
    String s = null;
    private static   String AES_IV = "AGNNMLDKYPKEZDNK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webView = (WebView) findViewById(R.id.webView);
        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);


        String uName = getIntent().getStringExtra("username");
        String  pass = getIntent().getStringExtra("password");
        String  key_s = getIntent().getStringExtra("key");
        String  details_josn = getIntent().getStringExtra("details");




        if (TextUtils.isEmpty(uName))
        {
            Toast.makeText(Payment.this, "Payment gateway Username Required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(Payment.this, "Payment gateway Password Required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(key_s))
        {
            Toast.makeText(Payment.this, "key missing!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(details_josn))
        {
            Toast.makeText(Payment.this, "data missing!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new ApiCall(uName,pass,key_s,details_josn).execute();
        }




    }



    class ApiCall extends AsyncTask
    {


        String username;
        String password;
        String key;
        int responseCode;
        String payment_url = "";
        String details;


        public ApiCall(String username,String password, String key, String detail) {
            this.username = username;
            this.password = password;
            this.key = key;
            this.details = detail;
        }






        @Override
        protected Object doInBackground(Object[] objects) {


            String name = "";
            String email = "";
            String amount = "";
            String currency = "";
            String id = "";
            String number = "";
            String month = "";
            String year = "";
            String cvv = "";
            String cardType = "";
            String description = "";
            char quotes ='"';


            JSONObject js;
            {
                try {

                    js = new JSONObject(details);

                    name = quotes + js.getString("name") +quotes;
                    email = quotes + js.getString("email") +quotes;
                    amount = quotes + js.getString("amount") +quotes;
                    currency = quotes + js.getString("currency") +quotes;
                    id = quotes + js.getString("order_id") +quotes;
                    number = quotes + js.getString("card_number") +quotes;

                    month = quotes + js.getString("exp_month") +quotes;
                    year = quotes + js.getString("exp_year") +quotes;
                    cvv = quotes + js.getString("cvv") +quotes;
                    cardType = quotes + "C" +quotes;
                    description = quotes + js.getString("remark") +quotes;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            String json = "{\n" +
                    "\"customer\":{\n" +
                    "\"name\":"+name+",\n"+
                    "\"email\":"+email +
                    "},\n" +
                    "\"order\":{\n" +
                    "\"amount\":"+amount+",\n"+
                    "\"currency\":"+currency +",\n"+
                    "\"id\":"+id +
                    "},\n" +
                    "\"sourceOfFunds\":{\n" +
                    "\"provided\":{\n" +
                    "\"card\":{\n" +
                    "\"number\":"+number +",\n"+
                    "\"expiry\":{\n" +
                    "\"month\":"+month +",\n"+
                    "\"year\":"+year +
                    "},\n" +
                    "\"cvv\":"+cvv +
                    "}\n" +
                    "},\n" +
                    "\"cardType\":"+cardType +
                    "},\n" +
                    "\"remark\":{\n" +
                    "\"description\":"+description +
                    "}\n" +
                    "}";


            String url = "https://psp.digitalworld.com.sa/api/v1/test/payments/pay";


            Log.e("data>>>>",json);




            String encryptedData = encryptAES(key,json);

            /*try {

                URL urlObj = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

                String userpass = username + ":" + password;
                String basicAuth = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
                }
                httpCon.setRequestProperty ("Authorization", basicAuth);

                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("PUT");
                String parameters = "trandata=" + encryptedData;



                OutputStreamWriter writer = new OutputStreamWriter(
                        httpCon.getOutputStream());
                writer.write(parameters);
                writer.flush();

                responseCode = httpCon.getResponseCode();
                String s = httpCon.getResponseMessage();


                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();

                try {
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    JSONObject apiResponseObject = jsonObject.getJSONObject("apiResponse");
                    payment_url = apiResponseObject.getString("verifyUrl");





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (responseCode==200)
            {
                layoutProgress.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                if (!TextUtils.isEmpty(payment_url))
                {
                    webView.loadUrl(payment_url);
                }



                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {



                        if (url.contains("payment_status=APPROVED")) {

                            Intent intent = new Intent();
                            intent.putExtra("payment_status", "Payment Success");
                            setResult(RESULT_OK, intent);
                            finish();
                            //Toast.makeText(OminiPayAndroid.this, "Payment Success!", Toast.LENGTH_SHORT).show();

                            return true;
                        }
                        else if (url.contains("payment_status=FAILED"))
                        {
                            Intent intent = new Intent();
                            intent.putExtra("payment_status", "Payment Faild");
                            setResult(RESULT_OK, intent);
                            finish();
                            //Toast.makeText(OminiPayAndroid.this, "Error!", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        view.loadUrl(url);
                        return true;
                    }
                    //Show loader on url load
                    @Override
                    public void onLoadResource(WebView view, String url) {
                        //progressDialog.dismiss();
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //progressDialog.dismiss();
                    }
                });


            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


    public String encryptAES(String key, String encryptString)  {
        try {
            ivspec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));
            skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
            text = encryptString.getBytes("UTF-8");
            encryptedText = cipher.doFinal(text);
            s = String.valueOf(encodeHexString(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

}