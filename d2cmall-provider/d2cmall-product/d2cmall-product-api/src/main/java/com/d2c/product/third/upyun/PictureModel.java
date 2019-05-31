package com.d2c.product.third.upyun;

import com.d2c.product.third.upyun.core.Picture;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PictureModel extends Picture {

    private static final long serialVersionUID = 1L;

    public PictureModel() {
    }

    public PictureModel(File imageFile) throws IOException {
        super(imageFile);
    }

    @Override
    public void resize() throws IOException {
    }

    public PictureModel newInstanceFromUrl(String url) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IOException {
        return (PictureModel) super.newInstanceFromUrl(url);
    }

    @Override
    public String getSubdirectory() {
        return "model";
    }

    @Override
    public Picture getPicture(File newFile) throws IOException {
        return new PictureModel(newFile);
    }

}
