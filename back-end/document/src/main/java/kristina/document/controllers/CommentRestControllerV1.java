package kristina.document.controllers;


import kristina.document.dto.CommentDTO;
import kristina.document.services.CommentService;
import kristina.document.services.DocumentService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1/documents/{id}/comments")
public class CommentRestControllerV1 {
    private final CommentService commentService;
    public CommentRestControllerV1(CommentService commentService, DocumentService documentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity addComment(
            Principal principal,
            @PathVariable(name = "id") Long id,
            @RequestBody CommentDTO commentDTO
    ){

        commentService.addNewComment(commentDTO,id,principal.getName());
        return ResponseEntity.ok("Comment add successfully");
    }
    @DeleteMapping()
    public ResponseEntity deleteComment(
            Principal principal,
            @Param(value = "commentId") Long commentId
    ){
        commentService.deleteComment(commentId, principal.getName());
        return ResponseEntity.ok("Comment delete successfully");
    }
}
