package com.example.mysqltest.devtools;

import com.example.mysqltest.abstraction.I_ValidatorRule;

import java.util.ArrayList;

//Валидатор полей работающий по принципу передачи отвественности от одного правила к другому
public class InputValidator {
    private ArrayList<I_ValidatorRule> validators = new ArrayList<I_ValidatorRule>();
    private ArrayList<Integer> errorCodes = new ArrayList<Integer>();
    private boolean result = true;

    public InputValidator(ArrayList<I_ValidatorRule> validators){
        this.validators = validators;
    }

    public ArrayList<Integer> getErrorCodes(){
        return errorCodes;
    }

    public boolean check(){
        result = execute();
        return result;
    }

    public boolean getResult(){
        return result;
    }

    private boolean execute(){
        for(I_ValidatorRule validator : this.validators){
            if(!validator.execute()){
                errorCodes.add(validator.getFailReason());
                result = false;
            }
        }
        if (errorCodes.size() > 0) {
            return false;
        }
        return true;
    }
}
