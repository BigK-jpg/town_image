package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputImage = new File("C:/Users/sogyo/Downloads/town4.png");
        BufferedImage original = ImageIO.read(inputImage);

        int maxZoom = 5; // Or however deep you want to go

        for (int z = 0; z <= maxZoom; z++) {
            int tilesPerSide = (int) Math.pow(2, z);
            int tileSize = 256;
            int imageSize = tilesPerSide * tileSize;

            // Scale image to current zoom level size
            BufferedImage scaled = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(original, 0, 0, imageSize, imageSize, null);
            g.dispose();

            for (int x = 0; x < tilesPerSide; x++) {
                for (int y = 0; y < tilesPerSide; y++) {
                    int xPos = x * tileSize;
                    int yPos = y * tileSize;

                    BufferedImage tile = scaled.getSubimage(xPos, yPos, tileSize, tileSize);
                    File output = new File("tiles/" + z + "/" + x + "/" + y + ".png");
                    output.getParentFile().mkdirs();
                    ImageIO.write(tile, "png", output);
                }
            }
        }

        System.out.println("Tiles generated.");
    }
}
