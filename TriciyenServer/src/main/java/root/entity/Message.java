package root.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
