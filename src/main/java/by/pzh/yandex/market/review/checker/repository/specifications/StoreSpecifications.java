package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.Client_;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Store_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

/**
 * @author p.zhoidz.
 */
public final class StoreSpecifications {

    private StoreSpecifications() {
    }


    public static Specification<Store> forOwner(long id) {
        return (root, query, builder) ->
                builder.equal(root.get(Store_.owner)
                                .get(Client_.id),
                        id);
    }

    public static Specification<Store> filterActive() {
        return (root, query, builder) ->
                builder.and(builder.isTrue(root.get(Store_.active)),
                        builder.isTrue(root.join(Store_.owner, JoinType.LEFT).get(Client_.active)));
    }

}
