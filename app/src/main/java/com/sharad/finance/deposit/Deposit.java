package com.sharad.finance.deposit;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sharad on 28-Aug-15.
 */
public class Deposit {
    private long   _id;
	private Date   _startDate, _endDate;
    private String _title, _accNum, _bank, _note;
	private int    _tenure, _status, _type, _daysRemain, _progress;
    private float  _principle, _rate, _accInterest, _actInterest, _tds, _intPerDay;
    
	public static final int STATUS_INVALID = 0;
	public static final int STATUS_ACTIVE  = 1;
	public static final int STATUS_CLOSED  = 2;
	public static final int STATUS_CANCEL  = 3;
	
    Deposit(long id, String title, String bank, String accNum, String note,
			Date stDate, Date endDate, int tenure, int type, int status,
            float principle, float rate, float accInt, float actInt, float tds){
        _id = id;
		_title = title; 
		_accNum = accNum; 
		_bank = bank;
		_note = note;
		_startDate = stDate;
		_endDate = endDate;
		_tenure = tenure;
		_type = type;
		_status = status;
        _principle = principle;
		_rate = rate;
		_actInterest = actInt;
		_accInterest = accInt;
		_tds = tds;
        
		if(_status == STATUS_ACTIVE) {
			long diff = System.currentTimeMillis() - _startDate.getTime();
            float daySince = (float) diff / (24 * 60 * 60 * 1000);
            _daysRemain = (int)((tenure - daySince) > 0 ? (tenure - daySince) : 0);
			_intPerDay = _accInterest / daySince;
			//double roi = (double)_rate / 36500;
            //_accInterest = (int)(_principle * roi * daySince);
		} else {
			_daysRemain = 0;
			long diff = _endDate.getTime() - _startDate.getTime();
            float days = (float) diff / (24 * 60 * 60 * 1000);
			_intPerDay = _actInterest / days;
		}

        _progress = (100 * _daysRemain) / _tenure;
    }

    Deposit(String title){
        _title = title;
        _principle = 150000;
        _rate = 8.6f;
        Calendar c = Calendar.getInstance();
        _startDate = c.getTime();
    }
	
	public void set_id(long id) {             _id = id;      }

    public Date get_endDate() {        return _endDate;      }
    public Date get_startDate() {      return _startDate;    }
    public float get_accInterest() {   return _accInterest;  }
    public float get_actInterest() {   return _actInterest;  }
    public float get_principle() {     return _principle;    }
    public float get_rate() {          return _rate;         }
	public float get_tds() {           return _tds;	         }
    public int get_daysRemain() {      return _daysRemain;   }
    public int get_progress() {        return _progress;     }
    public long get_id() {             return _id;           }
    public int get_tenure() {          return _tenure;       }
    public String get_accNum() {       return _accNum;       }
    public String get_bank() {         return _bank;         }
    public String get_title() {        return _title;        }
    public String get_note() {         return _note;         }
	public int get_type() {            return _type;         }
	public int get_status() {          return _status;       }
	public float get_intPerDay() {     return _intPerDay;    }

    public String get_info() {
        DecimalFormat df = new DecimalFormat("##,##,###");
        return df.format(_principle)+" @ "+_rate+"%";
    }

    public String get_startDateText() {
        final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(_startDate);
        return df.format(cal.getTime());
    }
}
