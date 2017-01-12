package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for the entity Poster and its DTO PosterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PosterMapper {

    PosterDTO posterToPosterDTO(Poster poster);

    List<PosterDTO> postersToPosterDTOs(List<Poster> posters);

    @Named("posterDTOToPoster")
    Poster posterDTOToPoster(PosterDTO posterDTO);

    @Mapping(target = "id", ignore = true)
    Poster posterDTOToNewPoster(PosterDTO posterDTO);

    @IterableMapping(qualifiedByName = "posterDTOToPoster")
    List<Poster> posterDTOsToPosters(List<PosterDTO> posterDTOs);
}
