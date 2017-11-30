package com.codecool.jlamas.models.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactTest {

    String name;
    String description;
    Integer price;
    private Artifact artifact;


    @BeforeEach
    void setUp() {
        this.artifact = new Artifact("artifact", 100, "artifact description", "Single");
    }

    @Test
    void testGetName() {
        assertEquals("artifact", artifact.getName());
    }

    @Test
    void testGetPrice() {
        Integer localReward = 100;
        assertEquals(localReward, artifact.getPrice());
    }

    @Test
    void testGetDescription() {
        assertEquals("artifact description", artifact.getDescription());
    }

    @Test
    void testSetName() {
        artifact.setName("New name");
        assertEquals("New name", artifact.getName());
    }

    @Test
    void testSetPrice() {
        Integer localPrice = 200;
        artifact.setPrice(localPrice);
        assertEquals(localPrice, artifact.getPrice());
    }

    @Test
    void testSetDescription() {
        artifact.setDescription("New description");
        assertEquals("New description", artifact.getDescription());
    }

    @Test
    void testToString() {
        String name = artifact.getName();
        String description = artifact.getDescription();
        Integer price = artifact.getPrice();
        String info = String.format("%s  |  %s  |  %s\n================================\n", name, description, price);
        assertEquals(info, artifact.toString());
    }

}