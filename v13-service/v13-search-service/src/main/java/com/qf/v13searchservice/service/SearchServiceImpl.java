package com.qf.v13searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.ISearchService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.mapper.TProductMapper;
import com.qf.v13.pojo.PageResultBean;
import com.qf.v13.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl  implements ISearchService{

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;


    @Override
    public ResultBean copyAll() {
        /*
        * 初始化solr数据库
        * 1.得到mysql数据库中的东西
        * 2.将数据存到solr数据库中(新增document进行新增)
        * */
        //得到mysql数据库的数据
        List<TProduct> productList = productMapper.list();
        SolrInputDocument document = new SolrInputDocument();
        for (TProduct product : productList) {
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_images",product.getImages());
            document.setField("product_sale_price",product.getSalePrice());

            try {
                solrClient.add(document);
            } catch (SolrServerException e) {
                e.printStackTrace();
                return new ResultBean("500","同步solr数据库失败!");
            } catch (IOException e) {
                e.printStackTrace();
                return new ResultBean("500","同步solr数据库失败");
            }
        }
        //提交事务
        try {
            solrClient.commit();
        } catch (SolrServerException  | IOException e ) {
            e.printStackTrace();
            return new ResultBean("500","提交事务失败!");
        }
        return new ResultBean("200","同步solr数据库成功!");
    }

    @Override
    public ResultBean<List<TProduct>> SearchByKeyword(String keyword) {
        SolrQuery condition = new SolrQuery();
        condition.setQuery("product_name:"+keyword);
        ResultBean resultBean = new ResultBean();
        /*设置分页*/
        //设置起始页

        try {
            //得到机构
            QueryResponse response = solrClient.query(condition);
            //得到documentLsit
            SolrDocumentList documentList = response.getResults();

            List<TProduct> list = new ArrayList(documentList.size());//确定list的长度
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setSalePrice(Long.parseLong(document.getFieldValue("product_sale_price").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setName(document.getFieldValue("product_name").toString());
                product.setImages(document.getFieldValue("product_images").toString());
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));

                list.add(product);

            }
                resultBean.setStatusCode("200");
                resultBean.setData(list);

        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            //若数据库中没有存在你搜索的关键词
            condition.setQuery("*:*");
            try {
                solrClient.query(condition);
            } catch (SolrServerException |IOException e1 ) {
                e1.printStackTrace();
                return new ResultBean("500",null);
            }
        }

        return resultBean;
    }

    @Override
    public long getTotalCount(String keyword) {
        SolrQuery condition = new SolrQuery();
        condition.setQuery("product_name:" + keyword);
        Long result;
        try {
            QueryResponse response = solrClient.query(condition);
            result = (long) response.getResults().size();

        } catch (SolrServerException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return result;
    }

    @Override
    public int getTotalPages(int pageSize, long totalCount) {
        int totalPages = (int)(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);
        return totalPages;
    }

    @Override
    public ResultBean addInfoToSolr(TProduct product) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("product_sale_point",product.getSalePoint());
        document.setField("product_images",product.getImages());
        document.setField("product_sale_price",product.getSalePrice());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return new ResultBean("500","solr服务器异常");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("500","添加至solr库失败");
        }
        return new ResultBean("200","添加信息成功!");
    }

    @Override
    //出错返回0,成功返回受影响的函数
    public int batchDel(List<Long> ids) {
        List<String> list = new ArrayList<>(ids.size());
        for (Long id : ids) {
            String ID = id.toString();
            list.add(ID);
        }
        try {
            UpdateResponse response = solrClient.deleteById(list);

        } catch (SolrServerException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return list.size();
    }

    @Override
    public ResultBean addInfoToSolrById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);
        return addInfoToSolr(product);

    }

    @Override
    public ResultBean<PageResultBean<TProduct>> searchByKeywordAndPaging(String keyword, PageResultBean pageResultBean) {
        SolrQuery condition = new SolrQuery();
        condition.setQuery("product_name:"+keyword);
        ResultBean resultBean = new ResultBean();
        /*设置分页*/
        //设置偏移量
        condition.setStart((pageResultBean.getCurrentPage()-1)*pageResultBean.getPageSize());
        //设置每页展示个数
        condition.setRows(pageResultBean.getPageSize());
        /*设置高亮效果*/
        //开启高亮效果
        condition.setHighlight(true);
        condition.addHighlightField("product_name");
        condition.setHighlightSimplePre("<font color='red'>");
        condition.setHighlightSimplePost("</font>");


        try {
            //得到结果
            QueryResponse response = solrClient.query(condition);
            //得到documentLsit
            SolrDocumentList documentList = response.getResults();
            //获得高亮信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();


            List<TProduct> list = new ArrayList(documentList.size());//确定list的长度
            //对结果进行拼接高亮信息
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setSalePrice(Long.parseLong(document.getFieldValue("product_sale_price").toString()));
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                //product.setName(document.getFieldValue("product_name").toString());
                product.setImages(document.getFieldValue("product_images").toString());
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //得到id的所有高亮信息
                Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                //得到对象字段名的高亮信息
                List<String> productHigh = idHigh.get("product_name");
                if(productHigh ==null||productHigh.isEmpty()){
                    //没有对此字段设置高亮信息或者没有关于product_name的字段名的信息
                    product.setName(document.getFieldValue("product_name").toString());
                }else {
                    product.setName(productHigh.get(0));
                }

                list.add(product);

            }

            resultBean.setStatusCode("200");
            pageResultBean.setResultList(list);

            resultBean.setData(pageResultBean);

        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            //若数据库中没有存在你搜索的关键词
            condition.setQuery("*:*");
            try {
                solrClient.query(condition);
            } catch (SolrServerException |IOException e1 ) {
                e1.printStackTrace();
                return new ResultBean("500",null);
            }
        }

        return resultBean;
    }


}
