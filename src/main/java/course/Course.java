package course;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {
    private String code;
    private String title;
    private int credits;
    private boolean elective;
}
