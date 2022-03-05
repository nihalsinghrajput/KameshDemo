package com.example.kamesh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Kamesh {

    public static void show(Context context, String username, String password, String key, String details){

        Intent intent = new Intent(context,Payment.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("key",key);
        intent.putExtra("details",details);
        ((Activity)context).startActivityForResult(intent,500);

    }

}
