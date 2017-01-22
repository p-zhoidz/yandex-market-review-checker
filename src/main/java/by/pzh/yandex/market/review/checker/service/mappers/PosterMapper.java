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

    /**
     * Map {@link Poster} to {@link PosterDTO}.
     *
     * @param poster entity to be mapped.
     * @return instance of the {@link PosterDTO}.
     */
    PosterDTO posterToPosterDTO(Poster poster);

    /**
     * Map list of {@link Poster} to list of {@link PosterDTO}.
     *
     * @param posters list of entities to be mapped.
     * @return list of {@link PosterDTO}.
     */
    List<PosterDTO> postersToPosterDTOs(List<Poster> posters);

    /**
     * Map {@link PosterDTO} to {@link Poster}.
     *
     * @param posterDTO entity to be mapped.
     * @return instance of the {@link Poster}.
     */
    @Named("posterDTOToPoster")
    @Mapping(target = "id", expression = "java(id)")
    Poster posterDTOToPoster(Long id, PosterDTO posterDTO);


    /**
     * Map {@link PosterDTO} to {@link Poster}.
     * Skipping id.
     *
     * @param posterDTO entity to be mapped.
     * @return instance of the {@link Poster}.
     */
    @Mapping(target = "id", ignore = true)
    Poster posterDTOToNewPoster(PosterDTO posterDTO);

    /**
     * Map list of {@link PosterDTO} to list of {@link Poster}.
     *
     * @param posterDTOs list of entities to be mapped.
     * @return list of {@link Poster}.
     */
    @IterableMapping(qualifiedByName = "posterDTOToPoster")
    List<Poster> posterDTOsToPosters(List<PosterDTO> posterDTOs);
}