package by.pzh.yandex.market.review.checker.domain.features;

/**
 * @author p.zhoidz.
 */
public interface Deletable {
    /**
     * Set 'deleted' flag.
     *
     * @return true if entity is deleted, false otherwise.
     */
    boolean isDeleted();

    /**
     * Update deleted flag.
     *
     * @param isDeleted true to mark entity deleted, false otherwise.
     */
    void setDeleted(boolean isDeleted);
}
