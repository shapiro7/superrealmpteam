package com.example.newmemeo;

import java.io.Serializable;

public class Memo implements Serializable {

    int seq;
    String miantext;
    String subetxt;
    int isdone;


    public Memo(int seq, String miantext, String subetxt, int isdone) {
        this.seq = seq;
        this.miantext = miantext;
        this.subetxt = subetxt;
        this.isdone = isdone;
    }

    public Memo(String maintext, String subetxt, int isdone){
        this.miantext =maintext;
        this.subetxt = subetxt;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


    public String getMiantext() {
        return miantext;
    }

    public void setMiantext(String miantext) {
        this.miantext = miantext;
    }

    public String getSubetxt() {
        return subetxt;
    }

    public void setSubetxt(String subetxt) {
        this.subetxt = subetxt;
    }


    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}
