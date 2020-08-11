package com.spectrum.mall.mybatis.page;

import com.spectrum.mall.mybatis.BasePage;

import java.util.List;

/**
 * @author oe_qinzuopu
 */
public class Page<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

    public Page() {
    }

    public Page(List<T> list) {
        super(list);
    }

    public Page(List<T> list, int navigatePages) {
        this(list);
    }
}
