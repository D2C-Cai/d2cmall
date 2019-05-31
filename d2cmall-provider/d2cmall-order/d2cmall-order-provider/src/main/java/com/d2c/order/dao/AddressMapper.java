package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地址管理
 *
 * @author xh
 */
public interface AddressMapper extends SuperMapper<Address> {

    List<Address> findByMemberInfoId(@Param("memberInfoId") Long memberInfoId);

    // 查找某用户登记的所有地址，默认地址排前面
    List<Address> findByMemberIdAndKeyword(@Param("memberInfoId") Long memberInfoId, @Param("pager") PageModel pager,
                                           @Param("keyWord") String keyWord);

    int countByMemberInfoId(@Param("memberInfoId") Long memberInfoId, @Param("keyWord") String keyWord);

    int clearDefaultByMember(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    int settingDefault(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

}
