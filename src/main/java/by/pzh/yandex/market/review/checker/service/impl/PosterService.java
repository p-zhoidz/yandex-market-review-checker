package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.PosterResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.resources.PosterResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Poster.
 */
@Service
@Transactional
public class PosterService {
    private PosterRepository posterRepository;
    private PosterMapper posterMapper;
    private PosterResourceAssembler posterResourceAssembler;
    private PagedResourcesAssembler<Poster> pagedAssembler;

    /**
     * Parametrized constructor.
     *
     * @param posterRepository poster repository instance.
     * @param posterMapper     poster mapper instance.
     */
    @Inject
    public PosterService(PosterRepository posterRepository, PosterMapper posterMapper,
                         PosterResourceAssembler posterResourceAssembler,
                         PagedResourcesAssembler<Poster> pagedAssembler) {
        this.posterRepository = posterRepository;
        this.posterMapper = posterMapper;
        this.posterResourceAssembler = posterResourceAssembler;
        this.pagedAssembler = pagedAssembler;
    }

    /**
     * Update a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public PosterResource update(Long id, PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToPoster(id, posterDTO);
        posterRepository.save(poster);
        return posterResourceAssembler.toResource(poster);
    }

    /**
     * Create a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public PosterResource create(PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToNewPoster(posterDTO);
        posterRepository.save(poster);
        return posterResourceAssembler.toResource(poster);
    }

    @Transactional(readOnly = true)
    public PagedResources<PosterResource> getPosters(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Poster> posters = posterRepository.findAll(pageable);
        return pagedAssembler.toResource(posters, posterResourceAssembler);
    }

    /**
     * Get one poster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PosterResource> findOne(Long id) {
        return Optional.ofNullable(posterRepository.findOne(id))
                .map(poster ->
                        posterResourceAssembler.toResource(poster));
    }

    /**
     * Delete the  poster by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        posterRepository.delete(id);
    }

}
