package com.d2c.flame.freemarker;

import com.d2c.common.base.utils.security.D2CSign;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class RenderStaticSign implements TemplateMethodModelEx {

    protected static final Log logger = LogFactory.getLog(RenderStaticSign.class);

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
        return D2CSign.SEARCH_KEY;
    }

}
