/**
 * Copyright 2014-2016 the original author or authors.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kaczmarzyk.spring.data.jpa.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Created by Tim.Liu on 2018/2/27. before : 到當日的0點 */
public class RelaxesTimeBegin<T> extends PathSpecification<T> {

  private LocalDateTime begin;

  public RelaxesTimeBegin(String path, String[] args) {
    super(path);
    if (args.length != 0) {
      begin =
          LocalDateTime.of(
              LocalDate.parse(args[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalTime.MIN);
    }
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    return cb.greaterThanOrEqualTo(this.path(root), begin);
  }
}
