package mg.tife.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tife.domain.domain.Response;
import mg.tife.domain.usecase.AddDocumentUsecase;
import mg.tife.domain.usecase.SendMessageUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/messages")
@Tag(name = "messages Management", description = "API for managing messages")
public class MessageController {
    private final SendMessageUsecase sendMessageUsecase;

    public MessageController(SendMessageUsecase sendMessageUsecase) {
        this.sendMessageUsecase = sendMessageUsecase;
    }

    @Operation(summary = "Upload and index a document", description = "Uploads a file and indexes its content for later retrieval")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document successfully indexed",
                content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error occurred during document indexing",
                content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/chat")
    public ResponseEntity<String> addDocument(
            @Parameter(
                    description = "message",
                    required = true
            )
            @RequestParam("message") String message) {
        try {
            Response response = sendMessageUsecase.execute(message);
            return ResponseEntity.ok(response.getContent());
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
