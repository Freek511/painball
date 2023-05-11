package freek.anotheratempt.Mappers;

import freek.anotheratempt.DTO.CreatePgDto;
import freek.anotheratempt.DTO.ReadPgDto;
import freek.anotheratempt.Entities.PlayGround;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PlayGroundMapper {
    @Mapping(source = "name", target = "name")
    PlayGround createPgDtoToPg(CreatePgDto pgDto);

    ReadPgDto pgToReadpgDto(PlayGround playGround);
    default String map(List<String> field) {
        return String.join(", ", field);
    }

    @IterableMapping(elementTargetType = ReadPgDto.class)
    Iterable<ReadPgDto> arrayPgToArrayReadDto(Iterable<PlayGround> playGround);

}