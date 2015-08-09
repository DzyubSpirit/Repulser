package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Dzyub_000 on 06.08.2015.
 */
public class Main extends JFrame
    implements Runnable {

    private long lastTime = -1;
    private final long DELAY = 33;

    Board board;
    PhModel model;

    public Main() {
        setSize(800, 600);

        initUI();

        setTitle("Repulser");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initUI() {

        board = new Board();
        setLayout(null);
        board.setLocation(5, 5);

        JFrame temp = new JFrame();
        temp.pack();
        Insets insets = temp.getInsets();
        temp = null;

        int w = getWidth() - insets.right - insets.left - 10;
        int h = getHeight() - insets.top - insets.bottom - 10;

        board.setSize(w, h);

        add(board);

        model = new PhModel(100, 0);
        model.setSize(0, w, 0, h);
        board.setModel(model);

    }

    @Override
    public void run() {
        do {
            Calendar calendar = Calendar.getInstance();
            long newTime = calendar.getTimeInMillis();
            if (newTime - lastTime < DELAY) {
                try {
                    Thread.sleep(2, 0);
                } catch (InterruptedException e) {

                }
                continue;
            }


//            board.setModel(new PhModel());
            if (lastTime == -1) {
                lastTime = newTime - DELAY;
            }

            model.tick(newTime - lastTime);
//            setTitle("X: " + model.getBalls().get(0).getX());
            setTitle("Delay: " + (newTime - lastTime));
            board.repaint();
//            repaint();

            lastTime = newTime;
        } while (true);
    }

    public static void main(String[] args) {


        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main mainWin = new Main();
                mainWin.setVisible(true);

                Thread mainThread = new Thread(mainWin);
                mainThread.start();
            }
        });
    }
}
