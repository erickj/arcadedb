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
/* Generated By:JJTree: Do not edit this line. OArrayNumberSelector.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class ArrayNumberSelector extends SimpleNode {
  InputParameter inputValue;
  MathExpression expressionValue;
  Integer        integer;

  public ArrayNumberSelector(final int id) {
    super(id);
  }

  public ArrayNumberSelector(final SqlParser p, final int id) {
    super(p, id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (inputValue != null) {
      inputValue.toString(params, builder);
    } else if (expressionValue != null) {
      expressionValue.toString(params, builder);
    } else if (integer != null) {
      builder.append(integer);
    }
  }

  public Integer getValue(final Identifiable iCurrentRecord, final Object iResult, final CommandContext ctx) {
    Object result = null;
    if (inputValue != null) {
      result = inputValue.getValue(ctx.getInputParameters());
    } else if (expressionValue != null) {
      result = expressionValue.execute(iCurrentRecord, ctx);
    } else if (integer != null) {
      result = integer;
    }

    if (result == null) {
      return null;
    }
    if (result instanceof Number) {
      return ((Number) result).intValue();
    }
    return null;
  }

  public Integer getValue(final Result iCurrentRecord, final Object iResult, final CommandContext ctx) {
    Object result = null;
    if (inputValue != null) {
      result = inputValue.getValue(ctx.getInputParameters());
    } else if (expressionValue != null) {
      result = expressionValue.execute(iCurrentRecord, ctx);
    } else if (integer != null) {
      result = integer;
    }

    if (result == null) {
      return null;
    }
    if (result instanceof Number) {
      return ((Number) result).intValue();
    }
    return null;
  }

  public boolean needsAliases(final Set<String> aliases) {
    if (expressionValue != null) {
      return expressionValue.needsAliases(aliases);
    }
    return false;
  }

  public ArrayNumberSelector copy() {
    final ArrayNumberSelector result = new ArrayNumberSelector(-1);
    result.inputValue = inputValue == null ? null : inputValue.copy();
    result.expressionValue = expressionValue == null ? null : expressionValue.copy();
    result.integer = integer;
    return result;
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    if (expressionValue != null) {
      expressionValue.extractSubQueries(collector);
    }
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { expressionValue };
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    if (inputValue != null) {
      result.setProperty("inputValue", inputValue.serialize());
    }
    if (expressionValue != null) {
      result.setProperty("expressionValue", expressionValue.serialize());
    }
    result.setProperty("integer", integer);
    return result;
  }

  public void deserialize(final Result fromResult) {
    if (fromResult.getProperty("inputValue") != null) {
      inputValue = InputParameter.deserializeFromOResult(fromResult.getProperty("inputValue"));
    }
    if (fromResult.getProperty("toSelector") != null) {
      expressionValue = new MathExpression(-1);
      expressionValue.deserialize(fromResult.getProperty("expressionValue"));
    }
    integer = fromResult.getProperty("integer");
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { inputValue, expressionValue, integer };
  }

}
/* JavaCC - OriginalChecksum=5b2e495391ede3ccdc6c25aa63c8e591 (do not edit this line) */
