package root.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Conversation")
@Table(name="Conversation")
public class Conversation implements Serializable {
    @Id
    private Integer conversationId;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "UserConversation",
            joinColumns = @JoinColumn(name = "conversationId"),
            inverseJoinColumns = @JoinColumn(name = "login")
    )
    private List<UserAccount> userAccounts;
}
