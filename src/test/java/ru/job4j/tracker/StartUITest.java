package ru.job4j.tracker;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {
    @Test
    public void whenCreateItem() {
        Output out = new StubOutput();
        Input in = new StubInput(new String[]{"0", "Item name", "1"});
        Tracker tracker = new Tracker();
        UserAction[] actions = new UserAction[]{
                new CreateAction(out),
                new ExitAction()
        };
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findAll()[0].getName(), is("Item name"));
    }

    @Test
    public void whenReplaceItem() {
        Tracker tracker = new Tracker();
        Output out = new StubOutput();
        UserAction[] actions = new UserAction[]{
                new CreateAction(out),
                new EditAction(out),
                new ExitAction()
        };
        Input input = new StubInput(new String[]{
                "0",
                "item name",
                "1",
                "1",
                "new name",
                "2"});
        new StartUI(out).init(input, tracker, actions);
        assertThat(tracker.findAll()[0].getName(), is("new name"));
    }

    @Test
    public void whenReplaceItem1() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        /* Добавим в tracker новую заявку */
        Item item = tracker.add(new Item("Replaced item"));
        /* Входные данные должны содержать ID добавленной заявки item.getId() */
        String replacedName = "New item name";
        Input in = new StubInput(
                new String[]{"0", String.valueOf(item.getId()), replacedName, "1"}
        );
        UserAction[] actions = {
                new EditAction(out),
                new ExitAction()
        };
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()).getName(), is(replacedName));
    }

    @Test
    public void whenDeleteItem() {
        Output out = new StubOutput();
        Item item = new Item("first item");
        item.setId(1);
        Item item1 = new Item("second item");
        item1.setId(2);
        Tracker tracker = new Tracker();
        tracker.add(item);
        tracker.add(item1);
        Input input = new StubInput(new String[]{"0", String.valueOf(item.getId()), "1"});
        UserAction[] actions = new UserAction[]{new DeleteAction(out), new ExitAction()};
        new StartUI(out).init(input, tracker, actions);
        Item[] items = tracker.findAll();
        assertThat(items[0], is(item1));
    }

    @Test
    public void whenDeleteItem1() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        /* Добавим в tracker новую заявку */
        Item item = tracker.add(new Item("Deleted item"));
        /* Входные данные должны содержать ID добавленной заявки item.getId() */
        Input in = new StubInput(
                new String[]{"0", "1", "1"}
        );
        UserAction[] actions = {
                new DeleteAction(out),
                new ExitAction()
        };
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()), is(nullValue()));
    }

    @Test
    public void whenExit() {
        Tracker tracker = new Tracker();
        Output out = new StubOutput();
        Input in = new StubInput(new String[] {"0"});
        UserAction[] actions = new UserAction[] {new ExitAction()};
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu:"
                + System.lineSeparator()
                + "0. Exit Program"
                + System.lineSeparator()));
    }

    @Test
    public void whenShawAll() {
        Tracker tracker = new Tracker();
        Output output = new StubOutput();
        Input input = new StubInput(new String[] {"0", "new item", "1", "2"});
        UserAction[] actions = new UserAction[] {new CreateAction(output), new ShowAllAction(output), new ExitAction()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is(
                         "Menu:" + System.lineSeparator()
                            + "0. Add new Item" + System.lineSeparator()
                            + "1. Show all items" + System.lineSeparator()
                            + "2. Exit Program" + System.lineSeparator()
                            + "=== Create a new Item ====" + System.lineSeparator()
                            + "Added item: " + tracker.findAll()[0].toString() + System.lineSeparator()
                            + "Menu:" + System.lineSeparator() + "0. Add new Item" + System.lineSeparator()
                            + "1. Show all items" + System.lineSeparator()
                            + "2. Exit Program" + System.lineSeparator()
                            + "=== Show all items ====" + System.lineSeparator()
                            + tracker.findAll()[0].toString() + System.lineSeparator()
                            + "Menu:" + System.lineSeparator()
                            + "0. Add new Item" + System.lineSeparator()
                            + "1. Show all items" + System.lineSeparator()
                            + "2. Exit Program" + System.lineSeparator()
        ));
    }

    @Test
    public void whenFindByNameAction() {
        Tracker tracker = new Tracker();
        Output output = new StubOutput();
        Item item1 = new Item("first item");
        tracker.add(item1);
        Item item2 = new Item("second item");
        tracker.add(item2);
        Input input = new StubInput(new String[] {"0", "first item", "1"});
        UserAction[] actions = new UserAction[] {new FindByNameAction(output), new ExitAction()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is("Menu:" + System.lineSeparator()
                + "0. Find items by name" + System.lineSeparator()
                + "1. Exit Program" + System.lineSeparator()
                + "=== Find items by name ====" + System.lineSeparator()
                + item1.toString() + System.lineSeparator()
                + "Menu:" + System.lineSeparator()
                + "0. Find items by name" + System.lineSeparator()
                + "1. Exit Program" + System.lineSeparator())
        );
    }

    @Test
    public void whenFindByIdAction() {
        Tracker tracker = new Tracker();
        Output output = new StubOutput();
        Item item1 = new Item("first item");
        tracker.add(item1);
        Item item2 = new Item("second item");
        tracker.add(item2);
        Input input = new StubInput(new String[] {"0", "2", "1"});
        UserAction[] actions = new UserAction[] {new FindByIdAction(output), new ExitAction()};
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString(), is("Menu:" + System.lineSeparator()
                + "0. Find item by id" + System.lineSeparator()
                + "1. Exit Program" + System.lineSeparator()
                + "=== Find item by id ====" + System.lineSeparator()
                + item2.toString() + System.lineSeparator()
                + "Menu:" + System.lineSeparator()
                + "0. Find item by id" + System.lineSeparator()
                + "1. Exit Program" + System.lineSeparator())
        );
    }
