package lsm.helpers.filehandling;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Imager {

    public static void storeAnimation(int[][][] colors, String filename, String filetype) throws IOException {
        BufferedImage[] images = new BufferedImage[colors.length];
        for (int i = 0; i < images.length; i++)
            images[i] = createImage(colors[i]);
        storeAnimation(images, filename, filetype);
    }

    public static void storeAnimation(Color[][][] colors, String filename, String filetype) throws IOException {
        BufferedImage[] images = new BufferedImage[colors.length];
        for (int i = 0; i < images.length; i++)
            images[i] = createImage(colors[i]);
        storeAnimation(images, filename, filetype);
    }

    public static void storeAnimation(BufferedImage[] images, String filename, String filetype) throws IOException {
        Writer writer = getWriter(filename, filetype);
        for (BufferedImage image : images)
            writer.add(image);
        writer.close();
    }

    public static void storeImage(int[][] colors, String filename, String filetype) throws IOException {
        storeImage(createImage(colors), filename, filetype);
    }

    public static void storeImage(Color[][] colors, String filename, String filetype) throws IOException {
        storeImage(createImage(colors), filename, filetype);
    }

    public static void storeImage(BufferedImage image, String filename, String filetype) throws IOException {
        storeAnimation(new BufferedImage[]{image}, filename, filetype);
    }

    public static Color[][][] greyScale(int[][][] input) {
        Color[][][] colors = new Color[input.length][][];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color[][] greyScale(int[][] input) {
        Color[][] colors = new Color[input.length][];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color[] greyScale(int[] input) {
        Color[] colors = new Color[input.length];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color greyScale(int i) {
        return new Color(i, i, i, 1);
    }

    public static BufferedImage createImage(Color[][] colors) {
        if (colors == null || colors.length == 0)
            throw new IllegalArgumentException("Matrix needs to be X times Y size and above 0");
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x].getRGB());
        return image;
    }
    private static BufferedImage createImage(int[][] colors) {
        if (colors == null || colors.length == 0)
            throw new IllegalArgumentException("Matrix needs to be X times Y size and above 0");
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x]);
        return image;
    }

    public static Writer getWriter(String filename, String filetype) throws IOException {
        return new Writer(filename, filetype);
    }

}

