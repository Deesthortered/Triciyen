package root.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message {
    @Id
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name="conversationId", nullable=false)
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "login", nullable = false)
    private UserAccount user;

    private LocalDateTime creationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="messageContentTypeId")
    private MessageContentType contentType;

    private String content;
}
