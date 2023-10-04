package com.example.reminderapp.DoctorsList;

public class DoctorInfo {
    public String docname;
    public String Hospital;
    public String academicInfo;
    public String type;
    public String phnNo;
    public String docemail;
    public String id;

    public DoctorInfo()
    {

    }

    public DoctorInfo(String docname,String Hospital,String academicInfo,String type,String phnNo,String docemail,String id)
    {
        this.docname=docname;
        this.Hospital=Hospital;
        this.academicInfo=academicInfo;
        this.type=type;
        this.phnNo=phnNo;
        this.docemail=docemail;
        this.id=id;
    }
}
