package com.yaochow.servicedata.entity;

import com.yaochow.servicedata.common.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户信息
 */
@Document(collection = "user")
@Data
public class User extends BaseEntity {
    @Id
    private String id;
    @Field("account_id")
    private String accountId;
    @Field("name")
    private String name;
    @Field("certificate_type")
    private String certificateType;
    @Field("certificate_code")
    private String certificateCode;
    @Field("age")
    private String age;
    @Field("gender")
    private String gender;
    @Field("birthday")
    private String birthday;
}
