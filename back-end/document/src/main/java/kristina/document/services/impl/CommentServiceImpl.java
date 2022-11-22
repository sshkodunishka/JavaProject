package kristina.document.services.impl;

import kristina.document.Repository.CommentRepository;
import kristina.document.Repository.DocumentRepository;
import kristina.document.Repository.UserRepository;
import kristina.document.dto.CommentDTO;
import kristina.document.exception.DocumentNotFoundException;
import kristina.document.exception.MyNotOwnerException;
import kristina.document.models.Comment;
import kristina.document.models.Document;
import kristina.document.services.CommentService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    public CommentServiceImpl(CommentRepository commentRepository, DocumentRepository documentService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.documentRepository = documentService;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDTO> getAllFromDocument(Long documentId) {
        return commentRepository.findAllByDocumentId(documentId).stream()
                .map((CommentDTO::fromComment)).collect(Collectors.toList());
    }

    @Override
    public void addNewComment(CommentDTO commentDTO,Long id,String username) {
        Comment comment = commentDTO.toComment();
        comment.setUser(userRepository.findByUsername(username));
        Optional<Document> doc = documentRepository.findById(id);
        if (!doc.isPresent()) {
            throw new DocumentNotFoundException("Document with id ${commentDTO.getDocumentId()} not found");
        }
        comment.setDocument(doc.get());
        commentRepository.save(comment);
        log.info("IN addNewComment - comment added successfully");
    }


    @Override
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && comment.getUser().getUsername().equals(username)) {
            commentRepository.deleteById(commentId);
            log.info("IN delete - comment with id: ${id} successfully deleted");
        }
        else
        {
            log.info("IN delete - comment with id: ${id} not deleted");
            throw new MyNotOwnerException("Comment not belong to user with username " + username);
        }
    }
}
