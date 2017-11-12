package lsm.helpers.IO.write.image;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GifWriter {
    private final ImageWriter writer;
    private final ImageOutputStream output;
    private final ImageWriteParam imageWriteParam;
    private final IIOMetadata imageMetaData;

    public GifWriter(String filename) throws IOException {
        this(filename, 100);
    }

    public GifWriter(String filename, int msBetweenFrames) throws IOException {
        this(filename, msBetweenFrames, true);
    }

    public GifWriter(String filename, int msBetweenFrames, boolean repeat) throws IOException {
        this(filename, msBetweenFrames, repeat, true);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public GifWriter(String filename, int msBetweenFrames, boolean repeat, boolean overwrite) throws IOException {
        File file = new File(filename + ".gif");
        if(overwrite && file.exists()) file.delete();

        this.writer = ImageIO.getImageWritersByFormatName("gif").next();
        this.output = ImageIO.createImageOutputStream(file);

        // Begining black magic
        imageWriteParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
        imageMetaData = writer.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
        String metaFormatName = imageMetaData.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(msBetweenFrames / 10));
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");
        int loop = repeat ? 0 : 1;
        child.setUserObject(new byte[]{0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
        getNode(root, "ApplicationExtensions").appendChild(child);
        imageMetaData.setFromTree(metaFormatName, root);
        // Ending black magic

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
        writer.writeToSequence(new IIOImage(image, null, imageMetaData), imageWriteParam);
    }

    public void close() throws IOException {
        writer.endWriteSequence();
        output.close();
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nodes = rootNode.getLength();
        for (int i = 0; i < nodes; i++)
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0)
                return((IIOMetadataNode) rootNode.item(i));
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }

    ///////////////////////////////
    // Utility functions
    ///////////////////////////////

    public static RenderedImage createImage(Color[][] colors) {
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x].getRGB());
        return image;
    }

    public static RenderedImage createImage(int[][] colors) {
        BufferedImage image = new BufferedImage(colors[0].length, colors.length, BufferedImage.TYPE_BYTE_INDEXED);
        for (int y = 0; y < colors.length; y++)
            for (int x = 0; x < colors[0].length; x++)
                image.setRGB(x, y, colors[y][x]);
        return image;
    }
}

