package com.keerthi.mavenproject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.base.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@ToString
@Builder
class StudentRecord {
    private int id;
    private String name;
}

public class StudentRecordSystem {
    private final List<StudentRecord> studentList = new ArrayList<>();
    private final Multimap<String, StudentRecord> nameMap = ArrayListMultimap.create(); // ✅ Guava Multimap
    private final Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        System.out.print("Enter Student ID: ");
        String idInput = scanner.nextLine();

        if (!NumberUtils.isCreatable(idInput)) {
            System.out.println("Invalid input. ID must be a number.\n");
            return;
        }
        int id = Integer.parseInt(idInput);

        for (StudentRecord s : studentList) {
            if (s.getId() == id) {
                System.out.println("Error: Student ID already exists.\n");
                return;
            }
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        if (StringUtils.isBlank(name)) {
            System.out.println("Error: Student name cannot be empty.\n");
            return;
        }

        StudentRecord student = StudentRecord.builder()
                .id(id)
                .name(name)
                .build();

        studentList.add(student);
        nameMap.put(name.toLowerCase(), student); // Added to multimap
        System.out.println("Student added successfully!\n");
    }

    public void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String input = scanner.nextLine();

        if (!NumberUtils.isCreatable(input)) {
            System.out.println("Invalid input. ID must be a number.\n");
            return;
        }

        int id = Integer.parseInt(input);

        Optional<StudentRecord> result = Optional.absent();
        for (StudentRecord s : studentList) {
            if (s.getId() == id) {
                result = Optional.of(s); //  Guava Optional
                break;
            }
        }

        if (result.isPresent()) {
            System.out.println("Student Found: " + result.get() + "\n");
        } else {
            System.out.println("Student not found.\n");
        }
    }

    public void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().toLowerCase();

        if (StringUtils.isBlank(name)) {
            System.out.println("Error: Name cannot be blank.\n");
            return;
        }

        var records = nameMap.get(name);
        if (records.isEmpty()) {
            System.out.println("No student records found with name: " + name + "\n");
        } else {
            System.out.println("Students with name '" + name + "':");
            records.forEach(System.out::println);
            System.out.println();
        }
    }

    public void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students to display.\n");
        } else {
            System.out.println("\n--- Student Records ---");
            ImmutableList<StudentRecord> snapshot = ImmutableList.copyOf(studentList);
            snapshot.forEach(System.out::println);
            System.out.println();
        }
    }

    public void run() {
        int choice = 0;
        do {
            System.out.println("===== Student Record System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student by ID");
            System.out.println("3. Search Students by Name");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choiceInput = scanner.nextLine();
            if (!NumberUtils.isCreatable(choiceInput)) {
                System.out.println("Please enter a valid number.\n");
                continue;
            }

            choice = Integer.parseInt(choiceInput);
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> searchStudent();
                case 3 -> searchByName(); //  Added new option
                case 4 -> displayAllStudents();
                case 5 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        StudentRecordSystem system = new StudentRecordSystem();
        system.run();
    }
}

