package com.cw;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music extends Thread {
    private Player player;
    private boolean loop;
    private String filename;
    public Music(String mp3File,boolean loop)  {
        this.filename = mp3File;
        this.loop = loop;
    }
    @Override
    public void run() {
        File file = new File(this.filename);
        FileInputStream fis = null;
        do {
            try {
                fis = new FileInputStream(file);
                BufferedInputStream stream = new BufferedInputStream(fis);
                player = new Player(stream);
                player.play();
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        }while (loop);
    }

    public void close(){
        loop = false;
        player.close();
        this.interrupt();
    }

}
