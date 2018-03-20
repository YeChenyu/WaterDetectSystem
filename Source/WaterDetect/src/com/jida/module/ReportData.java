package com.jida.module;

import com.jida.base.BaseObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenyuye on 17/12/1.
 */

public class ReportData extends BaseObject {

    public static final int TYPE_DAY = 0;
    public static final int TYPE_MONTH = 1;
    public static final int TYPE_QUARTER = 2;
    public static final int TYPE_YEAR = 3;

    public static final int TYPE_COUNT_MIN = 0;
    public static final int TYPE_COUNT_AVE = 1;
    public static final int TYPE_COUNT_MAX = 2;
    public static final int TYPE_COUNT_NORMAL = 3;

    private int reportType;
    private int timeType;
    private Date data;
    private int temper;
    private int ph;
    private int nh4n2;
    private int p;
    private int n2;
    private int codmn;

    private SimpleDateFormat sdf = null;

    public ReportData(int type, int timeType, Date date, int temper, int ph, int nh4n2, int p, int n2, int codmn){
        this.reportType = type;
        this.timeType = timeType;
        this.data = date;
        this.temper = temper;
        this.ph = ph;
        this.nh4n2 = nh4n2;
        this.p = p;
        this.n2 = n2;
        this.codmn = codmn;
    }

    public ReportData(int reportType, int temper, int ph, int nh4n2, int p, int n2, int codmn){
        this.reportType = reportType;
        this.temper = temper;
        this.ph = ph;
        this.nh4n2 = nh4n2;
        this.p = p;
        this.n2 = n2;
        this.codmn = codmn;
    }


    public String getData() {
        switch (getReportType()){
            case TYPE_DAY:
                sdf = new SimpleDateFormat("yyyy-MM-dd hh-00-00");
                return sdf.format(data);
            case TYPE_MONTH:
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(data);
            case TYPE_QUARTER:
                sdf = new SimpleDateFormat("yyyy-MM");
                return sdf.format(data);
            case TYPE_YEAR:
                sdf = new SimpleDateFormat("yyyy-MM");
                return sdf.format(data);
            default:
                return "NULL";
        }
    }

    public int getTemper() {
        return temper;
    }

    public int getPh() {
        return ph;
    }

    public int getNh4n2() {
        return nh4n2;
    }

    public int getP() {
        return p;
    }

    public int getN2() {
        return n2;
    }

    public int getCodmn() {
        return codmn;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTemper(int temper) {
        this.temper = temper;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public void setNh4n2(int nh4n2) {
        this.nh4n2 = nh4n2;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public void setCodmn(int codmn) {
        this.codmn = codmn;
    }
}
