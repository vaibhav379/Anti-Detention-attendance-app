package com.darte.vaibhav.anti_detention;

/**
 * Created by VAIBHAV on 03-08-2016.
 */
public class attendence {
    private String subject;
    private int absent;
    private int present;





    public attendence(String subject,int present,int absent)
    {
        this.setSubject(subject);
        this.setPresent(present);
        this.setAbsent(absent);





    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }








}
