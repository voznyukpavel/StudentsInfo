package studentsinfo3.util;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class ResizedImage {

    private ResizedImage() {
    }

    public static Image resize(Image image, int size) {
        return scaleImage(Display.getCurrent(), image, size);
    }

    private static Image scaleImage(Display display, Image image, int size) {

        ImageData imageData = image.getImageData();
        if (imageData == null)
            return image;
        Image newImage = new Image(display, size, size);
        GC gc = new GC(newImage);
        gc.drawImage(image, 0, 0, imageData.width, imageData.height, 0, 0, size, size);
        gc.dispose();
        return newImage;
    }

}
