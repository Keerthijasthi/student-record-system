package course;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Semester {
    private String year;
    private String term; // e.g., "Spring" or "Fall"

    @Override
    public String toString() {
        return term + " " + year;
    }

    // Needed for using Semester as a key in HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semester)) return false;
        Semester that = (Semester) o;
        return year.equals(that.year) && term.equalsIgnoreCase(that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, term.toLowerCase());
    }
}
