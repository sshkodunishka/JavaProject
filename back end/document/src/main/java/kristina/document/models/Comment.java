package kristina.document.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
public class Comment extends BaseEntity {

    @Column(name = "comment")
    private String comment;
    public Comment() {
        super(new Date(), new Date(), Status.ACTIVE);
    }

    public Comment(String commentMessage) {
        super(new Date(), new Date(), Status.ACTIVE);
        this.comment = commentMessage;
    }

    @ManyToOne( fetch = FetchType.LAZY)
    private User user;

    @ManyToOne( fetch = FetchType.LAZY)
    private Document document;
}
