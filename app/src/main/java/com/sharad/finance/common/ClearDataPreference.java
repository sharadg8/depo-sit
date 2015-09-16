package com.sharad.finance.common;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.sharad.finance.deposit.DBAdapter;

/**
 * Created by Sharad on 16-Sep-15.
 */
public class ClearDataPreference extends DialogPreference {
    public ClearDataPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPositiveButtonText("Ok");
        setNegativeButtonText("Cancel");

        setDialogIcon(null);
        setDialogTitle(null);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        if (positiveResult) {
            // User selected OK
            DBAdapter db = new DBAdapter(getContext());
            db.open();
            db.deleteAllDeposits();
            db.close();
        } else {
            // User selected Cancel
        }
    }
}
