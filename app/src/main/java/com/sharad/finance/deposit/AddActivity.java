package com.sharad.finance.deposit;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddActivity extends ActionBarActivity {
	private DBAdapter _db;
    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initToolbar();
        initWidgets();
    }

    private void initWidgets() {
        pair(R.id.ic_title, R.id.et_title);
        pair(R.id.ic_bank, R.id.et_bank);
        pair(R.id.ic_account, R.id.et_account);
        pair(R.id.ic_principle, R.id.et_principle);
        pair(R.id.ic_rate, R.id.et_rate);
        pair(R.id.ic_start_date, R.id.et_start_date);
        pair(R.id.ic_tenure, R.id.et_tenure);

        EditText field = (EditText) findViewById(R.id.et_start_date);
        final SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy");
        Calendar cal = Calendar.getInstance();
        field.setText(df.format(cal.getTime()));
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        EditText field = (EditText) findViewById(R.id.et_start_date);
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day);
                        field.setText(df.format(cal.getTime()));
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });
    }

    private void pair(int id_icon, int id_field) {
        final ImageView icon = (ImageView) findViewById(id_icon);
        int color = getResources().getColor(R.color.secondary_text);
        icon.setColorFilter(color);
        EditText field = (EditText) findViewById(id_field);
        field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int coloId = hasFocus ? R.color.primary_dark : R.color.secondary_text;
                int color = getResources().getColor(coloId);
                icon.setColorFilter(color);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NEW DEPOSIT");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

	@Override
    public void onResume() {
        super.onResume();
        _db = new DBAdapter(this);
        _db.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        _db.close();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                boolean isBack = saveItem();
                if(isBack) {
                    onBackPressed();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean saveItem() {
        String title = getItemTitle();
        if(title.length() == 0) return false;

        String bank = getItemBank();
        if(bank.length() == 0) return false;

        String accNum = getItemAccNum();
        if(accNum.length() == 0) return false;

        float principle = getItemPrinciple();
        if(principle <= 0) return false;

        float rate = getItemRate();
        if(rate <= 0) return false;

        int tenure = getItemTenure();
        if(tenure <= 0) return false;

		Calendar end = Calendar.getInstance();
        end.set(mYear, mMonth, mDay);
        Date start = end.getTime();
		end.add(Calendar.DATE, tenure);
		
		int type = 0;
		
		int status = 0;
		long diff = System.currentTimeMillis() - start.getTime();
		if(diff < 0) {
			status = Deposit.STATUS_INVALID;
		} else {
			diff = end.getTimeInMillis() - System.currentTimeMillis();
			if(diff < 0) {
				status = Deposit.STATUS_CLOSED;
			} else {
				status = Deposit.STATUS_ACTIVE;
			}
		}
		
		float accInt = 0;
		float actInt = 0;
		float tds = 0;
		
		Deposit newDeposit = new Deposit(0, title, bank, accNum, "note", start, end.getTime(),
						tenure, type, status, principle, rate, accInt, actInt, tds);
		newDeposit.set_id(_db.insertDeposit(newDeposit));
		
        return true;
    }

    private void showError(EditText field, String msg) {
        field.setError(msg);
        field.requestFocus();
    }

    private String getItemTitle() {
        EditText field = (EditText)findViewById(R.id.et_title);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Title");
            return "";
        }
        return field.getText().toString();
    }

    public String getItemBank() {
        EditText field = (EditText)findViewById(R.id.et_bank);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Bank Name");
            return "";
        }
        return field.getText().toString();
    }

    public String getItemAccNum() {
        EditText field = (EditText)findViewById(R.id.et_account);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Account Number");
            return "";
        }
        return field.getText().toString();
    }

    public float getItemPrinciple() {
        EditText field = (EditText)findViewById(R.id.et_principle);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Principle");
            return 0;
        }
        float value = Float.valueOf(field.getText().toString());
        if(value <= 0){
            showError(field, "Invalid Principle");
            return 0;
        }
        return value;
    }

    public float getItemRate() {
        EditText field = (EditText)findViewById(R.id.et_rate);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Rate");
            return 0;
        }
        float value = Float.valueOf(field.getText().toString());
        if(value <= 0){
            showError(field, "Invalid Rate");
            return 0;
        }
        return value;
    }

    public int getItemTenure() {
        EditText field = (EditText)findViewById(R.id.et_tenure);
        field.setError(null);
        if(field.getText().toString().trim().length() == 0) {
            showError(field, "Enter Tenure");
            return 0;
        }
        int value = Integer.valueOf(field.getText().toString());
        if(value <= 0){
            showError(field, "Invalid Principle");
            return 0;
        }
        return value;
    }
}
