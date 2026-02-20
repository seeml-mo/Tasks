package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;
import se.edu.streamdemo.task.TaskComparator;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        printWelcomeMassage();
        Datamanager dataManager = new Datamanager("./data/data.txt"); //relative path
        // "C:\\Users\\dcsaksh\\Desktop\\ip\\data\\data.txt" <<< absolute path
        // /home/username/ip/data/data.txt
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);
        printDataUsingStreams(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printDeadlineUsingStreams(tasksData);

        System.out.println("Total number of deadlines through iterations: " + countDeadlines(tasksData));
        System.out.println("Total number of deadlines using streams: " + countDeadlines(tasksData));

        ArrayList<Task> filterList = printFilteredList(tasksData,"11");
        printAllData(filterList);
    }

    private static void printWelcomeMassage() {
        System.out.println("Welcome to Task manager (using streams)");
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStreams(ArrayList<Task> tasks){

        long count = tasks.stream()
                .filter((t) -> t instanceof  Deadline)
                .count();

        return (int)count;
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing all data through iteration ...");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataUsingStreams(ArrayList<Task> tasks){
        System.out.println("Printing deadlines using streams ...");
        tasks.stream()
                .forEach(System.out::println);
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing deadlines through iteration ...");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlineUsingStreams(ArrayList<Task> tasks){
        System.out.println("Printing deadlines using streams ...");
        tasks.parallelStream()
                .filter((t) -> t instanceof Deadline)
                .sorted((t1,t2) -> t1.getDescription().compareToIgnoreCase(t2.getDescription()))
                .forEachOrdered(System.out::println);
    }

    public static ArrayList<Task> printFilteredList(ArrayList<Task> tasks, String filterString){
        ArrayList<Task> filterList = (ArrayList<Task>) tasks.stream()
                .filter((t) -> t.getDescription().contains(filterString))
                .collect(toList());
        
        return  filterList;
    }

}
