package mg.tife.ui.mapper;

import mg.tife.domain.domain.Conversation;
import mg.tife.ui.dto.ConversationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConversationUIMapper {
    ConversationUIMapper INSTANCE = Mappers.getMapper( ConversationUIMapper.class );
    Conversation dtoToDomain(ConversationDTO dto);
    ConversationDTO domainToDto(Conversation message);
}