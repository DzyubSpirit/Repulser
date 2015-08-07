package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dzyub_000 on 07.08.2015.
 */
public class Board extends JPanel {
    private JLabel label;
    private PhModel model = null;

    public Board() {
        initUI();
    }

    private void initUI() {
        setBackground(Color.BLACK);

        label = new JLabel();

    };

    public int setModel(PhModel model) {
        this.model = model;
        return 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);

        if (model != null) {
            for (PhModel.Ball ball : model.getBalls()) {
                g2d.drawOval((int) Math.round(ball.getX()),
                        (int) Math.round( ball.getY()),
                        (int) ball.getRadiusX(),
                        (int) ball.getRadiusY());
            }
        }
    }
}
