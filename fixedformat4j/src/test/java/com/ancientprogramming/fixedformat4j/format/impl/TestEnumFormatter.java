package com.ancientprogramming.fixedformat4j.format.impl;

import com.ancientprogramming.fixedformat4j.annotation.Align;
import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Record;
import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import org.junit.Assert;
import org.junit.Test;

public class TestEnumFormatter {


    public enum Type {
        ONE, TWO
    }

    public enum VarSized {
        SHORT, LONG
    }

    public enum Shape {
        LINE, CIRCLE, BOX
    }

    @Record
    public static class Person {
        @Field(offset = 1, length = 10)
        private String name;

        @Field(offset = 11, length = 3)
        private Type type;

        @Field(offset = 14, length = 5)
        private VarSized varSized;

        @Field(offset = 19, length = 10, align = Align.RIGHT, paddingChar = '=')
        private Shape shape;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public VarSized getVarSized() {
            return varSized;
        }

        public void setVarSized(VarSized varSized) {
            this.varSized = varSized;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }
    }

    @Test
    public void testSimpleEnum() {
        FixedFormatManager manager = new FixedFormatManagerImpl();
        ByTypeFormatter.registerEnum(Type.class);
        ByTypeFormatter.registerEnum(VarSized.class);
        ByTypeFormatter.registerEnum(Shape.class);

        var s = "Anton     ONELONG =======BOX";
        Person record = manager.load(Person.class, s);

        Assert.assertEquals("Anton", record.getName());
        Assert.assertEquals(Type.ONE, record.getType());
        Assert.assertEquals(VarSized.LONG, record.getVarSized());
        Assert.assertEquals(Shape.BOX, record.getShape());

        record.setName("Alex");
        record.setType(Type.TWO);
        record.setVarSized(VarSized.SHORT);
        record.setShape(Shape.CIRCLE);

        s = manager.export(record);

        Assert.assertEquals("Alex      TWOSHORT====CIRCLE", s);
    }


}
