package com.example.reminderapp.calculators;

import java.text.DecimalFormat;

import static java.lang.Math.round;

public class BMICalc {
    double meterHeight;
    double weightKg;
    double bmi;

    public BMICalc()
    {

    }

    public BMICalc(double meter,double kg)
    {
        this.meterHeight=meter;
        this.weightKg=kg;
    }

    public double CalculateBMI(double meter,double kg)
    {
        bmi=kg/(meter*meter);
        bmi=Math.round(bmi*10.0)/10.0;
        return bmi;
    }
}
