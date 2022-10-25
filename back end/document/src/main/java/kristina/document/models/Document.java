package kristina.document.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "documents")
@Data
public class Document extends BaseEntity {

  public Document() {
    super(new Date(), new Date(), Status.ACTIVE);
  }

  public Document(String title, String description) {
    super(new Date(), new Date(), Status.ACTIVE);
    this.title = title;
    this.description = description;
  }

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "filename")
  private String filename;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "document",fetch = FetchType.LAZY)
  private List<Comment> comments;

  @OneToMany(mappedBy = "document",fetch = FetchType.LAZY)
  private List<Mark> marks;
}
