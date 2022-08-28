public class MergeSortTopDown extends Sorting{
    private static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid); //sort left half
        sort(a, mid+1, hi); //sort right half
        merge(a, lo, mid, hi);
    }
    public static void sort(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a, 0, a.length-1);
    }
    public static void main(String[] args) {
        String myString = "OHSMIDTERMEXAM";
        String[] a = myString.split("(?!^)");
        System.out.print("Input:");
        System.out.print("\t\t\t\t\t");
        for(int i=0;i<a.length;i++)
        {
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.print("\t\t\t\t\t\t");
        show(a);
        sort(a);
        assert isSorted(a);
        System.out.print("Output:");
        System.out.print("\t\t\t\t\t");
        show(a);
    }
}
