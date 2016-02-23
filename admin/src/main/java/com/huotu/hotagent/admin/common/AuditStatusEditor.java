package com.huotu.hotagent.admin.common;

import com.huotu.hotagent.service.common.AuditStatus;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;

/**
 * ÉóºË×´Ì¬Êý¾Ý°ó¶¨
 * Created by cwb on 2016/2/22.
 */
public class AuditStatusEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(StringUtils.isEmpty(text)) {
            setValue(AuditStatus.APPLYING);
        }
        switch (text) {
            case "0":
                setValue(AuditStatus.APPLYING);
                break;
            case "1":
                setValue(AuditStatus.PROCESSED);
                break;
        }
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}
