package com.d2c.logger.service;

import com.d2c.logger.model.UpyunTask;

import java.util.List;

public interface UpyunTaskService {

    UpyunTask findByTaskIds(String taskIds);

    UpyunTask insert(UpyunTask upyunTask);

    UpyunTask callBackInsert(UpyunTask upyunTask);

    List<String> findPicsByTaskIds(String[] taskIds);

}
