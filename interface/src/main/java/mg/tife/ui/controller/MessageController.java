package mg.tife.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.exception.ElementNotFundException;
import mg.tife.domain.usecase.message.GetMessagesUsecase;
import mg.tife.domain.usecase.message.SendMessageUsecase;
import mg.tife.ui.dto.MessageDTO;
import mg.tife.ui.dto.ResponseDTO;
import mg.tife.ui.mapper.MessageUIMapper;
import mg.tife.ui.mapper.ResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@Tag(name = "messages Management", description = "API for managing messages")
public class MessageController {
    private final SendMessageUsecase sendMessageUsecase;
    private final GetMessagesUsecase getMessagesUsecase;

    public MessageController(SendMessageUsecase sendMessageUsecase
    , GetMessagesUsecase getMessagesUsecase) {
        this.sendMessageUsecase = sendMessageUsecase;
        this.getMessagesUsecase = getMessagesUsecase;
    }

    @Operation(summary = "Envoyer un message", description = "Envoyer un message à la conversation")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "message successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error sending message",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @PostMapping(value = "/chat")
    public ResponseEntity<ResponseDTO> chat(
            @Parameter(
                    description = "message",
                    required = true
            )
            @RequestParam("documentIndex") UUID documentIndex,
            @RequestParam("message") String message,
            @RequestParam("conversationID") UUID convID) throws ElementNotFundException {
            System.out.println("convID: " + convID);
            Response response = sendMessageUsecase.execute(documentIndex.toString(),message,convID);
            return ResponseEntity.ok(ResponseMapper.INSTANCE.domainToDto(response));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MessageDTO>> all(
            @Parameter(
                    description = "conversationID",
                    required = true
            )
            @RequestParam("conversationID") UUID convID
    ) throws ElementNotFundException {
        List<Message> messages = getMessagesUsecase.execute(convID);
        return ResponseEntity.ok(messages.stream().map(MessageUIMapper.INSTANCE::domainToDto).toList());
    }
}
