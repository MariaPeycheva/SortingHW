package com.company;

import javax.swing.*;
import java.awt.*;

class Square extends JPanel {
    private String name;

    public Square(String name, int size) {
        this.name = name;
        setPreferredSize(new Dimension(size, size));
        setBackground(Color.PINK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
