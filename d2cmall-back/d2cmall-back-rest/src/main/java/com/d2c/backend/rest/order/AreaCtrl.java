package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.response.Response;
import com.d2c.order.model.Area;
import com.d2c.order.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/area")
public class AreaCtrl extends BaseCtrl<BaseQuery> {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public JSONArray json(Integer level) {
        List<Area> list = areaService.findAll();
        List<Area> province = new ArrayList<>();
        List<Area> city = new ArrayList<>();
        List<Area> district = new ArrayList<>();
        for (Area area : list) {
            if (area.getCode() % 10000 == 0) {
                province.add(area);
            } else if (area.getCode() % 100 == 0) {
                city.add(area);
            } else {
                district.add(area);
            }
        }
        JSONArray cArray = new JSONArray();
        for (Area c : city) {
            JSONArray dArray = new JSONArray();
            for (Area d : district) {
                if (c.getCode() / 100 == d.getCode() / 100) {
                    JSONObject o = d.toJson();
                    o.put("children", new JSONArray());
                    dArray.add(o);
                }
            }
            JSONObject o = c.toJson();
            o.put("children", dArray);
            cArray.add(o);
        }
        JSONArray pArray = new JSONArray();
        for (Area p : province) {
            JSONArray cTemp = new JSONArray();
            for (int i = 0; i < cArray.size(); i++) {
                if (p.getCode() / 10000 == cArray.getJSONObject(i).getIntValue("code") / 10000) {
                    cTemp.add(cArray.getJSONObject(i));
                }
            }
            JSONObject o = p.toJson();
            o.put("children", cTemp);
            pArray.add(o);
        }
        return pArray;
    }

    @RequestMapping(value = "/json2", method = RequestMethod.GET)
    public JSONObject json2(Integer level) {
        List<Area> list = areaService.findAll();
        List<Area> province = new ArrayList<>();
        List<Area> city = new ArrayList<>();
        List<Area> district = new ArrayList<>();
        for (Area area : list) {
            if (area.getCode() % 10000 == 0) {
                province.add(area);
            } else if (area.getCode() % 100 == 0) {
                city.add(area);
            } else {
                district.add(area);
            }
        }
        JSONObject result = new JSONObject();
        for (Area p : province) {
            JSONObject pJson = new JSONObject();
            for (Area c : city) {
                if (p.getCode() / 10000 == c.getCode() / 10000) {
                    pJson.put(c.getCode().toString(), c.getName());
                }
            }
            result.put(p.getCode().toString(), pJson);
        }
        for (Area c : city) {
            JSONObject cJson = new JSONObject();
            for (Area d : district) {
                if (c.getCode() / 100 == d.getCode() / 100) {
                    cJson.put(d.getCode().toString(), d.getName());
                }
            }
            result.put(c.getCode().toString(), cJson);
        }
        JSONObject index = new JSONObject();
        for (Area p : province) {
            index.put(p.getCode().toString(), p.getName());
        }
        result.put("100000", index);
        return result;
    }

    @Override
    protected Response doList(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BaseQuery searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
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
    protected Response doHelp(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
