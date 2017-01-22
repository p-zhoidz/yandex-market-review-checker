package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Poster.
 */
@Service
@Transactional
public class PosterService {
    private PosterRepository posterRepository;
    private PosterMapper posterMapper;

    /**
     * Parametrized constructor.
     *
     * @param posterRepository poster repository instance.
     * @param posterMapper     poster mapper instance.
     */
    @Inject
    public PosterService(PosterRepository posterRepository, PosterMapper posterMapper) {
        this.posterRepository = posterRepository;
        this.posterMapper = posterMapper;
    }

    /**
     * Update a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public Poster update(Long id, PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToPoster(id, posterDTO);
        return posterRepository.save(poster);
    }

    /**
     * Create a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public Poster create(PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToNewPoster(posterDTO);
        return posterRepository.save(poster);
    }

    @Transactional(readOnly = true)
    public Page<Poster> getPosters(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return posterRepository.findAll(pageable);
    }

    /**
     * Get one poster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Poster findOne(Long id) {
        return posterRepository.findOne(id);
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
