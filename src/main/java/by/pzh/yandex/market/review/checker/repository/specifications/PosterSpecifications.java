package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Poster_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author p.zhoidz.
 */
public final class PosterSpecifications {

    public static Specification<Poster> filterActive() {
        return (root, query, builder) ->
                builder.isTrue(root.get(Poster_.active));
    }
}
