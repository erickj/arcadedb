/*
 * Copyright © 2021-present Arcade Data Ltd (info@arcadedata.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-FileCopyrightText: 2021-present Arcade Data Ltd (info@arcadedata.com)
 * SPDX-License-Identifier: Apache-2.0
 */
/* Generated By:JJTree: Do not edit this line. OProjectionItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Document;
import com.arcadedb.database.Identifiable;
import com.arcadedb.database.Record;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.AggregationContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InternalResultSet;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class ProjectionItem extends SimpleNode {
  protected boolean          exclude = false;
  protected boolean          all     = false;
  protected Identifier       alias;
  protected Expression       expression;
  protected Boolean          aggregate;
  protected NestedProjection nestedProjection;

  public ProjectionItem(final Expression expression, final Identifier alias, final NestedProjection nestedProjection) {
    super(-1);
    this.expression = expression;
    this.alias = alias;
    this.nestedProjection = nestedProjection;
  }

  public ProjectionItem(final int id) {
    super(id);
  }

  public ProjectionItem(final SqlParser p, final int id) {
    super(p, id);
  }

  public boolean isAll() {
    if (all) {
      return true;
    }
    return expression != null && "*".equals(expression.toString());
  }

  public void setAll(final boolean all) {
    this.all = all;
  }

  public Identifier getAlias() {
    return alias;
  }

  public void setAlias(final Identifier alias) {
    this.alias = alias;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(final Expression expression) {
    this.expression = expression;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (all) {
      builder.append("*");
    } else {
      if (exclude) {
        builder.append("!");
      }
      if (expression != null) {
        expression.toString(params, builder);
      }
      if (nestedProjection != null) {
        builder.append(" ");
        nestedProjection.toString(params, builder);
      }
      if (alias != null) {

        builder.append(" AS ");
        alias.toString(params, builder);
      }
    }
  }

  public Object execute(final Record iCurrentRecord, final CommandContext ctx) {
    Object result;
    if (all) {
      result = iCurrentRecord;
    } else {
      result = expression.execute(iCurrentRecord, ctx);
    }
    if (nestedProjection != null) {
      result = nestedProjection.apply(expression, result, ctx);
    }
    return convert(result);
  }

  public Object convert(Object value) {
    if (value instanceof InternalResultSet) {
      ((InternalResultSet) value).reset();
      value = ((InternalResultSet) value).stream().collect(Collectors.toList());
    }
    if (value instanceof Iterator && !(value instanceof Identifiable)) {
      final Iterator iter = (Iterator) value;
      value = new ArrayList<>();
      while (iter.hasNext()) {
        ((List) value).add(iter.next());
      }
    }

    return value;
  }

  public Object execute(final Result iCurrentRecord, final CommandContext ctx) {
    Object result;
    if (all) {
      result = iCurrentRecord;
    } else {
      result = expression.execute(iCurrentRecord, ctx);
    }
    if (nestedProjection != null) {
      if (result instanceof Document && ((Document) result).getPropertyNames().isEmpty()) {
        ((Document) result).reload();
      }
      result = nestedProjection.apply(expression, result, ctx);
    }
    return convert(result);
  }

  /**
   * returns the final alias for this projection item (the explicit alias, if defined, or the default alias)
   *
   * @return the final alias for this projection item
   */
  public String getProjectionAliasAsString() {
    return getProjectionAlias().getStringValue();
  }

  public Identifier getProjectionAlias() {
    if (alias != null) {
      return alias;
    }
    final Identifier result;
    if (all) {
      result = new Identifier("*");
    } else {
      result = new Identifier(expression.toString());
    }
    return result;
  }

  public boolean isExpand() {
    return expression.isExpand();
  }

  public ProjectionItem getExpandContent() {
    final ProjectionItem result = new ProjectionItem(-1);
    result.setExpression(expression.getExpandContent());
    return result;
  }

  public boolean isAggregate() {
    if (aggregate != null) {
      return aggregate;
    }
    if (all) {
      aggregate = false;
      return false;
    }
    if (expression.isAggregate()) {
      aggregate = true;
      return true;
    }
    aggregate = false;
    return false;
  }

  /**
   * INTERNAL USE ONLY this has to be invoked ONLY if the item is aggregate!!!
   *
   * @param aggregateSplit
   */
  public ProjectionItem splitForAggregation(final AggregateProjectionSplit aggregateSplit, final CommandContext ctx) {
    if (isAggregate()) {
      final ProjectionItem result = new ProjectionItem(-1);
      result.alias = getProjectionAlias();
      result.expression = expression.splitForAggregation(aggregateSplit, ctx);
      result.nestedProjection = nestedProjection;
      return result;
    } else {
      return this;
    }
  }

  public AggregationContext getAggregationContext(final CommandContext ctx) {
    if (expression == null) {
      throw new CommandExecutionException("Cannot aggregate on this projection: " + this);
    }
    return expression.getAggregationContext(ctx);
  }

  public ProjectionItem copy() {
    final ProjectionItem result = new ProjectionItem(-1);
    result.exclude = this.exclude;
    result.all = all;
    result.alias = alias == null ? null : alias.copy();
    result.expression = expression == null ? null : expression.copy();
    result.nestedProjection = nestedProjection == null ? null : nestedProjection.copy();
    result.aggregate = aggregate;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final ProjectionItem that = (ProjectionItem) o;
    return exclude == that.exclude && all == that.all && Objects.equals(alias, that.alias) && Objects.equals(expression, that.expression) && Objects.equals(
        aggregate, that.aggregate) && Objects.equals(nestedProjection, that.nestedProjection);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exclude, all, alias, expression, aggregate, nestedProjection);
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    if (expression != null) {
      expression.extractSubQueries(collector);
    }
  }

  public boolean refersToParent() {
    if (expression != null) {
      return expression.refersToParent();
    }
    return false;
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    result.setProperty("all", all);
    if (alias != null) {
      result.setProperty("alias", alias.serialize());
    }
    if (expression != null) {
      result.setProperty("expression", expression.serialize());
    }
    result.setProperty("aggregate", aggregate);
    if (nestedProjection != null) {
      result.setProperty("nestedProjection", nestedProjection.serialize());
    }
    result.setProperty("exclude", exclude);
    return result;
  }

  public void deserialize(final Result fromResult) {
    all = fromResult.getProperty("all");
    if (fromResult.getProperty("alias") != null) {
      alias = Identifier.deserialize(fromResult.getProperty("alias"));
    }
    if (fromResult.getProperty("expression") != null) {
      expression = new Expression(-1);
      expression.deserialize(fromResult.getProperty("expression"));
    }
    aggregate = fromResult.getProperty("aggregate");
    if (fromResult.getProperty("nestedProjection") != null) {
      nestedProjection = new NestedProjection(-1);
      nestedProjection.deserialize(fromResult.getProperty("nestedProjection"));
    }
    if (Boolean.TRUE.equals(fromResult.getProperty("exclude"))) {
      exclude = true;
    }

  }

  public void setNestedProjection(final NestedProjection nestedProjection) {
    this.nestedProjection = nestedProjection;
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { expression };
  }
}
/* JavaCC - OriginalChecksum=6d6010734c7434a6f516e2eac308e9ce (do not edit this line) */
