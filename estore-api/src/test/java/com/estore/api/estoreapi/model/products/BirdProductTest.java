package com.estore.api.estoreapi.model.products;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BirdProductTest {

    @Test
    void testGetSponsors() {
        String[] sponsors = { "1", "2", "3" };
        BirdProduct bird = createBird(1);

        assertEquals(null, bird.getSponsors());

        bird.setSponsors(sponsors);

        String[] actual = bird.getSponsors();

        assertEquals(Arrays.toString(sponsors), Arrays.toString(actual));
    }

    @Test
    void testAddSponsor() {
        String[] sponsors = new String[0];
        BirdProduct bird = createBird(1);

        assertEquals(false, bird.addSponsor("Sponsor"));

        bird.setSponsors(sponsors);

        assertEquals(true, bird.addSponsor("Sponsor"));

        assertEquals(true, bird.addSponsor("Sponsor2"));

        assertEquals(false, bird.addSponsor("Sponsor"));
    }

    @Test
    void testRemoveSponsor() {
        String[] sponsors = new String[0];
        BirdProduct bird = createBird(1);

        assertEquals(false, bird.removeSponsor("Sponsor"));

        bird.setSponsors(sponsors);

        assertEquals(false, bird.removeSponsor("Sponsor"));

        bird.addSponsor("Sponsor");
        bird.addSponsor("Sponsor2");

        assertEquals(true, bird.removeSponsor("Sponsor"));
        assertEquals(true, bird.removeSponsor("Sponsor2"));

    }

    @Test
    void testSetSponsors() {
        String[] sponsors = { "1", "2", "3" };
        BirdProduct bird = createBird(1);

        assertEquals(null, bird.getSponsors());

        bird.setSponsors(sponsors);
        String[] actual = bird.getSponsors();

        assertEquals(Arrays.toString(sponsors), Arrays.toString(actual));
    }

    @Test
    void testToString() {
        String expected = "\nProduct {id=1, productType=1, name=TestBird, price=15.000000, quantity=5, description=Testing Bird Product, fileName=null, fileSource=null, sponsors=[Sponsor]}\n";

        BirdProduct bird = createBird(1);
        bird.setSponsors(new String[0]);
        bird.addSponsor("Sponsor");

        assertEquals(expected, bird.toString());

    }

    private BirdProduct createBird(int id) {
        return new BirdProduct(id, 1, 15, "TestBird", "Testing Bird Product", 5, null, null, null);
    }
}
