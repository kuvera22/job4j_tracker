package ru.job4j.tracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Tracker tracker, UserAction[] actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Select: ");
            UserAction action = actions[select];
            run = action.execute(input, tracker);

        }
    }

    private void showMenu(UserAction[] actions) {
        out.println("Menu:");
        for (int i = 0; i < actions.length; i++) {
            out.println(i + ". " + actions[i].name());
        }

    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ConsoleInput();
        Tracker tracker = new Tracker();
        UserAction[] actions = {new CreateAction(output),
                                new ShowAllAction(output),
                                new EditAction(output),
                                new DeleteAction(output),
                                new FindByIdAction(output),
                                new FindByNameAction(output),
                                new ExitAction()};
        new StartUI(output).init(input, tracker, actions);
    }
}
//    public static void createItem(Input input, Tracker tracker) {
//        System.out.println("=== Create a new Item ====");
//        String name = input.askStr("Enter name: ");
//        Item item = new Item(name);
//        tracker.add(item);
//        System.out.println("Added item: " + item);
//    }

//    public static void showAllItems(Input input, Tracker tracker) {
//        System.out.println("=== Show all items ====");
//        Item[] items = tracker.findAll();
//        if (items.length > 0) {
//            for (Item item : items) {
//                System.out.println(item);
//            }
//        } else {
//            System.out.println("No items yet");
//        }
//    }

//    public static void editItem(Input input, Tracker tracker) {
//        System.out.println("=== Edit item ====");
//        int id = input.askInt("Enter id: ");
//        String name = input.askStr("Enter name: ");
//        Item item = new Item(name);
//        if (tracker.replace(id, item)) {
//            System.out.println("Заявка изменена успешно.");
//        } else {
//            System.out.println("Ошибка замены заявки.");
//        }
//    }
//
//    public static void deleteItem(Input input, Tracker tracker) {
//        System.out.println("=== Delete item ====");
//        int id = input.askInt("Enter id: ");
//        if (tracker.delete(id)) {
//            System.out.println("Заявка успешно удалена");
//        } else {
//            System.out.println("Ошибка удаления заявки");
//        }
//    }

//    public static void findItemById(Input input, Tracker tracker) {
//        System.out.println("=== Find item by id ====");
//        int id = input.askInt("Enter id: ");
//        Item item = tracker.findById(id);
//        if (item != null) {
//            System.out.println(item);
//        } else {
//            System.out.println("Заявка с таким id не найдена");
//        }
//    }

//    public static void findItemsByName(Input input, Tracker tracker) {
//        System.out.println("=== Find items by name ====");
//        String name = input.askStr("Enter name: ");
//        Item[] items = tracker.findByName(name);
//        if (items.length > 0) {
//            for (Item elem : items) {
//                System.out.println(elem);
//            }
//        } else {
//            System.out.println("Заявки с таким именем не найдена");
//        }
//    }

//   Item item = new Item();
//        LocalDateTime itemDateTime = item.getCreated();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");
//        String itemDateTimeFormatted = itemDateTime.format(formatter);
//        System.out.println(itemDateTimeFormatted);
//
//       System.out.println(item);

//        String[] menu = {
//                "Add new Item", "Show all items", "Edit item",
//                "Delete item", "Find item by id", "Find items by name",
//                "Exit Program"
//        };
//        for (int i = 0; i < menu.length; i++) {
//            System.out.println(i + ". " + menu[i]);
//        }

//            switch (select) {
//                case 0:
//                    createItem(input, tracker);
//                    break;
//                case 1:
//                    showAllItems(input, tracker);
//                    break;
//                case 2:
//                    editItem(input, tracker);
//                    break;
//                case 3:
//                    deleteItem(input, tracker);
//                    break;
//                case 4:
//                    findItemById(input, tracker);
//                    break;
//                case 5:
//                    findItemsByName(input, tracker);
//                    break;
//                case 6:
//                    run = false;
//                    break;
//                default:
//                    System.out.println("Incorrect");
//
//            }