package kristina.document.Repository;

import kristina.document.models.Mark;
import kristina.document.models.TypeMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
  List<Mark> findAllByDocumentIdAndTypeMark(Long id, TypeMark typeMark);
}
