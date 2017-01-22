package by.pzh.yandex.market.review.checker.web.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author p.zhoidz.
 */
public class CustomBaseResourceSupport extends ResourceSupport {
    @JsonIgnore
    @Override
    public List<Link> getLinks() {
        return super.getLinks();
    }

    @JsonProperty("_links")
    public Map<String, String> getFlatLinks() {
        return getLinks()
                .stream()
                .collect(toMap(Link::getRel, Link::getHref));
    }
}
