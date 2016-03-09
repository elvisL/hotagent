package com.huotu.hotagent.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 产品列表树
 * Created by cwb on 2016/3/9.
 */
@Getter
@Setter
public class ProductTreeModel {

    private String text;
    private List<ProductTreeModel> nodes;

}
