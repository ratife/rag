package mg.tife.ui.controller;

import mg.tife.domain.usecase.AddDocumentUsecase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/documents")
@Tag(name = "Document Management", description = "API for managing documents")
public class DocumentController {
    private final AddDocumentUsecase addDocumentUsecase;

    public DocumentController(AddDocumentUsecase addDocumentUsecase) {
        this.addDocumentUsecase = addDocumentUsecase;
    }

    @Operation(summary = "Upload and index a document", description = "Uploads a file and indexes its content for later retrieval")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document successfully indexed",
                content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error occurred during document indexing",
                content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addDocument(
            @Parameter(
                    description = "File to be uploaded and indexed",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {
        try {
            addDocumentUsecase.execute(file.getBytes(), file.getContentType());
            return ResponseEntity.ok("Document indexed successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing document: " + e.getMessage());
        }
    }


    @Operation(summary = "Liste document", description = "Afficher la liste des documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document successfully indexed",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error occurred during document indexing",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<String> all() {
        try {
            return ResponseEntity.ok("Document indexed successfully: " );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing document: " + e.getMessage());
        }
    }
}
