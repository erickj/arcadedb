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
/* Generated by: JJTree: Do not edit this line. ExportDatabaseStatement.java Version 1.1 */
/* ParserGeneratorCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.exception.CommandExecutionException;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InternalResultSet;
import com.arcadedb.query.sql.executor.ResultInternal;
import com.arcadedb.query.sql.executor.ResultSet;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class ExportDatabaseStatement extends SimpleExecStatement {

  protected Url               url;
  protected Identifier        format    = new Identifier("jsonl");
  protected BooleanExpression overwrite = BooleanExpression.FALSE;

  public ExportDatabaseStatement(final int id) {
    super(id);
  }

  public ExportDatabaseStatement(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public ResultSet executeSimple(final CommandContext ctx) {
    final String targetUrl = this.url.getUrlString();
    final ResultInternal result = new ResultInternal();
    result.setProperty("operation", "export database");
    result.setProperty("toUrl", targetUrl);

    String fileName = targetUrl.startsWith("file://") ? targetUrl.substring("file://".length()) : targetUrl;
    if (fileName.contains("..") || fileName.contains(File.separator))
      throw new IllegalArgumentException("Export file cannot contain path change because the directory is specified");

    fileName = "exports" + File.separator + fileName;

    try {
      final Class<?> clazz = Class.forName("com.arcadedb.integration.exporter.Exporter");
      final Object exporter = clazz.getConstructor(Database.class, String.class).newInstance(ctx.getDatabase(), fileName);

      String formatExport = format.getStringValue();
      if ((formatExport.startsWith("'") && formatExport.endsWith("'")) ||//
          formatExport.startsWith("\"") && formatExport.endsWith("\"")) {
        formatExport = formatExport.substring(1, formatExport.length() - 1);
      }

      clazz.getMethod("setOverwrite", Boolean.TYPE).invoke(exporter, overwrite == BooleanExpression.TRUE);
      clazz.getMethod("setFormat", String.class).invoke(exporter, formatExport);
      clazz.getMethod("exportDatabase").invoke(exporter);

    } catch (final ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
      throw new CommandExecutionException("Error on exporting database, exporter libs not found in classpath", e);
    } catch (final InvocationTargetException e) {
      throw new CommandExecutionException("Error on exporting database", e.getTargetException());
    }

    result.setProperty("result", "OK");

    final InternalResultSet rs = new InternalResultSet();
    rs.add(result);
    return rs;
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("EXPORT DATABASE ");
    url.toString(params, builder);
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { url };
  }

  @Override
  public Statement copy() {
    final ExportDatabaseStatement result = new ExportDatabaseStatement(-1);
    result.url = this.url;
    return result;
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { url };
  }
}
/* ParserGeneratorCC - OriginalChecksum=7a41f26bd0c3d48aafcf45752ac28521 (do not edit this line) */
