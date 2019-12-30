package root.entity;

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

    private Integer contentTypeId;

    private String content;
}
