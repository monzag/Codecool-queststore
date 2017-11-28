package com.codecool.jlamas.models.accountdata;

import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    private Wallet wallet;

    @BeforeEach
    void setUp() {

        this.wallet = new Wallet(100);

    }

    @Test
    void getBalance() {
        Integer balance = 100;
        assertEquals(balance, wallet.getBalance());
    }

    @Test
    void testPut() {
        Integer addedAmount = 50;
        Integer newBalance = 150;
        wallet.put(addedAmount);
        assertEquals(newBalance, wallet.getBalance());
    }

    @Test
    void testTake1() {
        Integer takenAmount = 50;
        assertTrue(wallet.take(takenAmount));

    }

    @Test
    void testTake2() {
        Integer takenAmount = 250;
        assertFalse(wallet.take(takenAmount));

    }

    @Test
    void getDoneQuests() {
        ArrayList<Quest> localQuests = new ArrayList<>();
        Quest quest = new Quest("quest", "description", 10);
        localQuests.add(quest);
        wallet.setDoneQuests(localQuests);

        assertEquals(localQuests, wallet.getDoneQuests());
    }

    @Test
    void getOwnedArtifacts() {
        ArrayList<Artifact> localArtifacts = new ArrayList<>();
        Artifact artifact = new Artifact("name", 20, "description", "Single");
        localArtifacts.add(artifact);
        wallet.setOwnedArtifacts(localArtifacts);

        assertEquals(localArtifacts, wallet.getOwnedArtifacts());
    }

}