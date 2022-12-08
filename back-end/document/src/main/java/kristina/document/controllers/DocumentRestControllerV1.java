package kristina.document.controllers;

import kristina.document.dto.DocumentDTO;
import kristina.document.exception.MyNotOwnerException;
import kristina.document.models.Document;
import kristina.document.models.User;
import kristina.document.services.CommentService;
import kristina.document.services.DocumentService;
import kristina.document.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping(value = "/api/v1/documents")
public class DocumentRestControllerV1 {

    private final DocumentService documentService;
    private final UserService userService;
    private final CommentService commentService;

    public DocumentRestControllerV1(
            DocumentService documentService, UserService userService, CommentService commentService) {
        this.documentService = documentService;
        this.userService = userService;
        this.commentService = commentService;
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable(name = "id") Long id) {
        Document document = documentService.findById(id);

        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DocumentDTO result = DocumentDTO.fromDocument(document);
        result.setComments(commentService.getAllFromDocument(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<DocumentDTO>> getAllDocument() {
        List<DocumentDTO> result = documentService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity uploadFile(
            Principal principal,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description)
            throws IOException {
        String fileName = documentService.storeFile(file);

        String fileDownloadUri =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileName)
                        .toUriString();
        User user = userService.findByUsername(principal.getName());
        documentService.addNewDoucment(new DocumentDTO(title, description, fileName), user);
        return ResponseEntity.ok("Document add");
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = documentService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @PostMapping(value = "/search")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByDescription(
            @RequestParam("search") String description) {
        List<DocumentDTO> result = documentService.getAllByDescription(description);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocument(
            Principal principal,
            @PathVariable Long id) throws IOException {
        User user = userService.findByUsername(principal.getName());
        if(!user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")))
        {
           return ResponseEntity.status(403).build();
        }
        documentService.deleteDoucmentById(id, principal.getName());
        return ResponseEntity.ok("Document delete successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity updateDocument(
            @PathVariable Long id,
            @RequestBody DocumentDTO documentDTO){
        documentService.updateDoucment(id,documentDTO);
        return ResponseEntity.ok("Document update successfully");
    }
}
