package mg.tife.ui.dto;

import java.util.UUID;

public record MessageDTO(UUID id, String content, ResponseDTO response) {}
