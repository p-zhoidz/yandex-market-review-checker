package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(PosterService.class);

    @Inject
    private PosterRepository posterRepository;

    @Inject
    private PosterMapper posterMapper;

    /**
     * Save a poster.
     *
     * @param posterDTO the entity to save
     * @return the persisted entity
     */
    public PosterDTO save(PosterDTO posterDTO) {
        log.debug("Request to save Poster : {}", posterDTO);
        Poster poster = posterMapper.posterDTOToPoster(posterDTO);
        poster = posterRepository.save(poster);
        PosterDTO result = posterMapper.posterToPosterDTO(poster);
        return result;
    }

    /**
     * Get all the posters.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PosterDTO> findAll() {
        log.debug("Request to get all Posters");
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
        log.debug("Request to get Poster : {}", id);
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
        log.debug("Request to delete Poster : {}", id);
        posterRepository.delete(id);
    }
}
