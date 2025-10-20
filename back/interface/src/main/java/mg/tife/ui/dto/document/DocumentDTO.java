package mg.tife.ui.dto.document;

import java.util.UUID;

public record DocumentDTO(UUID indexName,String contentType,String fileName,String description){}