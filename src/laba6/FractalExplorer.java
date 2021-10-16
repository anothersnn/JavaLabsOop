package laba6;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.io.File;

public class FractalExplorer {
    private int displaySize;
    private int rowsRemaining;
    private JImageDisplay display;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    private JButton saveButton = new JButton();
    private JButton resetButton = new JButton();
    private JComboBox myComboBox = new JComboBox();

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

        JComboBox comboBox = new JComboBox();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());

        ButtonHandler fractalChooser = new ButtonHandler();
        comboBox.addActionListener(fractalChooser);

        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Фрактал:");
        topPanel.add(label);
        topPanel.add(comboBox);
        frame.add(topPanel, BorderLayout.NORTH);

        JButton saveButton = new JButton("Сохранить");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(resetButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /** Рисует фрактал */
    private void drawFractal()
    {
        enableUI(false);
        rowsRemaining = displaySize;
        for (int x=0; x<displaySize; x++){
            FractalWorker fractalWorker = new FractalWorker(x);
            fractalWorker.execute();
        }
    }

    /** Отключает пользовательский интерфейс (фрейм) */
    private void enableUI(boolean val) {
        saveButton.setEnabled(val);
        resetButton.setEnabled(val);
        myComboBox.setEnabled(val);
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (e.getSource() instanceof JComboBox) {
                JComboBox src = (JComboBox) e.getSource();
                fractalGenerator = (FractalGenerator) src.getSelectedItem();
                fractalGenerator.getInitialRange(range);
                drawFractal();

            } else if (command.equals("Сбросить")) {
                fractalGenerator.getInitialRange(range);
                drawFractal();
            } else if (command.equals("Сохранить")) {
                // Сохранение изображения
                JFileChooser chooser = new JFileChooser();

                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);

                int userSelection = chooser.showSaveDialog(display);

                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    java.io.File file = chooser.getSelectedFile();
                    java.io.File savingFile = new java.io.File(file.getPath() + file.getName() + ".png");

                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "PNG", savingFile);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Не удалось сохранить изображение",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return;
            }
        }
    }

    private class ResetHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (rowsRemaining != 0) return;
            int x = e.getX();
            int y = e.getY();
            double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = fractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object> {
        int y;
        private int[] pixels;

        private FractalWorker(int y) {
            this.y = y;
        }

        // Функция для работы в фоне
        protected Object doInBackground() {

            // Считает строку символов
            pixels = new int[displaySize];
            for (int i = 0; i < pixels.length; i++) {
                double xCoord = fractalGenerator.getCoord(range.x, range.x + range.width, displaySize, i);
                double yCoord = fractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                //Считает количество итераций
                int iteration = fractalGenerator.numIterations(xCoord, yCoord);

                //В зависимости от количества итераций меняет цвет пикселя
                if (iteration == -1) pixels[i] = 0;
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    pixels[i] = rgbColor;
                }
            }
            return null;
        }

        // Вызывается после того, как doInBackground закончилась
        // рисует отдельную строки пикселей
        protected void done() {
            for (int i = 0; i < pixels.length; i++) {
                display.drawPixel(i, y, pixels[i]);
            }
            display.repaint(0, 0, y, displaySize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0) enableUI(true);
        }
    }

    //Запуск программы
    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(700);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}