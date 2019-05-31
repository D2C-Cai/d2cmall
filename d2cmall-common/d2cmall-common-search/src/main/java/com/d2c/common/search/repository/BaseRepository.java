package com.d2c.common.search.repository;

import com.d2c.common.search.model.ParentSearchDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends ParentSearchDO<ID>, ID extends Serializable> extends ElasticsearchRepository<T, ID> {

}
