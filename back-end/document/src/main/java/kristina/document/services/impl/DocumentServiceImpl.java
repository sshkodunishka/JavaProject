package kristina.document.services.impl;

import kristina.document.Repository.DocumentRepository;
import kristina.document.dto.DocumentDTO;
import kristina.document.exception.DocumentNotFoundException;
import kristina.document.exception.FileStorageException;
import kristina.document.exception.MyFileNotFoundException;
import kristina.document.exception.MyNotOwnerException;
import kristina.document.models.Document;
import kristina.document.models.User;
import kristina.document.property.FileStorageProperties;
import kristina.document.services.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final Path fileStorageLocation;

    public DocumentServiceImpl(
            DocumentRepository documentRepository, FileStorageProperties fileStorageProperties) {
        this.documentRepository = documentRepository;
        this.fileStorageLocation =
                Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public List<DocumentDTO> getAll() {
        return documentRepository.findAll().stream()
                .map((DocumentDTO::fromDocument))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getAllByDescription(String description) {
        if (description != null && !description.isEmpty()) {
            return documentRepository.findByDescriptionContaining(description).stream()
                    .map((DocumentDTO::fromDocument))
                    .collect(Collectors.toList());
        } else {
            return documentRepository.findAll().stream()
                    .map((DocumentDTO::fromDocument))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void addNewDoucment(DocumentDTO documentDTO, User user) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            Document document = documentDTO.toDocument();
            document.setUser(user);
            documentRepository.save(document);
            log.info("IN addNewDoucment - document added successfully");
        } else {
            log.info("IN addNewDoucment - document wasn't add. Forbidden");
            throw new MyNotOwnerException("Only admins can upload new files");
        }
    }

    @Override
    public void deleteDoucmentById(Long id, String username) throws IOException {
        Document document =
                documentRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new DocumentNotFoundException(
                                                "Document not exist with id :" + id));
        if (document != null && document.getUser().getUsername().equals(username)) {
            Path filePath = this.fileStorageLocation.resolve(document.getFilename()).normalize();
            Files.deleteIfExists(filePath);
            documentRepository.deleteById(id);
            log.info("IN deleteDoucmentById - document with id: ${id} successfully deleted");
        } else {
            log.info("IN deleteDoucmentById - document with id: ${id} successfully deleted");
            throw new MyNotOwnerException("Document not belong to user with username " + username);
        }
    }

    @Override
    public void updateDoucment(Long id, DocumentDTO documentDTO) {
        Document document =
                documentRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new DocumentNotFoundException(
                                                "Document not exist with id :" + id));
        document.setTitle(documentDTO.getTitle());
        document.setDescription(documentDTO.getDescription());
        Document updatedDocument = documentRepository.save(document);
        log.info("IN updateDoucment - document with id: ${id} successfully update");
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "." + fileName;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            System.out.println(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException(
                    "Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Document findById(Long id) {
        Document result = documentRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no document found by id: {}", id);
            return null;
        }

        log.info("IN findById - document: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}

