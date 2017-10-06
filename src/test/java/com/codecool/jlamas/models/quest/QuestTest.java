package com.codecool.jlamas.models.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    private Quest quest;

    @BeforeEach
    void setUp(){
        this.quest = new Quest("Quest Name", "description", 20);
    }

    @Test
    void testGetName() {
        assertEquals("Quest Name", quest.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("description", quest.getDescription());
    }

    @Test
    void testGetReward() {
        Integer reward = 20;
        assertEquals(reward, quest.getReward());
    }

    @Test
    void testGetStatus() {
        assertEquals(false, quest.getStatus());
    }

    @Test
    void testSetName() {
        quest.setName("New name");
        assertEquals("New name", quest.getName());
    }

    @Test
    void testSetDescription() {
        quest.setDescription("New description");
        assertEquals("New description", quest.getDescription());
    }

    @Test
    void testSetReward() {
        Integer reward = 40;
        quest.setReward(40);
        assertEquals(reward, quest.getReward());
    }

    @Test
    void testChangeStatus() {
        quest.changeStatus();
        assertEquals(true, quest.getStatus());
    }

    @Test
    void testToString() {
        String name = quest.getName();
        String description = quest.getDescription();
        Integer reward = quest.getReward();
        String info = String.format("%s  |  %s  |  %s\n================================\n", name, description, reward);
        assertEquals(info, quest.toString());
    }

}