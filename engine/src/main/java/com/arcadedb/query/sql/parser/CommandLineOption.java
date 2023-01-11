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
/* Generated By:JJTree: Do not edit this line. OCommandLineOption.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;

public class CommandLineOption extends SimpleNode {

  protected Identifier name;

  public CommandLineOption(final int id) {
    super(id);
  }

  public CommandLineOption(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("-");
    name.toString(params, builder);
  }

  public CommandLineOption copy() {
    final CommandLineOption result = new CommandLineOption(-1);
    result.name = name == null ? null : name.copy();
    return result;
  }

  @Override
  protected Object[] getIdentityElements() {
    return new Object[] { name };
  }
}
/* JavaCC - OriginalChecksum=7fcb8de8a1f99a2737aac85933d074d9 (do not edit this line) */
