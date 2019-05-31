package com.d2c.logger.dao;

import com.d2c.logger.model.UpyunTask;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpyunTaskMapper extends SuperMapper<UpyunTask> {

    UpyunTask findByTaskIds(String taskIds);

    List<String> findPicsByTaskIds(@Param("taskIds") String[] taskIds);

}
