// Thread 1 → creates Thread 2 → creates Thread 3 →
public class ChainThread
{
    public static void main (String [] args)
    {
        System.out.println("Chain Thread");

        startChain (1,10);
        // will start with 1 and goes to 5
    }// end main

    public static void startChain ( int start, int end)
    {
        System.out.println("Thread "+start+" started");

        if (start < end)
        {
            Thread next = new Thread(() ->
            {
                startChain(start + 1, end);
                // recursive call inside new thread
            });
            //end lambda for next thread

            next.start();
        }
    }// end startChain
}//end class
