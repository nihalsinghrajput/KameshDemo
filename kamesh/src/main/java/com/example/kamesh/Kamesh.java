package com.example.kamesh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Kamesh {

    public static void show(Context context){


        Intent intent = new Intent(context,Payment.class);
        ((Activity)context).startActivityForResult(intent,500);

    }

}
