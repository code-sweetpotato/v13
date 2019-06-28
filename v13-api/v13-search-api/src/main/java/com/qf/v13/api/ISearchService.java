package com.qf.v13.api;

import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.PageResultBean;
import com.qf.v13.pojo.ResultBean;

import java.util.List;

public interface ISearchService {

    public ResultBean copyAll();

    public ResultBean<List<TProduct>> SearchByKeyword(String keyword);

    public ResultBean<PageResultBean<TProduct>> searchByKeywordAndPaging(String keyword, PageResultBean<TProduct> pageResultBean);

    public long getTotalCount(String keyword);

    public int getTotalPages(int pageSize,long totalCount);

    public ResultBean addInfoToSolr(TProduct product);

    public int batchDel(List<Long> ids);


    public ResultBean addInfoToSolrById(Long id);
}
