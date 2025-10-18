package mg.tife.ui.mapper;

import mg.tife.domain.domain.Message;
import mg.tife.ui.dto.MessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageUIMapper {
    MessageUIMapper INSTANCE = Mappers.getMapper( MessageUIMapper.class );
    Message dtoToDomain(MessageDTO dto);

    @Mapping(target = "response", source = "response")
    MessageDTO domainToDto(Message message);
}
