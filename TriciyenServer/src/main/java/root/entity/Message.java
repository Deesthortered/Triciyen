package root.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
