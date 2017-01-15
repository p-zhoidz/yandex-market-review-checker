package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    public PosterDTO update(PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToPoster(posterDTO);
        return save(poster);
    }

    /**
     * Create a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public PosterDTO create(PosterDTO posterDTO) {
        Poster poster = posterMapper.posterDTOToNewPoster(posterDTO);
        return save(poster);
    }

    /**
     * Get all the posters.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PosterDTO> findAll() {
        List<PosterDTO> result = posterRepository.findAll().stream()
                .map(posterMapper::posterToPosterDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one poster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PosterDTO findOne(Long id) {
        Poster poster = posterRepository.findOne(id);
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);
        return posterDTO;
    }

    /**
     * Delete the  poster by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        posterRepository.delete(id);
    }

    /**
     * Save a poster.
     *
     * @param poster the entity to save
     * @return the persisted entity
     */
    private PosterDTO save(Poster poster) {
        poster = posterRepository.save(poster);
        return posterMapper.posterToPosterDTO(poster);
    }
}
