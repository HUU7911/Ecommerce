package com.huumod.ecommerce.search.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneQuery {

    @NonFinal
    static String[] SEARCH_FIELDS = {
            "name^3",
            "name.folded^2",
            "categories.name",
            "description"
    };

    public Query build(String keyword, String category, BigDecimal price) {
        List<Query> filters = new ArrayList<>();

        categoryFilter(filters, category);
        priceFilter(filters, price);

        return Query.of(query-> query
                .bool(bool -> bool
                        .must(queryKeyWord(keyword))
                        .filter(filters)));
    }

    private Query queryKeyWord(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Query.of(query ->
                    query.matchAll(match -> match));
        }

        return Query.of(queryKeyWord ->
                queryKeyWord.multiMatch(multiMatch ->
                        multiMatch.query(keyword.trim())
                                .fields(List.of(SEARCH_FIELDS))
                                .operator(Operator.And)));
    }

    private void categoryFilter(List<Query> queries, String category) {
        if (!StringUtils.hasText(category))
            return;

        queries.add(Query.of(query -> query.term(term ->
                term.field("categories.name.raw").value(category.trim()))));
    }

    private void priceFilter(List<Query> queries, BigDecimal price) {
         if (price == null)
             return;

         queries.add(Query.of(query -> query.range(range -> range.number(number -> {
             number.field("price");
             number.gt(price.doubleValue());
             return number;
         }))));
    }
}
