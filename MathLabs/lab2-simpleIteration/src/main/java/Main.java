public class Main {

    public static void main(String[] args){
        IterationMethod rootSearcher= new IterationMethod();
        double l=3.3;
        int h=3;
        double eps=0.0001;
        double startPoint=0.001;
        System.out.println(0);
        if(l>h){
            System.out.println(rootSearcher.findRoot(startPoint,eps,l,h));
        }

    }
}
