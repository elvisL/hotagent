/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.support;

import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.service.common.Authority;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by allan on 1/29/16.
 */
@Converter(autoApply = true)
public class AuthorityConverter implements AttributeConverter<Set<Authority>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Authority> attribute) {
        if (attribute == null) {
            return null;
        }
        String authoritiesStr = "";
        if (attribute.size() > 0) {
            for (Authority authority : attribute) {
                authoritiesStr += authority.getValue() + ",";
            }

            return authoritiesStr.substring(0, authoritiesStr.length() - 1);
        }
        return "";
    }

    @Override
    public Set<Authority> convertToEntityAttribute(String dbData) {
        Set<Authority> authorities = new HashSet<>();
        if (StringUtils.isEmpty(dbData)) {
            return authorities;
        }
        String[] authorityArray = dbData.split(",");
        for (String auth : authorityArray) {
            authorities.add(EnumHelper.getEnumType(Authority.class, auth));
        }
        return authorities;
    }
}
