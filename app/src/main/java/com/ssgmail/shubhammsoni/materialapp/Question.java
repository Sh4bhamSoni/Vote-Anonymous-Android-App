package com.ssgmail.shubhammsoni.materialapp;


public class Question {
    private String question;
    private Integer code;
    private String key;
    private Integer countAgree;
    private Integer countDisagree;
    //////////////////////////////////////////////


    Question() {


    }

    Question(int code, String question, String key, Integer countAgree,
             Integer countDisagree) {
        this.code = code;
        this.question = question;
        this.key = key;
        this.countAgree = countAgree;
        this.countDisagree = countDisagree;


    }

    public Integer getCountAgree() {
        return countAgree;
    }

    public String getQuestion() {
        return question;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public Integer getCountDisagree() {
        return countDisagree;
    }


}
