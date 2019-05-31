package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.QuizBank;
import com.d2c.content.query.QuizBankSearcher;
import com.d2c.content.service.QuizBankService;
import com.d2c.member.model.Admin;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/quizbank")
public class QuizBankCtrl extends BaseCtrl<QuizBankSearcher> {

    @Autowired
    private QuizBankService quizBankService;
    @Autowired
    private ProductService productService;

    @Override
    protected Response doList(QuizBankSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<QuizBank> pager = quizBankService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(QuizBankSearcher searcher) {
        return quizBankService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(QuizBankSearcher searcher, PageModel page) {
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
    protected Response doHelp(QuizBankSearcher searcher, PageModel page) {
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
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        QuizBank quiz = JsonUtil.instance().toObject(data, QuizBank.class);
        quiz.setLastModifyMan(admin.getUsername());
        int success = quizBankService.update(quiz);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("保存不成功");
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        QuizBank quiz = JsonUtil.instance().toObject(data, QuizBank.class);
        quiz.setCreator(admin.getUsername());
        quiz.setLastModifyMan(admin.getUsername());
        quizBankService.insert(quiz);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int success = quizBankService.deleteById(id);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("删除不成功");
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int failCount = 0;
        for (Long id : ids) {
            int success = quizBankService.deleteById(id);
            if (success < 1) {
                failCount++;
            }
        }
        result.setMessage("删除成功" + (ids.length - failCount) + "条，删除不成功" + failCount + "条");
        return result;
    }

    @RequestMapping(value = "/mark/{id}", method = RequestMethod.POST)
    public Response doMark(@PathVariable("id") Long id, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = quizBankService.updateStatus(id, status, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("保存不成功");
        }
        return result;
    }

    @RequestMapping(value = "/batch/mark", method = RequestMethod.POST)
    public Response doBatchMark(Long[] ids, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int failCount = 0;
        for (Long id : ids) {
            int success = quizBankService.updateStatus(id, status, admin.getUsername());
            if (success < 1) {
                failCount++;
            }
        }
        result.setMessage("操作成功" + (ids.length - failCount) + "条，操作不成功" + failCount + "条");
        return result;
    }

    /**
     * 导入题库表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/quiz", method = RequestMethod.POST)
    public Response importQuiz(HttpServletRequest request) throws Exception {
        this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                QuizBank quizBank = new QuizBank();
                if (StringUtil.isBlank(map.get("题目").toString())) {
                    errorMsg.append("第" + row + "行：题目不能为空<br/>");
                    return false;
                } else {
                    quizBank.setTitle(map.get("题目").toString());
                }
                if (StringUtil.isBlank(map.get("答案").toString())) {
                    errorMsg.append("第" + row + "行：答案不能为空<br/>");
                    return false;
                } else {
                    quizBank.setAnswer(map.get("答案").toString());
                }
                if (StringUtil.isBlank(map.get("类型").toString())) {
                    errorMsg.append("第" + row + "行：类型不能为空<br/>");
                    return false;
                } else {
                    if (map.get("类型").toString().contains("1")) {
                        quizBank.setType(1);
                    } else if (map.get("类型").toString().contains("2")) {
                        quizBank.setType(2);
                    } else {
                        quizBank.setType(3);
                    }
                }
                if (StringUtil.isBlank(map.get("选项").toString())) {
                    errorMsg.append("第" + row + "行：选项不能为空<br/>");
                    return false;
                } else {
                    String[] strs = map.get("选项").toString().split(",");
                    JSONArray array = new JSONArray();
                    for (String str : strs) {
                        JSONObject obj = new JSONObject();
                        if (quizBank.getType() == 2 && str.contains(":")) {
                            String[] condition = str.split(":");
                            Long productId = Long.parseLong(condition[1]);
                            Product product = productService.findById(productId);
                            obj.put("pic", product.getProductImageListFirst());
                            obj.put("word", condition[0]);
                        } else {
                            obj.put("pic", "");
                            obj.put("word", str);
                        }
                        array.add(obj);
                    }
                    quizBank.setChoice(array.toString());
                }
                if (quizBank.getType() == 1 && StringUtil.isNotBlank(map.get("商品ID").toString())) {
                    Long productId = new BigDecimal(map.get("商品ID").toString()).longValue();
                    Product product = productService.findById(productId);
                    if (product != null) {
                        quizBank.setPic(product.getProductImageListFirst());
                    }
                }
                quizBankService.insert(quizBank);
                return true;
            }
        });
    }

}
