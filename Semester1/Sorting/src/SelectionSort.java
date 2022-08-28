public class SelectionSort extends Sorting{
    public static void sort(Comparable[] a){
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i +1 ; j < n; j++) {
                if(less(a[j], a[min])) min = j;

            }
            System.out.print(i + "\t" + min + "\t");
            show(a);
            System.out.println();
            exchange(a, i, min);
        }
    }
    public static void main(String[] args) {
        String myString = "ILOVEALGORITHMS";
        String[] a = myString.split("(?!^)");
        System.out.print("i min ");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(i+" ");
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
