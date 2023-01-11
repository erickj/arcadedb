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
/* Generated By:JJTree: Do not edit this line. OClusterList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;
import java.util.stream.*;

public class BucketList extends SimpleNode {

  protected List<Identifier> buckets = new ArrayList<>();

  public BucketList(final int id) {
    super(id);
  }

  public BucketList(final SqlParser p, final int id) {
    super(p, id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("bucket:[");
    boolean first = true;
    for (final Identifier id : buckets) {
      if (!first) {
        builder.append(",");
      }
      id.toString(params, builder);
      first = false;
    }
    builder.append("]");
  }

  public List<Bucket> toListOfClusters() {
    final List<Bucket> result = new ArrayList<>();
    for (final Identifier id : buckets) {
      final Bucket bucket = new Bucket(-1);
      bucket.bucketName = id.getStringValue();
      result.add(bucket);
    }
    return result;
  }

  public BucketList copy() {
    final BucketList result = new BucketList(-1);
    result.buckets = buckets.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { buckets };
  }

  public Result serialize() {
    final ResultInternal result = new ResultInternal();
    if (buckets != null) {
      result.setProperty("buckets", buckets.stream().map(x -> x.serialize()).collect(Collectors.toList()));
    }
    return result;
  }

  public void deserialize(final Result fromResult) {
    if (fromResult.getProperty("buckets") != null) {
      buckets = new ArrayList<>();
      final List<Result> ser = fromResult.getProperty("buckets");
      for (final Result item : ser) {
        final Identifier id = new Identifier(-1);
        Identifier.deserialize(item);
        buckets.add(id);
      }
    }
  }
}
/* JavaCC - OriginalChecksum=bd90ffa0b9d17f204b3cf2d47eedb409 (do not edit this line) */
