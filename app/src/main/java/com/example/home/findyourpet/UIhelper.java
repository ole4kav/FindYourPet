package com.example.home.findyourpet;

import android.view.View;
import android.widget.EditText;

/**
 * Created by HOME on 23/04/2016.
 */
public class UIhelper {

    public static View.OnFocusChangeListener myFocus = new View.OnFocusChangeListener(){
        public void onFocusChange (View v,boolean hasFocus){
            EditText editText = (EditText) v;
            String text = editText.getText().toString();
            String tag = editText.getTag().toString();
            if ((hasFocus) && (text.equals(tag))){
                ((EditText)v).setText("");
            }
            if ((!hasFocus)&&(text.equals(""))) {
                ((EditText)v).setText(tag);
            }
        }
    };
}
