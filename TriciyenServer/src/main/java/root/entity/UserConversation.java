package root.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_conversation")
@DynamicUpdate
public class UserConversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userConversationId;

    @ManyToOne
    @JoinColumn(name = "login", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name="conversationId", nullable=false)
    private Conversation conversation;

    private Integer lastReadMessageId;
}
