package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberInvite;
import com.d2c.member.query.MemberInviteSearcher;

public interface MemberInviteService {

    MemberInvite insert(MemberInvite memberPrivilegeApply);

    PageResult<MemberInvite> findBySearcher(MemberInviteSearcher searcher, PageModel page);

    int doRefuse(Long id, String refuseReason, String lastModifyMan);

    int doAgree(Long id, String lastModifyMan);

}
