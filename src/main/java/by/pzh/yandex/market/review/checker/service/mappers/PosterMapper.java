package by.pzh.yandex.market.review.checker.service.mappers;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Poster and its DTO PosterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PosterMapper {

    PosterDTO posterToPosterDTO(Poster poster);

    List<PosterDTO> postersToPosterDTOs(List<Poster> posters);

    Poster posterDTOToPoster(PosterDTO posterDTO);

    List<Poster> posterDTOsToPosters(List<PosterDTO> posterDTOs);
}
