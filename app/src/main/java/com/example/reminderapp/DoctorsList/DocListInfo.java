package com.example.reminderapp.DoctorsList;

public class DocListInfo {
        String name,num,desc,mail;
        int proflImg,phnImg,mailImg;
        int expandablecont;
        int cardview;
        int arrbutt;

        public DocListInfo(String name,String num,String desc,String mail,int proflImg,int phnImg,int mailImg,int expandablecont,int cardview,int arrbutt)
        {
            this.name=name;
            this.num=num;
            this.desc=desc;
            this.mail=mail;
            this.proflImg=proflImg;
            this.phnImg=phnImg;
            this.mailImg=mailImg;
            this.expandablecont=expandablecont;
            this.cardview=cardview;
            this.arrbutt=arrbutt;
        }

        public String getName() {
            return name;
        }

        public String getNum() {
            return num;
        }

        public String getDesc() {
            return desc;
        }

        public String getMail() {
            return mail;
        }

        public int getProflImg() {
            return proflImg;
        }

        public int getPhnImg() {
            return phnImg;
        }

        public int getMailImg() {
            return mailImg;
        }

        public int getExpandablecont()
        {
            return expandablecont;
        }

        public int getCardview()
        {
            return cardview;
        }

        public int getArrbutt()
        {
            return arrbutt;
        }
}
