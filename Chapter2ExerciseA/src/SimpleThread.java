// Parent thread → creates → one child thread


public class SimpleThread
{
    public static void main (String [] args)
    {
        System.out.println("Parent: Starting simple thread");

        // create child tread
        Thread child = new Thread (() ->
        {
            System.out.println("Child: Hello Parent: from Child ");
        }); // end lambda in the child thread

        child.start();
        System.out.println("Parent: Child: Starting simple thread ");

    }// end Main
}// end class
