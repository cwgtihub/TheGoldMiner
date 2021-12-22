package com.cw;

import java.awt.*;

public class Gold extends Object{
    Gold(){
        this.x = (int)(Math.random()*700);
        this.y = (int)(Math.random()*550+300);
        this.width = 50;
        this.height = 48;
        this.flag = false;
        this.m=30;
        this.count=4;
        this.type=1;
        this.img =  Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
    }
}
class GoldMini extends Gold{
    public GoldMini() {
        this.width = 29;
        this.height = 27;
        this.m=15;
        this.count=2;
        this.img =  Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");
    }
}

class GoldPlus extends Gold{
    public GoldPlus() {
        this.x = (int)(Math.random()*650);
        this.width = 96;
        this.height = 88;
        this.m=60;
        this.count=8;
        this.img =  Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");
    }
}