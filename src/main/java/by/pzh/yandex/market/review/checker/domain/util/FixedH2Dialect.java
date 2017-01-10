package by.pzh.yandex.market.review.checker.domain.util;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

/**
 * Class to fix H@ dialect.
 */
public class FixedH2Dialect extends H2Dialect {

    /**
     * Default constructor.
     */
    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }
}
