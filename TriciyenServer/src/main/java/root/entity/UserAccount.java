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
@Entity(name="UserAccount")
@Table(name="UserAccount")
public class UserAccount implements Serializable {
    @Id
    private String login;
    private String password;
    private String name;

    @Column(unique=true)
    private String email;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "UserConversation",
            joinColumns = @JoinColumn(name = "login"),
            inverseJoinColumns = @JoinColumn(name = "conversationId")
    )
    private List<Conversation> conversations;
}