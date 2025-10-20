package mg.tife.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tife.domain.domain.Conversation;
import mg.tife.domain.usecase.conversation.CreateConversationUsecase;
import mg.tife.domain.usecase.conversation.GetListConversationUsecase;
import mg.tife.ui.dto.ConversationDTO;
import mg.tife.ui.mapper.ConversationUIMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@Tag(name = "Conversation Management", description = "API for managing Conversation")
public class ConversationController {
    private final CreateConversationUsecase createConversationUsecase;
    private final GetListConversationUsecase getListConversationUsecase;

    public ConversationController(CreateConversationUsecase createConversationUsecase,
                                  GetListConversationUsecase getListConversationUsecase) {
        this.createConversationUsecase = createConversationUsecase;
        this.getListConversationUsecase = getListConversationUsecase;
    }

    @Operation(summary = "Création d'une conversation", description = "Création d'une conversation avec son titre")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "conversation successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error creation conversation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<ConversationDTO> createConversation(
            @Parameter(
                    description = "Title of the conversation",
                    required = true
            )
            @RequestParam("title") String title
    ) {
        Conversation conversation = createConversationUsecase.execute(title);
        ConversationDTO response = ConversationUIMapper.INSTANCE.domainToDto(conversation);
        System.out.println("response: " + response.toString());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Liste de conversation", description = "liste de conversation")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "conversation successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error creation conversation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List> all() {
        List<Conversation> convs = getListConversationUsecase.execute();
        return ResponseEntity.ok(convs.stream().map(e-> ConversationUIMapper.INSTANCE.domainToDto(e)).toList());
    }
}
