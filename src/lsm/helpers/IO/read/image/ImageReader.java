package lsm.helpers.IO.read.image;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import static lsm.helpers.IO.Utils.isWebsite;

public class ImageReader {

    private static RenderedImage readWebsite(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

    private static RenderedImage readFile(String path) throws IOException {
        return ImageIO.read(Paths.get(path).toFile());
    }

    public static RenderedImage read(String from) throws IOException {
        if (isWebsite(from))
            return readWebsite(from);
        return readFile(from);
    }
}
