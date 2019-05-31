/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.d2c.frame.provider.service;

import com.d2c.common.api.dto.DtoHandler;
import com.d2c.common.api.dto.ResDTO;
import com.d2c.common.api.model.BaseParentDO;
import com.d2c.mybatis.service.BaseServiceImpl;

/**
 * REST实现类
 */
public abstract class RestServiceImpl<T extends BaseParentDO<?>> extends BaseServiceImpl<T> implements RestService<T> {

    @Override
    public T selectById(Integer id) {
        return super.findOneById(id);
    }

    @Override
    public ResDTO saveEntity(T entity) {
        return DtoHandler.success(super.save(entity));
    }

    @Override
    public ResDTO updateEntity(Integer id, T entity) {
        return DtoHandler.success(super.updateNotNull(entity));
    }

    @Override
    public ResDTO deleteById(Integer id) {
        return DtoHandler.success(super.deleteById(id));
    }

}
