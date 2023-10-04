package com.example.reminderapp.calculators;

public class IdealWeight {
    double hight;
    double idealWeight;

    public IdealWeight()
    {

    }

    public IdealWeight(double hight)
    {
        this.hight=hight;
    }

    public double getIdealWeight(double hightMeter,String sex)
    {
        double hightcm=hightMeter*100.0;
        if(sex.equals("Male") || sex.equals("male"))
        {
            idealWeight =50.0+(0.91*(hightcm-152.4));
            idealWeight=Math.round(idealWeight*10.0)/10.0;
            return idealWeight;
        }

        else if(sex.equals("Female") || sex.equals("female"))
        {
            idealWeight =45.5+(0.91*(hightcm-152.4));
            idealWeight=Math.round(idealWeight*10.0)/10.0;
            return idealWeight;
        }
        return idealWeight;
    }
}
