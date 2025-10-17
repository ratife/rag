package mg.tife.ui.mapper;

import mg.tife.domain.domain.Document;
import mg.tife.ui.dto.document.DocumentDTO;
import mg.tife.ui.dto.document.DocumentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.util.UUID;

@Mapper
public interface DocumentUIMapper {
    DocumentUIMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper( DocumentUIMapper.class );

    @Mapping(target = "fileName", expression = "java(dto.getFile().getOriginalFilename())")
    @Mapping(target = "contentType", expression = "java(dto.getFile().getContentType())")
    @Mapping(target = "contents", expression = "java(dto.getFile().getBytes())")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "indexName", expression = "java(UUID.randomUUID())")
    Document dtoToDomain(DocumentRequest dto) throws IOException;

    DocumentDTO domainToDto(Document document);
}
