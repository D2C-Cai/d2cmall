package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Account;
import com.d2c.member.query.AccountSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountMapper extends SuperMapper<Account> {

    Account findByMemberId(@Param("memberInfoId") Long memberInfoId);

    Account findByAccount(@Param("account") String account);

    List<Account> findBySearch(@Param("searcher") AccountSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") AccountSearcher searcher);

    Map<String, BigDecimal> countAccountAmount();

    int updateMobile(@Param("accountId") Long id, @Param("mobile") String mobile,
                     @Param("nationCode") String nationCode);

    int changePassword(@Param("accountId") Long id, @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword);

    int doInOutAmount(@Param("accountId") Long accountId, @Param("cashAmount") BigDecimal cashAmount,
                      @Param("giftAmount") BigDecimal giftAmount, @Param("limitGiftAmount") BigDecimal limitGiftAmount,
                      @Param("limitDate") Date limitDate);

    int doLockAccount(@Param("accountId") Long accountId, @Param("locked") Boolean locked);

    int doUpdateRedAmount(@Param("accountId") Long accountId, @Param("redAmount") BigDecimal redAmount,
                          @Param("redDate") Date redDate);

    int doCleanLimitAmount(@Param("accountId") Long accountId);

    int doChangeMobile(@Param("memberId") Long memberId, @Param("newMobile") String newMobile,
                       @Param("nationCode") String nationCode);

    int doDelete(@Param("account") String account);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

}
