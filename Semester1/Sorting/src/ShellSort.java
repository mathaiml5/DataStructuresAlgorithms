public class ShellSort extends Sorting {
    public static void sort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 2) h = 2 * h + 1;
        while (h >= 1) {

            for (int i = h; i < n; i++) {
                int lastExch = i;
                for (int j = i; j > 0 && (j-h) >= 0 && less(a[j], a[j - h]); j--) {
                    exchange(a, j, j - h);
                    lastExch = j - h;
                }


                System.out.print(h + "\t");
                System.out.print(i + "\t" + lastExch + "\t");
                show(a);
                System.out.println();
            }


            h = h / 3;
        }
    }



    public static void main(String[] args) {
        String myString = "EASYSHELLSORTQUESTION";
        String[] a = myString.split("(?!^)");
        System.out.print("h\ti\tj\t");
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%2d ", i);
//            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("\t \t \t \t");
        show(a);
        sort(a);
        assert isSorted(a);
        System.out.print("\t\t\t");
        show(a);
    }
}
