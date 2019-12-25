package com.triciyen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private Integer messageId;
    private Date creationTime;
    private Conversation conversation;
    private UserAccount user;
    private MessageContentType contentType;
    private String content;
}
