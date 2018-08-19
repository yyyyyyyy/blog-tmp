package com.yaochow.servicedata.common.base;

import com.yaochow.servicedata.common.DBConstant;

import java.util.Calendar;

public class BaseService {
    public void setInsertParam(final BaseEntity entity) {
        entity.setModifier(DBConstant.SYSTEM);
        entity.setGmtModified(Calendar.getInstance().getTime());
        entity.setCreator(DBConstant.SYSTEM);
        entity.setGmtCreated(Calendar.getInstance().getTime());
        entity.setDelete(DBConstant.UN_DELETE);
    }

    public void setUpdateParam(final BaseEntity entity) {
        entity.setModifier(DBConstant.SYSTEM);
        entity.setGmtModified(Calendar.getInstance().getTime());
    }

    public void setUnDeleted(final BaseEntity entity) {
        entity.setDelete(DBConstant.UN_DELETE);
    }

    public void setDeleted(final BaseEntity entity) {
        entity.setDelete(DBConstant.DELETE);
    }
}
