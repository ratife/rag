package mg.tife.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tife.domain.domain.Document;
import mg.tife.domain.exception.ElementNotFundException;
import mg.tife.domain.usecase.document.AddDocumentUsecase;
import mg.tife.domain.usecase.document.GetListDocumentUsecase;
import mg.tife.ui.dto.document.DocumentDTO;
import mg.tife.ui.dto.document.DocumentRequest;
import mg.tife.ui.mapper.DocumentUIMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
@Tag(name = "Document Management", description = "API for managing documents")
public class DocumentController {
    private final AddDocumentUsecase addDocumentUsecase;
    private final GetListDocumentUsecase getListDocumentUsecase;

    public DocumentController(AddDocumentUsecase addDocumentUsecase,
                              GetListDocumentUsecase getListDocumentUsecase) {
        this.addDocumentUsecase = addDocumentUsecase;
        this.getListDocumentUsecase = getListDocumentUsecase;
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
            DocumentRequest request) {
        try {

            addDocumentUsecase.execute(DocumentUIMapper.INSTANCE.dtoToDomain(request));
            return ResponseEntity.ok("Document indexed successfully: " + request.getFile());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing document: " + e.getMessage());
        }
    }

    @Operation(summary = "Liste document", description = "Afficher la liste des documents")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<DocumentDTO>> all() throws ElementNotFundException {
            List<Document> list = getListDocumentUsecase.execute();
            return ResponseEntity.ok(list.stream().map(DocumentUIMapper.INSTANCE::domainToDto).toList());
    }
}
