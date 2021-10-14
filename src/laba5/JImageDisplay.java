package laba5;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent {
    private BufferedImage image;

    //Конструктор для создания дисплея
    JImageDisplay(int height, int width) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }

    //Переопределние функции для отрисовки картинки
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(),image.getHeight(), null);
    }

    //Очистить картинку
    public void clearImage()
    {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    //Нарисовать пиксель по двум координатам и заданному цвету
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }

    // Получение изображения
    public BufferedImage getImage() {
        return image;
    }
}
