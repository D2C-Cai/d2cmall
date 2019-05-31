package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.SectionDto;
import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionSearcher;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.service.SectionService;
import com.d2c.content.service.SectionValueService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/section")
public class SectionCtrl extends BaseCtrl<SectionSearcher> {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionValueService sectionValueService;

    @Override
    protected List<Map<String, Object>> getRow(SectionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SectionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(SectionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SectionSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Section> pager = sectionService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        if (searcher.getModuleId() != null) {
            result.put("moduleId", searcher.getModuleId());
        }
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Section section = sectionService.findById(id);
        SectionDto sectionDto = null;
        if (section != null) {
            sectionDto = new SectionDto();
            BeanUtils.copyProperties(section, sectionDto);
            this.initSection(sectionDto);
        }
        result.put("section", sectionDto);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        result.setMessage("操作成功");
        int success = sectionService.delete(id);
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Section section = (Section) JsonUtil.instance().toObject(data, Section.class);
        if (StringUtil.isBlank(section.getMemberLevel())) {
            section.setMemberLevel("N");
        }
        section = sectionService.insert(section);
        result.put("section", section);
        result.setMessage("新增成功");
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Section section = (Section) JsonUtil.instance().toObject(data, Section.class);
        if (StringUtil.isBlank(section.getMemberLevel())) {
            section.setMemberLevel("N");
        }
        sectionService.update(section);
        result.put("section", section);
        result.setMessage("修改成功");
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    private void initSection(SectionDto section) {
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setSectionDefId(section.getId());
        PageModel page = new PageModel();
        PageResult<SectionValue> pager = sectionValueService.findBySearcher(searcher, page);
        section.setSectionValues(pager.getList());
    }

}
