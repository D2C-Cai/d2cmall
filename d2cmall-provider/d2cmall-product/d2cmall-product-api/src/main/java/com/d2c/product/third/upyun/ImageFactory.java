package com.d2c.product.third.upyun;

import com.d2c.product.third.upyun.core.Picture;

import java.io.File;
import java.io.IOException;

public class ImageFactory {

    private static ImageFactory instance;

    public static Picture getPicture(File image) throws IOException {
        if (instance == null)
            instance = new ImageFactory();
        return instance.get(image);
    }

    public static Picture getPicture() throws IOException {
        if (instance == null)
            instance = new ImageFactory();
        return instance.get();
    }

    private Picture get(File image) throws IOException {
        return new PictureModel(image);
    }

    private Picture get() throws IOException {
        return new PictureModel();
    }

}
