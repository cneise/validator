package com.masterarbeit.compare;

import java.sql.Date;
import java.sql.SQLData;
import java.time.LocalDate;

/**
 * Created by jan-philippheinrich on 08.07.17.
 */
public class DateComp implements ComparerInterface {

    private final IntegerComp integerComp;

    DateComp(IntegerComp i1){
        this.integerComp = i1;
    }

    private LocalDate convertDate(Object a){
        LocalDate date;
        if (a.getClass() == Date.class){
            date = ((Date) a).toLocalDate();
        } else {
            date = ((LocalDate) a);
        }
        return date;
    }

    private double compareTwoValues(int org, int anonymus, int half, int full){

        if (org<anonymus){
            if (anonymus-org<(full-anonymus+org)){
                //System.out.println("1: " + (1.0-((anonymus-org)/15.0)));
                return (1.0-((anonymus-org)/(half+0.0)));
            } else {
                //System.out.println("2");
                return (1.0-((full-anonymus+org)/(half+0.0)));
            }
        } else {
            if (org-anonymus<(full-org+anonymus)){
                //System.out.println("3");
                return (1.0-((org-anonymus)/(half+0.0)));
            } else {
                //System.out.println("4");
                return (1.0-((full-org+anonymus)/(half+0.0)));
            }
        }

    }

    @Override
    public double compare(Object a, Object b, int sig) {

        LocalDate date1 = convertDate(a);
        LocalDate date2 = convertDate(b);

        double day = compareTwoValues(date1.getDayOfMonth(), date2.getDayOfMonth(), 15, 31);
        System.out.println("Day: " + day);
        double month = compareTwoValues(date1.getMonthValue(), date2.getMonthValue(), 6, 12);
        System.out.println("Month: " + month);
        double year = this.integerComp.compare(date1.getYear(),date2.getYear(), 1);
        System.out.println("Year: " + year);
        return ((day+month+year)/3);
    }
}