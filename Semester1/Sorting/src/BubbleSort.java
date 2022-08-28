public class BubbleSort extends Sorting {
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n-1; i++) {
            int lastExch = i;
            for (int j = 0; j < n-i-1; j++){
                    if (less( a[j+1], a[j]))  exchange(a, j, j + 1);
                    lastExch = j;
            }


//            int exchanges = 0;
//            int lastExch = i;
//            for (int j = n - 1; j > i; j--) {
//                if (less(a[j], a[j - 1])) {
//                    exchange(a, j, j - 1);
//                    lastExch = j - 1;
//                    exchanges++;
//                }

            System.out.print(i + "\t");
            show(a);
            System.out.println();

//            if (exchanges == 0) break;
        }
    }

    public static void main(String[] args) {

        String myString = "ILOVEALGORITHMS";
        String[] a = myString.split("(?!^)");
        System.out.print("i\t");
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
