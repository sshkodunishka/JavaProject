package kristina.document.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "marks")
@Data
public class Mark extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "mark")
  private TypeMark typeMark;

  public Mark() {
    super();
  }

  public Mark(TypeMark typeMark) {
    super(new Date(), new Date(), Status.ACTIVE);
    this.typeMark = typeMark;
  }

  @ManyToOne( fetch = FetchType.LAZY)
  private User user;

  @ManyToOne( fetch = FetchType.LAZY)
  private Document document;
}
