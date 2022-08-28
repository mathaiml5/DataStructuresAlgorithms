public class Sorting {

    protected static Comparable[] aux;

    public static void sort(Comparable[] a){
    }
    protected static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
    protected static void exchange(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    protected static void show(Comparable[] a){
//        int numL = String.valueOf(a.length).length();
//        String formatS = "%" + numL + "s";
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
    public static boolean isSorted(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            if(less(a[i], a[i-1])) return false;
        }
        return true;
    }

    //In place merge
    protected static void merge(Comparable[] a, int lo, int mid, int hi){
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi ; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if(i>mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];

        }
        System.out.print("merge(a,\t" + lo + ",\t" + mid + ",\t" + hi + ")\t");
        show(a);
        System.out.println();
    }


}
