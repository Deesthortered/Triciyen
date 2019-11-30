package root.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message_content_type")
public class MessageContentType implements Serializable {
    @Id
    private Integer messageContentTypeId;

    @Column(unique=true)
    private String name;
}
