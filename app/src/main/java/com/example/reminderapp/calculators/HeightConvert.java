package com.example.reminderapp.calculators;

public class HeightConvert {
    double inch;
    double foot;
    double meters;
    double cm;

    public HeightConvert()
    {

    }

    public HeightConvert(double inch,double feet)
    {
        this.inch=inch;
        this.foot=feet;
    }

    public double  convertToMeter(double inch,double foot)
    {
        meters=((foot*12.0)+inch)*0.0254;
        return meters;
    }
}
