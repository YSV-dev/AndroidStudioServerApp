package com.example.mysqltest.abstraction;

public interface I_ValidatorRule {
    public boolean execute();
    public int getFailReason();
}
