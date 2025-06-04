package course;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class Student {
    private String id;
    private String name;

    private Map<Semester, List<Course>> semesterCourses = new HashMap<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
