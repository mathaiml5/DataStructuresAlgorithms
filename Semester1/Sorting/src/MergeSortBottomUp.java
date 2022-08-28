public class MergeSortBottomUp extends Sorting{

    public static void sort(Comparable[] a){
        int n = a.length;
        aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            System.out.println("len = " + len);
            for (int lo = 0; lo < n - len ; lo += len+ len) {
                merge(a, lo, lo+len-1, Math.min(lo+len+len-1, n-1));
            }
        }

    }
    public static void main(String[] args) {
        String myString = "ILOVEALGORITHMS";
        String[] a = myString.split("(?!^)");
        System.out.print("\t\t\t\t\t\t");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.print("Input:");
        System.out.print("\t\t\t\t\t");
        show(a);
        sort(a);
        assert isSorted(a);
        System.out.print("Output:");
        System.out.print("\t\t\t\t\t");
        show(a);
    }
}
