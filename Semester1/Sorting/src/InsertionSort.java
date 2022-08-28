public class InsertionSort extends Sorting {
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int lastExch = i;
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exchange(a, j, j - 1);
                lastExch = j - 1;
            }
            System.out.print(i + "\t" + lastExch + "\t");
            show(a);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String myString = "ILOVEALGORITHMS";
        String[] a = myString.split("(?!^)");
        System.out.print("i\tj\t");
        for (int i = 0; i < a.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("\t\t");
        show(a);
        sort(a);
        assert isSorted(a);
        System.out.print("\t\t");
        show(a);
    }
}
