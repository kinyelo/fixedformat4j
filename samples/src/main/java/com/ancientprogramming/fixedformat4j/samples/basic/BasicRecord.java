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
package com.ancientprogramming.fixedformat4j.samples.basic;

import com.ancientprogramming.fixedformat4j.annotation.Record;
import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Align;
import com.ancientprogramming.fixedformat4j.annotation.FixedFormatPattern;
import com.ancientprogramming.fixedformat4j.format.EnumLookup;
import lombok.Data;

import java.util.Date;

/**
 * A record containing some simple datatypes to show basic parsing and formatting.
 *
 * @author Jacob von Eyben - http://www.ancientprogramming.com
 * @since 1.2.0
 */
//START-SNIPPET: basicrecord
@Record
@Data
public class BasicRecord {

  public enum Type {
    ONE, TWO
  }

  public enum ComplexType implements EnumLookup {

    ALPHA("1236", "Alpha type"),
    BETA("456", "Beta type");

    private String code, label;

    ComplexType(String code, String label) {
      this.code = code;
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
    @Override
    public String getCode() {
      return code;
    }
  }

  @Field(offset = 1, length = 10)
  private String stringData;

  @Field(offset = 11, length = 5, align = Align.RIGHT, paddingChar = '0')
  private Integer integerData;

  @Field(offset = 16, length = 10)
  @FixedFormatPattern("yyyy-MM-dd")
  private Date dateData;

  @Field(offset = 26, length = 3)
  private Type type;

  @Field(offset = 29, length = 4)
  private ComplexType complexType;

}
//END-SNIPPET: basicrecord
