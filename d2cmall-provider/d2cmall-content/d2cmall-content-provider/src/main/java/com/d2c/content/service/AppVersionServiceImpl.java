package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.AppVersionMapper;
import com.d2c.content.model.AppVersion;
import com.d2c.content.query.AppVersionSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("appVersionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AppVersionServiceImpl extends ListServiceImpl<AppVersion> implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<AppVersion> findBySearch(AppVersionSearcher searcher, PageModel page) {
        PageResult<AppVersion> pager = new PageResult<>(page);
        int totalCount = appVersionMapper.countBySearch(searcher);
        List<AppVersion> appVersions = new ArrayList<>();
        if (totalCount > 0) {
            appVersions = appVersionMapper.findBySearch(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(appVersions);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateForce(Long id, int force, String lastModifyMan) {
        return appVersionMapper.updateForce(id, force, lastModifyMan);
    }

    @Override
    public List<AppVersion> findAllVersion(String appTerminal) {
        return appVersionMapper.findAllVersion(appTerminal);
    }

    @Override
    public AppVersion findLastVersion(String appTerminal, String type, String version) {
        Long digit = Long.parseLong(version.replace(".", ""));
        if (digit < 1000 && "APPANDROID".equalsIgnoreCase(appTerminal)) {
            digit = digit * 10;
        }
        // 查找最新版本的
        AppVersion newAppVersion = appVersionMapper.findLastVersion(appTerminal, type, digit);
        if (newAppVersion == null) {
            return null;
        }
        // 如果最新版是强制升级的，直接升级；否则查找期间是否存在强制升级
        if (newAppVersion.getUpgrade() == 1) {
            return newAppVersion;
        } else {
            Long id = appVersionMapper.findUpgrade(appTerminal, type, digit);
            if (id != null) {
                newAppVersion.setUpgrade(1);
            }
        }
        return newAppVersion;
    }

    @Override
    public AppVersion findSameVersion(AppVersion appVersion) {
        return appVersionMapper.findSameVersion(appVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AppVersion insert(AppVersion appVersion) {
        return this.save(appVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(AppVersion appVersion) {
        return this.updateNotNull(appVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteById(Long id, String lastModifyMan) {
        return appVersionMapper.deleteById(id, lastModifyMan);
    }

}
