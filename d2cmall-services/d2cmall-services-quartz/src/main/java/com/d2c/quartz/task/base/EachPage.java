package com.d2c.quartz.task.base;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;

/**
 * 每页执行的操作
 *
 * @param <O>
 */
public interface EachPage<O> {

    int count();

    PageResult<O> search(PageModel page);

    boolean each(O object);

}
