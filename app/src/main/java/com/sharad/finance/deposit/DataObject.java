package com.sharad.finance.deposit;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sharad on 28-Aug-15.
 */
public class DataObject {
    private int    _id;
    private String _accNum;
    private String _bank;
    private float  _principle;
    private float  _rate;
    private Date   _startDate;
    private Date   _endDate;
    private int    _tenure;
    private int    _daysRemain;
    private float  _accInterest; //Accumulated interest
    private float  _actInterest; //Actual interest earned

    private boolean _active;

    DataObject(int id, String accNum, String bank,
               float principle, float rate, float actInterest,
               Date startDate, Date endDate, int tenure){
        _id = id; _accNum = accNum; _bank = bank; _principle = principle;
        _rate = rate; _actInterest = actInterest;
        _startDate = startDate; _endDate = endDate; _tenure = tenure;

        Calendar cal = Calendar.getInstance();
        cal.setTime(_endDate);
        cal.add(Calendar.DATE, _tenure);
        _active = cal.after(Calendar.getInstance());

        if(_active) {
            long diff = System.currentTimeMillis() - _startDate.getTime();
            float daySince = (float) diff / (24 * 60 * 60 * 1000);
            _daysRemain = (int)((tenure - daySince) > 0 ? (tenure - daySince) : 0);
            double roi = (double)_rate / 36500;
            _accInterest = (int)(_principle * roi * daySince);
        } else {
            _daysRemain = 0;
            _accInterest = _actInterest;
        }
    }

    DataObject(String accNum, String bank){
        _accNum = accNum; _bank = bank;
    }

    public Date get_endDate() {        return _endDate;      }
    public Date get_startDate() {      return _startDate;    }
    public float get_accInterest() {   return _accInterest;  }
    public float get_actInterest() {   return _actInterest;  }
    public float get_principle() {     return _principle;    }
    public float get_rate() {          return _rate;         }
    public int get_daysRemain() {      return _daysRemain;   }
    public int get_id() {              return _id;           }
    public int get_tenure() {          return _tenure;       }
    public String get_accNum() {       return _accNum;       }
    public String get_bank() {         return _bank;         }
    public boolean is_active() {       return _active;       }
}
