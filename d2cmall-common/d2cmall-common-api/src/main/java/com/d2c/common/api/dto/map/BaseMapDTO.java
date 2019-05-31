package com.d2c.common.api.dto.map;

import com.d2c.common.base.utils.JsonUt;

import java.util.LinkedHashMap;

/**
 * @author wull
 */
public class BaseMapDTO extends LinkedHashMap<String, String> {

    private static final long serialVersionUID = 4604163869121345788L;

    @Override
    public String toString() {
        return JsonUt.serialize(this);
    }

}
