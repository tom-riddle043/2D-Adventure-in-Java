package com;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 16; //16 x 16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48 x 48 tile
    final int maxTileCol = 18;
    final int maxTileRow = 12;
    final int screenWidth = maxTileCol * tileSize; // 864 pixels
    final int screenHeight = maxTileRow * tileSize; // 576 pixels

    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime, timer = 0, drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }
//    public void run() {
//        double drawInterval = 1000000000 / fps;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while (gameThread != null){
////            long currentTime  = System.nanoTime();
////            System.out.println("current Time: " + currentTime);
//            //update: update information such as player positions
//            update();
//            //DRAW: draw the screen with the updated information
//            repaint();  //Calls the paintComponent method
//
//            try {
//                double remTime = System.nanoTime() - nextDrawTime;
//                remTime /= 1000000;
//                if(remTime < 0 ){
//                    remTime = 0;
//                }
//                Thread.sleep((long)remTime);
//                remTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }

    public void update(){
        if(keyH.upPressed) playerY -= playerSpeed;
        else  if(keyH.downPressed) playerY += playerSpeed;
        else  if(keyH.leftPressed) playerX -= playerSpeed;
        else  if(keyH.rightPressed) playerX += playerSpeed;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(playerX,playerY, tileSize, tileSize);
        g2d.dispose();
    }
}
