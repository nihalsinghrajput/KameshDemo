package com.example.kamesh;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Kamesh {

    public static void show(Context c){

        Intent intent = new Intent(c,Payment.class);
        c.startActivity(intent);

    }

}
