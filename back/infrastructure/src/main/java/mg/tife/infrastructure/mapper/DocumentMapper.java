package mg.tife.infrastructure.mapper;

import mg.tife.domain.domain.Document;
import mg.tife.domain.domain.Message;
import mg.tife.infrastructure.entity.DocumentEntity;
import mg.tife.infrastructure.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper( DocumentMapper.class );

    DocumentEntity domainToEntity(Document doc);

    Document entityToDomain(DocumentEntity entity);
}