package mg.tife.ui.mapper;

import mg.tife.domain.domain.Response;
import mg.tife.ui.dto.ResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseMapper {

    ResponseMapper INSTANCE = Mappers.getMapper( ResponseMapper.class );

    Response dtoToDomain(ResponseDTO dto);
    ResponseDTO domainToDto(Response response);
}
