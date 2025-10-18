package mg.tife.infrastructure.mapper;

import mg.tife.domain.domain.Response;
import mg.tife.infrastructure.entity.ResponseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface ResponseMapper {
    ResponseMapper INSTANCE = Mappers.getMapper( ResponseMapper.class );

    @Mapping(target = "id", source = "id")
    ResponseEntity messageToEntity(Response message);

    @Mapping(target = "id", source = "id")
    Response entityToMessage(ResponseEntity respEntity);
}