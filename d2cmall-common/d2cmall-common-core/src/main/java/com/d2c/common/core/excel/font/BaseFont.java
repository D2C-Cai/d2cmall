package com.d2c.common.core.excel.font;

import com.d2c.common.base.exception.FileException;
import jxl.write.WriteException;

public abstract class BaseFont {

    public BaseFont() {
        try {
            initFont();
        } catch (WriteException e) {
            throw new FileException(e);
        }
    }

    public abstract void initFont() throws WriteException;

}
