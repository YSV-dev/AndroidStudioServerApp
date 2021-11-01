package com.example.mysqltest.devtools.validators;

import android.widget.TextView;

import com.example.mysqltest.abstraction.I_ValidatorRule;

public class InputLatinChars implements I_ValidatorRule {
    private final static String latin = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ";
    private final static String numbers = "1234567890";
    private final static String specialChars = "~!@#$%^&*()_+\\|/{}:<>?.\"[];-=`";
    private String alphabet = latin;
    private String inputValue;
    private int reason = ReasonCode.OK;

    public InputLatinChars(TextView textView, boolean allowNumberChars, boolean allowSpecialChars){
        if(allowNumberChars) alphabet += numbers;
        if(allowSpecialChars) alphabet += specialChars;
        this.inputValue = textView.getText().toString();
    }

    @Override
    public boolean execute() {

        for(char ch : inputValue.toCharArray()){
            if(alphabet.indexOf(ch) == -1){
                reason = ReasonCode.VALUE_CHARS_NON_ALPHABET;
                return false;
            }
        }
        return true;
    }

    @Override
    public int getFailReason() {
        return reason;
    }
}
