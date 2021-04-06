package com.example.goingonce.Diags;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlaceBidDialog extends Dialog {



    public PlaceBidDialog(@NonNull Context context) {
        super(context);
    }

    public PlaceBidDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PlaceBidDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
