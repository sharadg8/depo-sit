package com.sharad.finance.deposit;

/**
 * Created by Sharad on 12-Sep-15.
 */
public class DetailItem {
    String _text;
    String _info;
    int _imgId;
    boolean _divider;

    DetailItem(String text, String info, int imgId, boolean divider) {
        _text = text;
        _info = info;
        _imgId = imgId;
        _divider = divider;
    }

    public int get_imgId() {        return _imgId;    }
    public String get_info() {        return _info;    }
    public String get_text() {        return _text;    }
    public boolean is_divider() {        return _divider;    }
}