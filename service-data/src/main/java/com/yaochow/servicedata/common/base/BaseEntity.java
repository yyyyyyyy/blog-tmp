package com.yaochow.servicedata.common.base;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class BaseEntity {
    @Field("gmt_created")
    protected Date gmtCreated;
    @Field("creator")
    protected String creator;
    @Field("gmt_modified")
    protected Date gmtModified;
    @Field("modifier")
    protected String modifier;
    @Field("is_delete")
    protected String delete;

}
