/*
 * Copyright 2021 Arcade Data Ltd
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.arcadedb.exporter.format;

import com.arcadedb.database.DatabaseInternal;
import com.arcadedb.exporter.ExporterContext;
import com.arcadedb.exporter.ExporterSettings;
import com.arcadedb.importer.ConsoleLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class AbstractExporter {
  protected final        ExporterSettings settings;
  protected final        ExporterContext  context;
  protected final        DatabaseInternal database;
  protected final        ConsoleLogger    logger;
  protected static final DateFormat       dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  protected AbstractExporter(final DatabaseInternal database, final ExporterSettings settings, final ExporterContext context, final ConsoleLogger logger) {
    this.database = database;
    this.settings = settings;
    this.context = context;
    this.logger = logger;
  }

  public abstract void exportDatabase() throws Exception;
}
