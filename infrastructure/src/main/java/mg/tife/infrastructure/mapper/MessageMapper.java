package mg.tife.infrastructure.mapper;

import mg.tife.domain.domain.Message;
import mg.tife.infrastructure.entity.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageEntity messageToEntity(Message message);
    Message entityToMessage(MessageEntity msgEntity);
}