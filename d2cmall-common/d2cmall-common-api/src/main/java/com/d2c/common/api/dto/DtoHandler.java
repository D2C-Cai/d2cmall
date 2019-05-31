package com.d2c.common.api.dto;

import com.d2c.common.base.utils.BeanUt;

public class DtoHandler {

    public static ResDTO success() {
        return new ResDTO(true, "操作成功");
    }

    public static ResDTO success(Object data) {
        return new ResDTO(data);
    }

    public static ResDTO success(Integer total, Object data) {
        return new ResDTO(total, data);
    }

    public static ResDTO error(Exception e) {
        return error(e.getMessage());
    }

    public static ResDTO error(String message) {
        return new ResDTO(false, message);
    }

    public static ResDTO error(Object data, String message) {
        return new ResDTO(data, false, message);
    }
    //************************************************************

    public static <T> T buildDto(final Object orig, final Class<T> clz) {
        return BeanUt.buildBean(orig, clz);
    }

    public static void copyDto(final Object dest, final Object orig) {
        BeanUt.copyProperties(dest, orig);
    }

    public static void copyDto(final Object dest, final Object... origs) {
        BeanUt.copyProperties(dest, origs);
    }

}
