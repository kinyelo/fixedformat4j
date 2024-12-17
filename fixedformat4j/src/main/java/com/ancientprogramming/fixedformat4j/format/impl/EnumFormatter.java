/*
 * Copyright 2004 the original author or authors.
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
 */
package com.ancientprogramming.fixedformat4j.format.impl;

import com.ancientprogramming.fixedformat4j.annotation.Align;
import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.format.AbstractFixedFormatter;
import com.ancientprogramming.fixedformat4j.format.EnumLookup;
import com.ancientprogramming.fixedformat4j.format.FormatInstructions;
import org.apache.commons.lang.StringUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Formatter for {@link for enums} data
 *
 * @author Alex Oleynik
 * @since 1.4.1-AO
 */
public class EnumFormatter<T extends Enum> extends AbstractFixedFormatter<T> {

  private Class<T> eClass;
  private Map<String, T> map;
  private boolean caseSensitive, ignoreUnknown;

  public EnumFormatter(Class<T> eClass) {
    this(eClass, true, false);
  }

  public EnumFormatter(Class<T> eClass, boolean caseSensitive, boolean ignoreUnknown) {
    this.eClass = eClass;
    this.caseSensitive = caseSensitive;
    this.ignoreUnknown = ignoreUnknown;
    map = new HashMap<>();
    for (var e : eClass.getEnumConstants()) {
      var key = e instanceof EnumLookup l ? l.getCode() : e.name();
      if (!caseSensitive) {
        key = key.toUpperCase();
      }
      map.put(key, e);
    }
  }

  public T asObject(String string, FormatInstructions instructions) {
    if (string == null) return null;
    var key = caseSensitive ? string : string.toUpperCase();
    T r = map.get(key);
    if (r == null && !ignoreUnknown) {
      throw new IllegalArgumentException("Unknown enum item " + string);
    }
    return r;
  }

  public String asString(T e, FormatInstructions instructions) {
    if (e == null) return null;
    return e instanceof EnumLookup l ? l.getCode() : e.name();
  }
}
