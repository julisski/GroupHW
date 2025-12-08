// Parent thread → creates → many child threads (fan-out)
public class FanThread
{
    public static void main  (String[]args)
    {
        System.out.println("Parent: Starting fan thread");

        int numberOfChildren = 5; // how many child threads to create

        // Loop to create children
        for (int i = 1; i <= numberOfChildren; i++)
        {

            int childId = i;
            //lambda captures correct child number

            // Define child thread
            Thread child = new Thread(() ->
            {
                System.out.println("Child " + childId + ": Running");
            }); // end lambda for child thread

            child.start();
        } // end for loop creating children

        System.out.println("Parent: All children started!");

    }// end main
}// end class