//    @Test
//    public void whenAddItem() {
//        String[] answers = {"Fix PC"};
//        Input input = new StubInput(answers);
//        Tracker tracker = new Tracker();
//        StartUI.createItem(input, tracker);
//        Item created = tracker.findAll()[0];
//        Item expected = new Item("Fix PC");
//        assertThat(created.getName(), is(expected.getName()));
//        //assertTrue(created.getName().equals(expected.getName()));
//    }
//
//    @Test
//    public void whenAdd2Items() {
//        String[] answers = {"Hello", "Goodbye"};
//        Input input = new StubInput(answers);
//        Tracker tracker = new Tracker();
//        StartUI.createItem(input, tracker);
//        StartUI.createItem(input, tracker);
//        Item[] result = tracker.findAll();
//        Item item1 = new Item("Hello");
//        item1.setId(1);
//        Item item2 = new Item("Goodbye");
//        item2.setId(2);
//        System.out.println(item1.equals(result[0]));
//        Item[] expected = {item1, item2};
//        assertArrayEquals(result, expected);
////        for (int i = 0; i < expected.length; i++) {
////            assertThat(result[i].getName(), is(expected[i].getName()));
////        }
//    }
//
//    @Test
//    public void whenEditItem() {
//        Tracker tracker = new Tracker();
//        Item item = new Item("new item");
//        tracker.add(item);
//        String[] answers = {
//                String.valueOf(item.getId()),
//                "edited item"
//        };
//        StartUI.editItem(new StubInput(answers), tracker);
//        Item replaced = tracker.findById(item.getId());
//        assertThat(replaced.getName(), is("edited item"));
//    }
//
//    @Test
//    public void whenDeleteItem() {
//        Tracker tracker = new Tracker();
//        Item item = new Item("new item");
//        tracker.add(item);
//        String[] answers = new String[] {String.valueOf(item.getId())};
//        StartUI.deleteItem(new StubInput(answers), tracker);
//        Item deleted = tracker.findById(item.getId());
//        assertNull(deleted);
//    }
}