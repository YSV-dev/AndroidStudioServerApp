package com.example.mysqltest.entities;

public class ClientProfile {
    private static Profile clientProfile;

    public static void setClientProfile(Profile clientProfile_){
        clientProfile = clientProfile_;
    }

    public static Profile getProfile(){
        return clientProfile;
    }
}
