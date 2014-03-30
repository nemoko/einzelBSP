package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import entity.Box;
import exception.BoxException;
import org.junit.Test;
import persistance.BoxDAO;

import java.util.List;


public class BoxDAOImplTest {
    private Box b;
    private BoxDAO bxDAO;

    protected void initBoxDAO(BoxDAO bxDAO) {
        this.bxDAO = bxDAO;
    }

    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test creates and stores a new box in DB, and checks if it was created. Test should succeed.
     */
    @Test
    public void testCreateBox()
    {
        //new Box, fill in NULL CONSTRAINTS
        b = new Box();
        b.setDailyRate(1342534);
        b.setSize(123456789);
        b.setFloor("Wood");
        b.setWindow(true);
        b.setOutside(false);

        //find Boxes
        try {
            List<Box> boxes = bxDAO.findBox(b);

            //Box shouldn't exist in DB yet
            assertFalse(boxes.contains(b));

            //save the new box in DB
            Box x = bxDAO.create(b);

            //find the box
            boxes = bxDAO.findBox(b);

            //box should be in DB
            assertTrue(boxes.contains(b));

        } catch (BoxException be) {
            be.printStackTrace();
        }
    }

    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test creates and stores a new box in DB, with a NULL value, violating a NULL constraint
     * DAO should throw an Exception.
     */
    @Test(expected = NullPointerException.class)
    public void testCreateBoxException()
    {
        //new Box
        b = new Box();

        //save the Box in DB
        try {
            bxDAO.create(b);
        } catch (BoxException be) {
            be.printStackTrace();
        }
    }

    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test should update a Box and check if it works.
     * This test should succeed.
     */
    @Test
    public void testUpdateBox()
    {
        //new Box, fill in NULL CONSTRAINTS
        b = new Box();
        b.setDailyRate(1342534);
        b.setPicURL("");
        b.setSize(123456789);
        b.setFloor("Straw");
        b.setWindow(true);
        b.setOutside(false);

        //save the new box in DB
        try {
            b = bxDAO.create(b);

            //confirming the price of the box in DB is correct
            assertEquals(1342534, b.getDailyRate(),0);

            //updating the price
            b.setDailyRate(10);

                bxDAO.update(b);

            //the new price should be 10
            List<Box> lbox = bxDAO.findBox(b);

            assertEquals(10,lbox.get(0).getDailyRate(),0);

        } catch (BoxException be) {
            be.printStackTrace();
        }
    }

    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test will try update a box that doesn't exist.
     * BoxException is expected from DAO.
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateBoxException()
    {
        //create new Box
        Box b = new Box();

        try {
            //update the created box in DB
            bxDAO.update(b);
        } catch (BoxException be) {
            be.printStackTrace();
        }
    }

    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test will try to list all boxes in DB
     * It should succeed.
     */
    @Test
    public void testFindBoxes() {
        Box b = new Box();

        //list all boxes in DB
        try {
            List<Box> blist = bxDAO.findBox(b);

            assertFalse(blist.isEmpty());

        } catch (BoxException be) {
            be.printStackTrace();
        }
    }


    /**
     * Test method is for  sepm.ss14.e1228153.dao.BoxDAOImpl
     *                     sepm.ss14.e1228153.entity.Box
     *
     * This test tries to get a list of actual boxes.
     * It should throw an Exception because the list is not actual.
     */
    @Test(expected = NullPointerException.class)
    public void testFindBoxesException() {

        try {
            Box b = new Box();

            //create a list with all boxes
            List<Box> blist = bxDAO.findBox(b);

            b = bxDAO.create(b);

            //create a list with all boxes
            List<Box> clist = bxDAO.findBox(b);

            assert(blist.size() != clist.size());

        } catch (BoxException be) {
            be.printStackTrace();
        }
    }


}
