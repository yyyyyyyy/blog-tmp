package com.yaochow.servicedata.entity;

import com.yaochow.servicedata.common.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 笔记
 */
@Document(collection = "note")
@Data
public class Note extends BaseEntity {

    @Id
    private String id;
    @Field("account_id")
    private String accountId;
    @Field("note_name")
    private String noteName;
    @Field("category")
    private String category;
    @Field("note_content")
    private String noteContent;

}
