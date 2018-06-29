package com.boot.entity;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 描述：
 *
 * @author: zhangzy
 * @create: 2018-06-29
 */
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}
