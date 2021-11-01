package com.example.mysqltest.entities;

public class Profile {
    private int userId = -1;
    private String login = "";
    private String mail = "";
    private int mailStatus = 0;

    public Profile(int userId, String login, String mail, int mailStatus ){
        this.userId = userId;
        this.login = login;
        this.mail = mail;
        this.mailStatus = mailStatus;
    }

    public int getUserId(){
        return userId;
    }

    public String getLogin(){
        return login;
    }

    public String getMail(){
        return mail;
    }

    public int getMailStatus(){
        return mailStatus;
    }
}
