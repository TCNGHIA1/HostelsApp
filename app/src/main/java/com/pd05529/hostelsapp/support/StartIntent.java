package com.pd05529.hostelsapp.support;

import android.content.Context;
import android.content.Intent;

public class StartIntent {
    public static void startIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
