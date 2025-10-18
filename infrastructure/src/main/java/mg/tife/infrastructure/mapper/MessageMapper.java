package mg.tife.infrastructure.mapper;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.domain.Message;
import mg.tife.infrastructure.entity.ConversationEntity;
import mg.tife.infrastructure.entity.MessageEntity;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper( MessageMapper.class );

    @Mapping(target = "id", source = "id")
    MessageEntity messageToEntity(Message message);

    @Mapping(target = "conversation", ignore = true)
    @Mapping(target = "id", source = "id")
    Message entityToMessage(MessageEntity msgEntity);
}