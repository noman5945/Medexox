package com.example.reminderapp.calculators;

import android.content.Context;
import android.widget.Toast;

public class BMRCalc {
    String sex,age;
    double weight,heightFeet,heightInch;
    double bmr;
    Context cnt;

    public BMRCalc()
    {

    }

    public BMRCalc(String sex,String age,double weight,double heightFeet,double heightInch)
    {
        this.age=age;
        this.sex=sex;
        this.weight=weight;
        this.heightFeet=heightFeet;
        this.heightInch=heightInch;
    }

    public double CalculateBMR(String sex,String age,double weight,double heightFeet,double heightInch)
    {
        double inch=heightFeet*12.0+heightInch;
        double weightlbs=weight*2.20462;
        try{
            double Age=Double.parseDouble(age);
            if(sex.equals("Male") || sex.equals("male"))
            {
                bmr=66.47+(6.24*weightlbs)+(12.7*inch)-(6.755*Age);
                bmr=Math.round(bmr*10.0)/10.0;
                return bmr;
            }
            else if(sex.equals("Female") || sex.equals("female"))
            {
                bmr=655.1+(4.35*weightlbs)+(4.7*inch)-(4.7*Age);
                bmr=Math.round(bmr*10.0)/10.0;
                return bmr;
            }
        }
        catch (NumberFormatException e)
        {
            //Toast.makeText(cnt,"Number age needed",Toast.LENGTH_LONG).show();
        }

        return bmr;
    }

}
