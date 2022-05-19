package com.huweiv.domain;

import lombok.Data;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName pageBean
 * @Description TODO
 * @CreateTime 2022/4/21 10:43
 */
@Data
public class PageBean<T> {

    private int  totalCount;
    private List<T> rows;
}
