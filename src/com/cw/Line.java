package com.cw;

import java.awt.*;

public class Line {
    // 起点坐标
    int x=380;
    int y=180;
    // 终点坐标
    int endx=500;
    int endy=500;

    // 线长
    double length = 100;
    // 最小值
    double MIN_length = 100;
    // 最大值
    double MAX_length = 750;
    // 角度
    double n = 0;
    // 方向
    int dir = 1;
    // 状态 0 摇摆 1 抓取 2 收回 3 抓取返回
    int state;
    // 钩爪
    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");

    GameWin frame;
    Line(GameWin frame){
        this.frame = frame;
    }

    // 碰撞检测
    void logic(){
        for (Object obj:this.frame.objectList) {
            if(endx>obj.x && endx<obj.x+obj.width && endy>obj.y && endy<obj.y+obj.height){
                state = 3;
                obj.flag=true;
            }
        }

    }


    // 绘制
    void lines(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        endx = (int)(x + length*Math.cos(n*Math.PI));
        endy = (int)(y + length*Math.sin(n*Math.PI));
        g2.setStroke(new BasicStroke(3.0f));
        g2.setColor(Color.red);
        g2.drawLine(x, y, endx, endy);
        // 钩子旋转
        g2.rotate(Math.toRadians(n*180 - 90), endx,endy);
        g2.drawImage(hook, endx-30, endy-2, null);
    }

    // 绘图
    void paintSelf(Graphics g){
        logic();
        switch (state){
            case 0:
                if(n<0.1){dir=1;}
                else if(n>0.9){dir=-1;}
                n+=0.005*dir;
                lines(g);
                break;
            case 1:
                if (length<MAX_length) {
                    length=length+5;
                    lines(g);
                }else{
                    state=2;
                }
                break;
            case 2:
                if (length>MIN_length) {
                    length=length-5;
                    lines(g);
                }else{
                    state=0;
                }
                break;
            case 3:
                int m=1;
                if(length>MIN_length){
                    length=length-5;
                    lines(g);
                    for (Object obj:this.frame.objectList) {
                        if (obj.flag) {
                            m=obj.m;
                            obj.x=endx-obj.getWidth()/2;
                            obj.y=endy;
                            if (length<=MIN_length) {
                                obj.x=-150;
                                obj.y=-150;
                                obj.flag = false;
                                Bg.waterFlag=false;
                                // 加分
                                Bg.count+=obj.count;
                                state=0;
                            }

                            if (Bg.waterFlag) {
                                if (obj.type==1) {
                                    m=1;
                                }
                                if (obj.type==2) {
                                    obj.x=-150;
                                    obj.y=-150;
                                    obj.flag = false;
                                    Bg.waterFlag=false;
                                    state=2;
                                }
                            }
                        }
                    }
                }
                // 质量休眠
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    // 重置元素
    void  reGame(){
        n=0;
        length=100;
    }
}
