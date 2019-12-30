package com.triciyen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation {
    private Integer userConversationId;
    private UserAccount user;
    private Conversation conversation;
    private Integer lastReadMessageId;
}
