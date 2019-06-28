package com.qf.v13.pojo;

import java.io.Serializable;
import java.util.List;

public class PageResultBean<T> implements Serializable{
    //当前页
    private int currentPage;
    //总页数
    private int totalPage;
    //总条数
    private long totalCounts;
    //每页展示个数
    private int pageSize;
    //查询的对象放入的集合
    private List<T> resultList;

    //导航页
    private int navigatePage;

    //所有的导航页
    private int[] navigatePageNums;

    public PageResultBean(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;


    }

    public PageResultBean() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(long totalCounts) {
        this.totalCounts = totalCounts;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public int getNavigatePage() {
        return navigatePage;
    }

    public void setNavigatePage(int navigatePage) {
        this.navigatePage = navigatePage;
        this.calcNavigatepageNums();
    }

    public int[] getNavigatePageNums() {
        return navigatePageNums;
    }


    private void calcNavigatepageNums() {

        //导航页与总页数相比
        if(totalPage<=navigatePage){
            navigatePageNums = new int[totalPage];
            for (int i=0;i<totalPage;i++){
                navigatePageNums[i] = i+1;
            }
        }else {
            //导航页小于总页数
            //计算最小页和最大页
            navigatePageNums = new int[navigatePage];
            int maxPage = currentPage + navigatePage/2;
            int minPage = currentPage - navigatePage/2;
            int endPage = totalPage;
            //存在三种情况 最大页  最小页   中间页
            if(maxPage>totalPage){
                for (int i = navigatePage-1;i>=0;i--){

                    navigatePageNums[i] =endPage;
                    endPage--;

                }
            }else if(minPage<1){
                for (int i=0;i<navigatePage;i++){
                    navigatePageNums[i] = i+1;
                }
            }else {
                //处于中间页
                for (int i=0;i<navigatePage;i++){
                    navigatePageNums[i] = minPage++;

                }
            }
        }
    }
}
