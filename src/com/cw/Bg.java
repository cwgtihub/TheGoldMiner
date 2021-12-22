package com.cw;

import java.awt.*;

public class Bg {
    // 通关卡数
    static int clearance_level = Config.clearance_level;
    // 关卡数
    static int level = Config.level;
    // 倒计时时间
    static int times = Config.times;
    // 目标得分
    int goal = level*Config.multiple;
    // 总分
    static int count = Config.count;
    // 药水默认数量
    static int waterNum = Config.waterNum;
    // 药水状态
    static boolean waterFlag = false;
    // 药水价格
    int price = Config.price;
    // 是否进入购买
    boolean shop = false;

    // 药水
    Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");
    // 开始时间
    long startTime;
    // 结束时间
    long endTime;
    // 背景
    Image bg = Toolkit.getDefaultToolkit().getImage("imgs/bj.jpg");
    Image bg1 = Toolkit.getDefaultToolkit().getImage("imgs/bj1.jpg");
    Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
    void paintSelf(Graphics g,GameWin gw){
        g.drawImage(bg1, 0, 0, null);
        g.drawImage(bg, 0, 200, null);
        switch (GameWin.state){
            case 0:
                drawWord(g,80,Color.BLACK,"准备开始",gw.getSize().width/2 - 160,gw.getSize().height/2-40);
                drawWord(g,40,Color.BLACK,"点击鼠标右键开始",gw.getSize().width/2 - 160,gw.getSize().height/2+10);
                break;
            case 1:
                g.drawImage(peo, 250, 61, null);
                drawWord(g,30,Color.BLACK,"积分:"+count,30,150);
                // 药水组件
                g.drawImage(water, 460, 40, null);
                drawWord(g,30,Color.BLACK,"*:"+waterNum,510,60);
                // 关卡数
                drawWord(g,20,Color.BLACK,"第:"+level+"关",30,60);
                drawWord(g,30,Color.red,"鼠标左键抓取，抓到后鼠标右键使用药水",100,gw.getSize().height-40);
                // 目标积分
                drawWord(g,30,Color.BLACK,"目标积分:"+goal,30,110);
                // 时间组件
                endTime = System.currentTimeMillis();
                long tim = times - (endTime - startTime)/1000;
                drawWord(g,30,Color.BLACK,"时间"+(tim>0?tim:0),gw.getSize().width - 150,150);
                break;
            case 2:
                g.drawImage(water, 310, 400, null);
                drawWord(g,30,Color.BLACK,"价格:"+price,300,500);
                drawWord(g,30,Color.BLACK,"是否购买?",300,550);
                drawWord(g,30,Color.BLACK,"鼠标左键购买，右键不购买",300,600);
                if(shop){
                    count = count-price;
                    waterNum++;
                    shop=false;
                    GameWin.state=1;
                    startTime = System.currentTimeMillis();
                }
                break;
            case 3:
                drawWord(g,80,Color.red,"失败",gw.getSize().width/2 - 120,gw.getSize().height/2-120);
                drawWord(g,80,Color.red,"积分:"+count,gw.getSize().width/2 - 120,gw.getSize().height/2-20);
                drawWord(g,40,Color.red,"点击鼠标左键重新开始",gw.getSize().width/2 - 190,gw.getSize().height/2+30);
                break;
            case 4:
                drawWord(g,80,Color.green,"通关",gw.getSize().width/2 - 120,gw.getSize().height/2-120);
                drawWord(g,80,Color.green,"积分:"+count,gw.getSize().width/2 - 120,gw.getSize().height/2-20);
                drawWord(g,40,Color.green,"点击鼠标左键重新开始",gw.getSize().width/2 - 190,gw.getSize().height/2+30);
                break;
        }
    }
    // 倒计时
    boolean gameTime(){
        long tim = (endTime-startTime)/1000;
        if (tim > times) {
            return true;
        }
        return false;
    }

    // 重置元素
    void  reGame(){
        // 通关卡数
        clearance_level = Config.clearance_level;
        // 关卡数
        level = Config.level;
        // 倒计时时间
        times = Config.times;
        // 目标得分
        goal = level*Config.multiple;
        // 总分
        count = Config.count;
        // 药水默认数量
        waterNum = Config.waterNum;
        // 药水状态
        waterFlag = false;
    }

    // 绘制字符串
    public static void drawWord(Graphics g,int size,Color color,String str,int x,int y){
        g.setColor(color);
        g.setFont(new Font("仿宋", Font.BOLD,size ));
        g.drawString(str, x, y);
    }
}
