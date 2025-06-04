import java.util.*;

public class FlamesGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get names from user
        System.out.print("Enter first name: ");
        String name1 = scanner.nextLine().replaceAll(" ", "").toLowerCase();

        System.out.print("Enter second name: ");
        String name2 = scanner.nextLine().replaceAll(" ", "").toLowerCase();

        // Step 2: Convert names to character lists
        List<Character> list1 = new ArrayList<>();
        List<Character> list2 = new ArrayList<>();
        for (char c : name1.toCharArray()) {
            list1.add(c);
        }
        for (char c : name2.toCharArray()) {
            list2.add(c);
        }

        //step 3: need to remove common characters
        for (int i = 0; i < list1.size(); i++) {
            char c = list1.get(i);
            if (list2.contains(c)) {
                list2.remove((Character) c);
                System.out.println("list2 :"+list2);
                  list1.remove(i);
                  System.out.println("list1 :"+list1);
                  i--;
            }
        }
        int remainingchar = list1.size() + list2.size();
        System.out.println("remainingchar : "+remainingchar);

        //step4 : flames logic
        List<Character> flames = new ArrayList<>(List.of('F','L','A','M','E','S'));
        int i=0;
        while(flames.size() >1) {
            i= (i+ remainingchar-1)%flames.size();
            flames.remove(i);
        }
        System.out.println(flames.getFirst());
    }
}
