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

import com.ancientprogramming.fixedformat4j.exception.FixedFormatException;
import com.ancientprogramming.fixedformat4j.format.FixedFormatter;
import com.ancientprogramming.fixedformat4j.format.FormatContext;
import com.ancientprogramming.fixedformat4j.format.FormatInstructions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Formatter capable of formatting a bunch of known java standard library classes. So far:
 * {@link String}, {@link Integer}, {@link Short}, {@link Long}, {@link Date},
 * {@link Character}, {@link Boolean}, {@link Double}, {@link Float} and {@link BigDecimal}
 *
 *
 * @author Jacob von Eyben - http://www.ancientprogramming.com
 * @since 1.0.0
 */
public class ByTypeFormatter implements FixedFormatter<Object> {
  private FormatContext context;

  private static final Map<Class<?>, FixedFormatter> KNOWN_FORMATTERS = new HashMap<>();

  static {
    KNOWN_FORMATTERS.put(String.class, new StringFormatter());
    KNOWN_FORMATTERS.put(short.class, new ShortFormatter());
    KNOWN_FORMATTERS.put(Short.class, new ShortFormatter());
    KNOWN_FORMATTERS.put(int.class, new IntegerFormatter());
    KNOWN_FORMATTERS.put(Integer.class, new IntegerFormatter());
    KNOWN_FORMATTERS.put(long.class, new LongFormatter());
    KNOWN_FORMATTERS.put(Long.class, new LongFormatter());
    KNOWN_FORMATTERS.put(Date.class, new DateFormatter());
    KNOWN_FORMATTERS.put(char.class, new CharacterFormatter());
    KNOWN_FORMATTERS.put(Character.class, new CharacterFormatter());
    KNOWN_FORMATTERS.put(boolean.class, new BooleanFormatter());
    KNOWN_FORMATTERS.put(Boolean.class, new BooleanFormatter());
    KNOWN_FORMATTERS.put(double.class, new DoubleFormatter());
    KNOWN_FORMATTERS.put(Double.class, new DoubleFormatter());
    KNOWN_FORMATTERS.put(float.class, new FloatFormatter());
    KNOWN_FORMATTERS.put(Float.class, new FloatFormatter());
    KNOWN_FORMATTERS.put(BigDecimal.class, new BigDecimalFormatter());
  }

  public static void register(Class<?> cl, FixedFormatter formatter) {
    KNOWN_FORMATTERS.put(cl, formatter);
  }

  public static void registerEnum(Class<?> cl) {
    KNOWN_FORMATTERS.put(cl, new EnumFormatter(cl));
  }

  public ByTypeFormatter(FormatContext context) {
    this.context = context;
  }


  public Object parse(String value, FormatInstructions instructions) {
    FixedFormatter formatter = actualFormatter(context.getDataType());
    return formatter.parse(value, instructions);
  }

  public String format(Object value, FormatInstructions instructions) {
    FixedFormatter formatter = actualFormatter(context.getDataType());
    return formatter.format(value, instructions);
  }

  public FixedFormatter actualFormatter(final Class<? extends Object> dataType) {
    if (!KNOWN_FORMATTERS.containsKey(dataType)) {
      throw new FixedFormatException("Could not find formatter for " + dataType.getName());
    }
    return KNOWN_FORMATTERS.get(dataType);
  }
}
