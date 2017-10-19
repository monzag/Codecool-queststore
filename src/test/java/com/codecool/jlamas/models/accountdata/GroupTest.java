package com.codecool.jlamas.models.accountdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    private Group group;

    @BeforeEach
    void setUp() {
        this.group = new Group("group");
    }

    @Test
    void testEmptyConstructor() {
        new Group();
    }

    @Test
    void setName() {
        String name = "new name";
        group.setName( name );

        assertEquals(name, group.getName());
    }

    @Test
    void getName() {
        assertEquals("group", group.getName());
    }

    @Test
    void testToString() {

        assertEquals("group", group.toString());

    }

}