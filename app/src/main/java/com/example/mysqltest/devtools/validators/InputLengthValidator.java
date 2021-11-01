package com.example.mysqltest.devtools.validators;

import android.widget.TextView;

import com.example.mysqltest.abstraction.I_ValidatorRule;

public class InputLengthValidator implements I_ValidatorRule {
    private int maxLength;
    private int minLength;
    private String inputValue;
    private int reason = ReasonCode.OK;

    public InputLengthValidator(TextView textView, int minLength, int maxLength){
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.inputValue = textView.getText().toString();
    }

    public int getFailReason(){
        return reason;
    }

    @Override
    public boolean execute() {
        int length = inputValue.length();
        if (length < minLength) {
            reason = ReasonCode.VALUE_LENGTH_LESS;
            return false;
        }
        if(length > maxLength){
            reason = ReasonCode.VALUE_LENGTH_IS_GREATER;
            return false;
        }
        return true;
    }
}
