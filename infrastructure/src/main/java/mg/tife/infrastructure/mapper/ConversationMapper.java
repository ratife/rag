package mg.tife.infrastructure.mapper;

import mg.tife.domain.domain.Conversation;
import mg.tife.infrastructure.entity.ConversationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConversationMapper {

    ConversationMapper INSTANCE = Mappers.getMapper( ConversationMapper.class );

    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "id", source = "id")
    ConversationEntity objToEntity(Conversation conversation);

    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "id", source = "id")
    Conversation entityToObj(ConversationEntity msgEntity);
}