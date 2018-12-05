package com.example.demo.Models;

public class Rating {

    private int idr;
    private String evaluation;
    private int ascore;
    private int tscore;
    private int idk;

    public Rating(int idr, String evaluation, int ascore, int tscore, int idk) {
        this.idr = idr;
        this.evaluation = evaluation;
        this.ascore = ascore;
        this.tscore = tscore;
        this.idk = idk;
    }

    public Rating() {
    }

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getAscore() {
        return ascore;
    }

    public void setAscore(int ascore) {
        this.ascore = ascore;
    }

    public int getTscore() {
        return tscore;
    }

    public void setTscore(int tscore) {
        this.tscore = tscore;
    }

    public int getIdk() {
        return idk;
    }

    public void setIdk(int idk) {
        this.idk = idk;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "idr=" + idr +
                ", evaluation='" + evaluation + '\'' +
                ", ascore=" + ascore +
                ", tscore=" + tscore +
                ", idk=" + idk +
                '}';
    }
}

