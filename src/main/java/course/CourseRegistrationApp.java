package course;
import java.util.*;

public class CourseRegistrationApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Define semesters
        Semester spring2025 = new Semester("2025", "Spring");
        Semester summer2025 = new Semester("2025", "Summer");
        Semester fall2025 = new Semester("2025", "Fall");

        // Predefined courses
        Course cs101 = Course.builder().code("CS101").title("Intro to Computer Science").credits(3).elective(false).build();
        Course math150 = Course.builder().code("MATH150").title("Linear Algebra").credits(3).elective(false).build();
        Course art202 = Course.builder().code("ART202").title("Modern Art").credits(3).elective(true).build();
        Course music101 = Course.builder().code("MUSIC101").title("Music Theory").credits(2).elective(true).build();
        Course phy101 = Course.builder().code("PHY101").title("Physics").credits(4).elective(false).build();
        Course eng201 = Course.builder().code("ENG201").title("Advanced English").credits(3).elective(false).build();
        Course hist110 = Course.builder().code("HIST110").title("World History").credits(3).elective(true).build();

        // Map courses per semester
        Map<Semester, List<Course>> coursesBySemester = new LinkedHashMap<>();
        coursesBySemester.put(spring2025, List.of(cs101, art202, phy101));
        coursesBySemester.put(summer2025, List.of(math150, music101, eng201));
        coursesBySemester.put(fall2025, List.of(eng201, hist110, cs101, music101));

        // Student details
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        Student student = new Student(studentId, studentName);

        // Process each semester
        for (Semester semester : coursesBySemester.keySet()) {
            System.out.printf("%n--- Registering for %s --- %n", semester);

            List<Course> availableCourses = coursesBySemester.get(semester);
            for (int i = 0; i < availableCourses.size(); i++) {
                Course c = availableCourses.get(i);
                System.out.printf("%d: %s (%d credits) [%s]%n", i + 1, c.getTitle(), c.getCredits(),
                        c.isElective() ? "Elective" : "Core");
            }

            List<Course> selectedCourses = new ArrayList<>();
            int totalCredits = 0;

            while (totalCredits != 9) {
                System.out.printf("Current total credits: %d. You must reach exactly 9 credits.%n", totalCredits);
                System.out.print("Enter course numbers to register (comma separated, e.g., 1,3): ");
                String input = scanner.nextLine();
                String[] indexes = input.split(",");
                selectedCourses.clear();
                totalCredits = 0;

                try {
                    for (String indexStr : indexes) {
                        int idx = Integer.parseInt(indexStr.trim()) - 1;
                        if (idx < 0 || idx >= availableCourses.size()) {
                            throw new IllegalArgumentException("Invalid course number.");
                        }
                        Course selected = availableCourses.get(idx);
                        if (selectedCourses.contains(selected)) {
                            throw new IllegalArgumentException("Duplicate course selected.");
                        }
                        selectedCourses.add(selected);
                        totalCredits += selected.getCredits();
                    }

                    if (totalCredits != 9) {
                        System.out.println("Total credits must be exactly 9. Please try again.");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    selectedCourses.clear();
                    totalCredits = 0;
                }
            }

            student.getSemesterCourses().put(semester, selectedCourses);
        }

        // Final summary
        System.out.printf("%n Registration Summary for Student: %s%n", student.getName());
        for (Semester sem : coursesBySemester.keySet()) {
            System.out.printf("Semester: %s %s%n", sem.getTerm(), sem.getYear());
            List<Course> courses = student.getSemesterCourses().get(sem);
            courses.forEach(c -> System.out.printf("- %s (%d credits) [%s]%n",
                    c.getTitle(), c.getCredits(), c.isElective() ? "Elective" : "Core"));
            System.out.printf("Total Credits: %d%n%n",
                    courses.stream().mapToInt(Course::getCredits).sum());
        }

        scanner.close();
    }
}
