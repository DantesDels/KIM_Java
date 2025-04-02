package fr.ynov.kim.domain;

import java.util.Scanner;

public class Discussion {

    public String title;
    public Message startMessage;

    public Discussion(String title, Message startMessage) {
        this.title = title;
        this.startMessage = startMessage;
    }

    public void displayReplies(Message currentMessage) {
        for (Message reply : currentMessage.getReplies()) {
            System.out.println(" - " + reply.getMsg());
        }
    }

    public void start() {
        System.out.println("Starting discussion " + this.title);
        System.out.println(startMessage);
        this.displayReplies(startMessage);
        final Scanner scanner = new Scanner(System.in);

        Message currentMessage = startMessage;
        while(!currentMessage.getReplies().isEmpty()) {
            int choice = scanner.nextInt();
            if (choice == -1) {
                return;
            }
            try {
                if (currentMessage.getReplies().get(choice - 1) != null) {
                    currentMessage = currentMessage.getReplies().get(choice - 1);
                    System.out.println(currentMessage.getMsg());
                    this.displayReplies(currentMessage);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error - Invalid choice");
            }
        }
    }
}
