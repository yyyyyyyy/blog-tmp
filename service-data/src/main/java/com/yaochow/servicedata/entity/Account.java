package com.yaochow.servicedata.entity;

import com.yaochow.servicedata.common.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 账户
 */
@Document(collection = "account")
@Data
public class Account extends BaseEntity {
    @Id
    private String id;
    @Field("username")
    private String username;
    @Field("password")
    private String password;
    @Field("email")
    private String email;
    @Field("confirm")
    private String confirm;
}
