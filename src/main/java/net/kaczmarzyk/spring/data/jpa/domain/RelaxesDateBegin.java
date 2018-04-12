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
 * before : 到當日的0點
 */
public class RelaxesDateBegin<T> extends PathSpecification<T> {
    private LocalDateTime begin;

    public RelaxesDateBegin(String path, String[] args) {
        super(path);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if(args.length != 0) {
            begin = LocalDateTime.of(LocalDate.parse(args[0], formatter), LocalTime.MIN);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(this.path(root), begin);
    }
}
