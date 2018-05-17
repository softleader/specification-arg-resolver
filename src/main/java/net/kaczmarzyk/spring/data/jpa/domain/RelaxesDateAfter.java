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
import java.time.format.DateTimeFormatter;

/** Created by Thomas Chen on 2018/3/19. */
public class RelaxesDateAfter<T> extends PathSpecification<T> {

  private LocalDate after;

  public RelaxesDateAfter(String path, String[] params) {
    super(path);
    if (params != null && params.length != 0) {
      after = LocalDate.parse(params[0], DateTimeFormatter.ISO_LOCAL_DATE);
    }
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    return cb.and(cb.lessThanOrEqualTo(this.path(root), after));
  }
}
