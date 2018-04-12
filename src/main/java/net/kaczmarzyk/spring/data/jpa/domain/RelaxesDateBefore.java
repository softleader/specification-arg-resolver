package net.kaczmarzyk.spring.data.jpa.domain;


import net.kaczmarzyk.spring.data.jpa.domain.PathSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Thomas Chen on 2018/3/19.
 */
public class RelaxesDateBefore<T> extends PathSpecification<T> {

    private LocalDate before;

    public RelaxesDateBefore(String path, String[] params) throws ParseException {
        super(path);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (params != null && params.length != 0) {
            before = LocalDate.parse(params[0], formatter);
        }
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.and(cb.greaterThanOrEqualTo(this.path(root), before));
    }
}
