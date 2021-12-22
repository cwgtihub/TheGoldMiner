package com.cw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameWin extends JFrame {
    // 状态 0未开始 1运行中 2商店 3失败 4胜利
    static int state;
    // 存储物品
    List<Object> objectList = new ArrayList<Object>();
    Bg bg = new Bg();
    Line line = new Line(this);
    Image offscreenImage;
    {
        // 是否可以放置
        boolean isPlace = true;
        // 创建
        for (int i = 0; i < Config.goldNum; i++) {
            double random = Math.random();
            Gold gold; // 存放当前生成的金块
            if(random<0.3){
                gold = new GoldMini();
            }else if(random<0.7){
                gold = new Gold();
            }else{
                gold = new GoldPlus();
            }
            for(Object obj:objectList){
                if (gold.getRec().intersects(obj.getRec())) {
                    // 重叠不可放置，重新生成
                    isPlace=false;
                    break;
                }
            }
            if (isPlace){
                objectList.add(gold);
            }else{
                isPlace=true;
                i--;
            }
        }
        for (int i = 0; i < Config.rockNum; i++) {
            Rock rock = new Rock();
            for(Object obj:objectList){
                if (rock.getRec().intersects(obj.getRec())) {
                    // 重叠不可放置，重新生成
                    isPlace=false;
                    break;
                }
            }
            if (isPlace){
                objectList.add(rock);
            }else{
                isPlace=true;
                i--;
            }
        }
    }

    void launch(){
        this.setSize(768,1000);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imgs/icon.png"));
        this.setTitle("黄金矿工");
        this.setResizable(false);
//        this.setUndecorated(true);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        // 左右摇摆，点击左键
                        if(e.getButton()==1&&line.state==0){
                            line.state=1;
                        }
                        // 抓取返回，点击右键
                        if(e.getButton()==3&&line.state==3&&Bg.waterNum>0){
                            if(!Bg.waterFlag){
                                Bg.waterNum--;
                            }
                            Bg.waterFlag=true;
                        }
                        break;
                    case 2:
                        if(e.getButton()==1){
                            bg.shop=true;
                        }
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 3:
                    case 4:
                        if(e.getButton()==1){
                            state=0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:
                        break;
                }

            }
        });
//        Music music = new Music("musics/music.mp3",true);
//        music.start();
        AePlayWave aePlayWave = new AePlayWave("musics/8278.wav", true);
        aePlayWave.start();
//        // 延时5秒
//        Timer timer = new Timer();// 实例化Timer类
//        timer.schedule(new TimerTask() {
//            public void run() {
//                System.out.println("退出");
//                aePlayWave.close();
//                this.cancel();
//            }
//        }, 5000);
        while (true){
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 下一关
    public void nextLevel(){
        if (bg.gameTime() && state == 1) {
            if (Bg.count >= bg.goal) {
                // 通关
                if (Bg.level==Bg.clearance_level) {
                    state=4;
                }else {
                    state=2;
                    Bg.level++;
                }
            }else {
                state=3;
            }
            dispose();
            GameWin gameWin  = new GameWin();
            gameWin.launch();
        }

    }

    @Override
    public void paint(Graphics g) {
        offscreenImage=createImage(this.getSize().width,this.getSize().height);
        Graphics gImage = offscreenImage.getGraphics();
        bg.paintSelf(gImage,this);
        if (state == 1) {
            for (Object obj:objectList) {
                obj.paintSelf(gImage);
            }
            line.paintSelf(gImage);
        }
        g.drawImage(offscreenImage, 0, 0, null);
    }


    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
