package lsm.helpers.IO.read.image;

import lsm.helpers.IO.read.Source;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import static lsm.helpers.IO.Utils.isWebsite;
import static lsm.helpers.IO.read.Source.*;

public class ImageReader {

    private static RenderedImage readWebsite(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

    private static RenderedImage readFile(String path) throws IOException {
        return ImageIO.read(Paths.get(path).toFile());
    }

    public static RenderedImage read(String path, Source source) throws IOException {
        switch (source) {
            case Website:
                return readWebsite(path);
            case File:
                return readFile(path);
            default:
                throw new IllegalArgumentException("No source given");
        }
    }

    public static RenderedImage read(String path) throws IOException {
        return read(path, isWebsite(path) ? Website : File);
    }
}
