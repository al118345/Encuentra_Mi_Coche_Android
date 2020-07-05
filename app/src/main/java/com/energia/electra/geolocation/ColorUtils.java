package com.energia.electra.geolocation;

import android.content.Context;
import android.os.Build;

/**
 * Utils fot the colors
 */
public class ColorUtils
{
    public static int getColor (Context context,int id){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? context.getColor(id) : context.getResources().getColor(id);
    }
}
