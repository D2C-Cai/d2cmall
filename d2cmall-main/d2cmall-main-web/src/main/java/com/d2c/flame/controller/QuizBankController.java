package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.query.QuizBankSearcher;
import com.d2c.content.search.model.SearcherQuizBank;
import com.d2c.content.search.query.QuizBankSearchBean;
import com.d2c.content.search.service.QuizBankSearcherService;
import com.d2c.content.service.QuizBankService;
import com.d2c.flame.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/quizbank")
public class QuizBankController extends BaseController {

    @Autowired
    private QuizBankService quizBankService;
    @Reference
    private QuizBankSearcherService quizBankSearcherService;

    /**
     * 列表查询
     *
     * @param model
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONObject list(ModelMap model) {
        String value = "2:5,1:4,3:1";
        List<SearcherQuizBank> quizBanks = new ArrayList<>();
        JSONObject json = new JSONObject();
        String[] type = value.split(",");
        QuizBankSearchBean searcher = new QuizBankSearchBean();
        QuizBankSearcher countSearch = new QuizBankSearcher();
        for (String condition : type) {
            String[] conditions = condition.split(":");
            searcher.setType(Integer.parseInt(conditions[0]));
            searcher.setMark(1);
            PageResult<SearcherQuizBank> pager = quizBankSearcherService.search(searcher, new PageModel());
            countSearch.setType(Integer.parseInt(conditions[0]));
            countSearch.setMark(1);
            int totalCount = quizBankService.countBySearcher(countSearch);
            // 当总数小于所需时，就不用随机取了
            if (totalCount <= Integer.parseInt(conditions[1])) {
                quizBanks.addAll(pager.getList());
            } else {
                quizBanks = this.getRandomQuiz(pager.getList(), Integer.parseInt(conditions[1]), quizBanks);
            }
        }
        json.put("quizz", quizBanks);
        return json;
    }

    /**
     * 从quziSource中随机取num题加到quizDestination中
     *
     * @param quizSource
     * @param num
     * @param quizDestination
     * @return
     */
    private List<SearcherQuizBank> getRandomQuiz(List<SearcherQuizBank> quizSource, int num,
                                                 List<SearcherQuizBank> quizDestination) {
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        int i = 0;
        while (set.size() < num && i < 50) {
            int index = random.nextInt(quizSource.size());
            if (set.add(index)) {
                quizDestination.add(quizSource.get(index));
            }
            i++;
        }
        return quizDestination;
    }

}
