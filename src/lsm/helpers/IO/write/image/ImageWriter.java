package lsm.helpers.IO.write.image;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageWriter {

    private final javax.imageio.ImageWriter writer;
    private final ImageOutputStream output;

    private ImageWriter(String filename, String filetype) throws IOException {
        Iterator<javax.imageio.ImageWriter> iterator;
        if ((iterator = ImageIO.getImageWritersByFormatName(filetype)) == null || !iterator.hasNext())
            throw new IllegalArgumentException("Filetype is unsupported");
        this.writer = iterator.next();
        this.output = ImageIO.createImageOutputStream(new File(filename + "." + filetype));
        writer.setOutput(output);
        writer.prepareWriteSequence(null);
    }

    public void add(int[][] image) throws IOException {
        add(createImage(image));
    }

    public void add(Color[][] image) throws IOException {
        add(createImage(image));
    }

    public void add(RenderedImage image) throws IOException {
        writer.writeToSequence(new IIOImage(image, null, null), writer.getDefaultWriteParam());
    }

    public void close() throws IOException {
        output.close();
    }

    ///////////////////////////////
    // ToUse functions
    ///////////////////////////////

    public static void save(int[][] colors, String filename, String filetype) throws IOException {
        save(createImage(colors), filename, filetype);
    }
    public static void save(int[][][] colors, String filename, String filetype) throws IOException {
        RenderedImage[] images = new RenderedImage[colors.length];
        for (int i = 0; i < images.length; i++)
            images[i] = createImage(colors[i]);
        save(images, filename, filetype);
    }

    public static void save(Color[][] colors, String filename, String filetype) throws IOException {
        save(createImage(colors), filename, filetype);
    }
    public static void save(Color[][][] colors, String filename, String filetype) throws IOException {
        RenderedImage[] images = new RenderedImage[colors.length];
        for (int i = 0; i < images.length; i++)
            images[i] = createImage(colors[i]);
        save(images, filename, filetype);
    }

    public static void save(RenderedImage image, String filename, String filetype) throws IOException {
        save(new RenderedImage[]{image}, filename, filetype);
    }
    public static void save(RenderedImage[] images, String filename, String filetype) throws IOException {
        ImageWriter writer = getWriter(filename, filetype);
        for (RenderedImage image : images)
            writer.add(image);
        writer.close();
    }

    ///////////////////////////////
    // Output functions
    ///////////////////////////////

    public static ImageWriter getWriter(String filename, String filetype) throws IOException {
        return new ImageWriter(filename, filetype);
    }

    ///////////////////////////////
    // Utility functions
    ///////////////////////////////

    public static RenderedImage createImage(Color[][] colors) {
        if (colors == null || colors.length == 0)
            throw new IllegalArgumentException("Matrix needs to be X times Y size and above 0");
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x].getRGB());
        return image;
    }
    private static RenderedImage createImage(int[][] colors) {
        if (colors == null || colors.length == 0)
            throw new IllegalArgumentException("Matrix needs to be X times Y size and above 0");
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x]);
        return image;
    }
}

