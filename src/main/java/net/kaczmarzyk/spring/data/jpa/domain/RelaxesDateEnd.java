package net.kaczmarzyk.spring.data.jpa.domain;

import net.kaczmarzyk.spring.data.jpa.domain.PathSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Tim.Liu on 2018/2/27.
 * 放寬查詢起迄的區段
 * after  : 到當日的23:59:59
 */
public class RelaxesDateEnd<T> extends PathSpecification<T> {

    private LocalDateTime after;

    public RelaxesDateEnd(String path, String[] args) {
        super(path);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if(args.length != 0) {
            after = LocalDateTime.of(LocalDate.parse(args[0], formatter), LocalTime.MAX);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(this.path(root), after);
    }
}
