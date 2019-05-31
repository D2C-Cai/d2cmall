package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.content.dto.NavigationDto;
import com.d2c.content.model.Navigation;
import com.d2c.content.query.NavigationSearcher;
import com.d2c.content.service.NavigationService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/navigation")
public class NavigationCtrl extends BaseCtrl<NavigationSearcher> {

    @Autowired
    private NavigationService navigationService;

    @Override
    protected List<Map<String, Object>> getRow(NavigationSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(NavigationSearcher searcher) {
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
    protected Response doHelp(NavigationSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(NavigationSearcher searcher, PageModel page) {
        List<NavigationDto> navigationTreeList = navigationService.getNavigationTreeList(searcher.getVersion());
        return new SuccessResponse(navigationTreeList);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Navigation navigation = navigationService.findById(id);
        result.put("navigation", navigation);
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
        Navigation navigation = navigationService.findById(id);
        navigationService.delete(id, navigation.getParentId());
        result.setMessage("删除成功");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Navigation navigation = JsonUtil.instance().toObject(data, Navigation.class);
        SuccessResponse result = new SuccessResponse();
        if (navigationService.findNavigationByCode(navigation.getCode()) == null) {
            navigation = navigationService.insert(navigation);
            result.put("navigation", navigation);
        } else {
            result.setStatus(-1);
            result.setMessage("代码重复，添加不成功");
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Navigation navigation = JsonUtil.instance().toObject(data, Navigation.class);
        SuccessResponse result = new SuccessResponse();
        navigationService.update(navigation);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(Navigation navigation) {
        SuccessResponse result = new SuccessResponse();
        try {
            navigationService.update(navigation);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public Response updateOrderList(Integer sequence, Long id) {
        Navigation navigation = navigationService.findById(id);
        navigationService.updateSequence(sequence, id, navigation.getParentId());
        SuccessResponse result = new SuccessResponse();
        return result;
    }

    @RequestMapping(value = "/updateOrders", method = RequestMethod.POST)
    public Response updateOrders(String ids, String sequences) {
        SuccessResponse result = new SuccessResponse();
        String[] idStrArray = ids.split(",");
        String[] sequenceStrArray = sequences.split(",");
        int[] sequenceArray = new int[sequenceStrArray.length];
        Long[] idArray = new Long[idStrArray.length];
        if (sequenceStrArray.length == idStrArray.length) {
            int paramLenth = idStrArray.length;
            for (int i = 0; i < paramLenth; i++) {
                sequenceArray[i] = Integer.valueOf(sequenceStrArray[i]);
                idArray[i] = Long.valueOf(idStrArray[i]);
            }
            navigationService.updateSequence(sequenceArray, idArray);
        }
        return result;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public Response refresh() {
        navigationService.refreshCache();
        SuccessResponse result = new SuccessResponse();
        return result;
    }

}
