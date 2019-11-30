package root.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_account")
public class UserAccount implements Serializable {
    @Id
    private String login;
    private String password;
    private String name;

    @Column(unique=true)
    private String email;
}