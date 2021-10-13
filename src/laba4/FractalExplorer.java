package laba4;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;
        fractalGenerator = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractalGenerator.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    /**Отвечает за графический интерфейс
     * Создает фрейм куда добавляет графические компоненты */
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Отрисовка фракталов");
        frame.add(display, BorderLayout.CENTER);
        JButton resetButton = new JButton("Сбросить");
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);
        frame.add(resetButton, BorderLayout.SOUTH);
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /** Рисует фрактал */
    private void drawFractal() {
        /** Loop through every pixel in the display */
        for (int x=0; x<displaySize; x++) {
            for (int y=0; y<displaySize; y++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                //Считает количество итераций
                int iteration = fractalGenerator.numIterations(xCoord, yCoord);

                //В зависимости от количества итераций меняет цвет пикселя
                if (iteration == -1) display.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }

            }
        }

        display.repaint();
    }

    private class ResetHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    //Зум по клику
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    //Запуск программы
    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(700);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}