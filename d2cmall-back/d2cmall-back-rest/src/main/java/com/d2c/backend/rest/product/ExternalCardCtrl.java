package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.model.ExternalCard;
import com.d2c.product.service.ExternalCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 外部卡导入
 *
 * @author wwn
 */
@RestController
@RequestMapping(value = "/rest/product/externalcard")
public class ExternalCardCtrl extends SuperCtrl {

    @Autowired
    private ExternalCardService externalCardService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Response importCard(HttpServletRequest request, Long sourceId) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int successCount = 0;
        List<Map<String, Object>> excelData = this.getExcelData(request);
        for (Map<String, Object> map : excelData) {
            try {
                ExternalCard card = new ExternalCard();
                card.setCode(map.get("兑换码").toString());
                card.setSourceId(sourceId);
                card = externalCardService.insert(card);
                if (card.getId() != null && card.getId() > 0) {
                    successCount++;
                }
            } catch (Exception e) {
            }
        }
        result.setMessage("成功操作" + successCount + "条，失败" + (excelData.size() - successCount) + "条");
        return result;
    }

}
