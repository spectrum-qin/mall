package com.spectrum.mall.mybatis;

import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class BasePage<T> implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);
    private static final long serialVersionUID = 1L;
    protected long total;
    protected List<T> list;
    private int pageNum;
    private int pageSize;
    private long pages;

    public BasePage() {
    }

    public BasePage(List<T> list) {
        this.list = list;
        if (list instanceof Page) {
            Page<T> page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = (long)page.getPages();
            this.total = page.getTotal();
        } else {
            this.pageSize = this.getPageSize() > 0 ? this.getPageSize() : 1;
            this.pageNum = this.getPageNum() > 0 ? this.getPageNum() : 1;
            this.total = this.total > 0L ? this.total : (long)list.size();
            this.pages = this.total / (long)this.getPageSize() + (long)(this.total % (long)this.pageSize == 0L ? 0 : 1);
        }

    }

    public BasePage(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public BasePage(List<T> list, int pageNum, int pageSize) {
        this(list, pageNum, pageSize, 0L);
    }

    public BasePage(List<T> list, int pageNum, int pageSize, long total) {
        this.list = list;
        if (list instanceof Page) {
            Page<T> page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = (long)page.getPages();
            this.total = page.getTotal();
        } else {
            this.pageSize = pageSize;
            this.pageNum = pageNum;
            this.total = total > 0L ? total : (long)list.size();
            this.pages = this.total / (long)this.getPageSize() + (long)(this.total % (long)this.pageSize == 0L ? 0 : 1);
        }

    }

    /** @deprecated */
    @Deprecated
    public static <T> BasePage<T> of(List<T> list) {
        return new BasePage(list, 1, 1);
    }

    public static <T> BasePage<T> of(List<T> list, int pageNum, int pageSize, long total) {
        return new BasePage(list, pageNum, pageSize, total);
    }

    public String toString() {
        return "BasePage{total=" + this.total + ", list=" + this.list + '}';
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return this.total;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public long getPages() {
        return this.pages;
    }
}
