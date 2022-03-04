package com.example.kamesh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Kamesh {

    public static void show(Context context){

        String params =
                "{\"name\":\"Raj\",\n" +
                        "            \"email\":\"raj@mailiinator.com\",\n" +
                        "            \"amount\":10.00,\n" +
                        "            \"currency\":\"SAR\",\n" +
                        "            \"order_id\":420,\n" +
                        "            \"card_number\":\"5105105105105100\",\n" +
                        "            \"exp_month\":\"12\",\n" +
                        "            \"exp_year\":\"23\",\n" +
                        "            \"cvv\":\"999\",\n" +
                        "            \"remark\":\"This payment is done by card ios\"\n" +
                        "    }";


        Intent intent = new Intent(context,Payment.class);
        intent.putExtra("payment_details",params);
        ((Activity)context).startActivityForResult(intent,1);

    }

}
