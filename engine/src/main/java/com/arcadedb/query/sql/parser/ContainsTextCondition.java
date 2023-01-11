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
/* Generated By:JJTree: Do not edit this line. OContainsTextCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;

import java.util.*;

@SuppressWarnings("ALL")
public class ContainsTextCondition extends BooleanExpression {

  protected Expression left;
  protected Expression right;

  public ContainsTextCondition(final int id) {
    super(id);
  }

  public ContainsTextCondition(final SqlParser p, int id) {
    super(p, id);
  }

  @Override
  public boolean evaluate(final Identifiable currentRecord, final CommandContext ctx) {
    final Object leftValue = left.execute(currentRecord, ctx);
    if (!(leftValue instanceof String))
      return false;

    final Object rightValue = right.execute(currentRecord, ctx);
    if (!(rightValue instanceof String))
      return false;

    return ((String) leftValue).contains((String) rightValue);
  }

  @Override
  public boolean evaluate(final Result currentRecord, final CommandContext ctx) {
    final Object leftValue = left.execute(currentRecord, ctx);
    if (!(leftValue instanceof String))
      return false;

    final Object rightValue = right.execute(currentRecord, ctx);
    if (!(rightValue instanceof String))
      return false;

    return ((String) leftValue).contains((String) rightValue);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    left.toString(params, builder);
    builder.append(" CONTAINSTEXT ");
    right.toString(params, builder);
  }

  @Override
  public boolean supportsBasicCalculation() {
    return true;
  }

  @Override
  protected int getNumberOfExternalCalculations() {
    int total = 0;
    if (!left.supportsBasicCalculation()) {
      total++;
    }
    if (!right.supportsBasicCalculation()) {
      total++;
    }
    return total;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    final List<Object> result = new ArrayList<Object>();
    if (!left.supportsBasicCalculation()) {
      result.add(left);
    }
    if (!right.supportsBasicCalculation()) {
      result.add(right);
    }
    return result;
  }

  @Override
  public boolean needsAliases(final Set<String> aliases) {
    if (!left.needsAliases(aliases)) {
      return true;
    }
    return !right.needsAliases(aliases);
  }

  @Override
  public ContainsTextCondition copy() {
    final ContainsTextCondition result = new ContainsTextCondition(-1);
    result.left = left.copy();
    result.right = right.copy();
    return result;
  }

  @Override
  public void extractSubQueries(final SubQueryCollector collector) {
    left.extractSubQueries(collector);
    right.extractSubQueries(collector);
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { left, right };
  }

  @Override
  public List<String> getMatchPatternInvolvedAliases() {
    final List<String> leftX = left == null ? null : left.getMatchPatternInvolvedAliases();
    final List<String> rightX = right == null ? null : right.getMatchPatternInvolvedAliases();

    final List<String> result = new ArrayList<String>();
    if (leftX != null) {
      result.addAll(leftX);
    }
    if (rightX != null) {
      result.addAll(rightX);
    }

    return result.isEmpty() ? null : result;
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { left, right };
  }

  public void setLeft(Expression left) {
    this.left = left;
  }

  public void setRight(Expression right) {
    this.right = right;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }
}
/* JavaCC - OriginalChecksum=b588492ba2cbd0f932055f1f64bbbecd (do not edit this line) */
