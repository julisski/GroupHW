import java.util.Random;

public class BINGO
{
    private static final int MAX_SIZE = 5;
    // Bingo cell type
    static class BingoCell
    {
        int number;
        boolean isMarked;

        BingoCell(int number, boolean isMarked)
        {
            this.number = number;
            this.isMarked = isMarked;
        }// end constructor
    }// end class BingoCell

    // -------------------------------------------------
    public static void main(String[] args)
    {
        System.out.println("Bingo Game Simulation – ChainThread Recursive Style");

        BingoCell[][] bingoCard = new BingoCell[MAX_SIZE][MAX_SIZE];

        generateBingoCard(bingoCard);
        displayBingoCard(bingoCard);

        // recursive thread chain: 1 → 10
        startChain(1, 10, bingoCard);
    }// end main

    // -------------------------------------------------
    public static void startChain(int current, int end, BingoCell[][] card)
    {
        long tid = Thread.currentThread().getId();
        System.out.println("Thread " + current + " started (ID " + tid + ")");

        // C++-equivalent for this thread
        switch (current)
        {
            case 1:
                checkFirstRow(card, tid);
                break;

            case 2:
                checkDiagonalLR(card, tid);
                break;

            default:
                System.out.printf("\n(%d) Unknown choice: %d\n", tid, current);
                break;
        }// end switch

        // Recursive thread creation
        if (current < end)
        {
            int nextValue = current + 1;

            Thread next = new Thread(() ->
            {
                startChain(nextValue, end, card);
                // recursive call inside new thread
            });
            // end lambda for next thread

            next.start();
        }// end if

    }// end startChain

    // -------------------------------------------------
    public static void generateBingoCard(BingoCell[][] card)
    {
        Random rand = new Random();

        for (int row = 0; row < MAX_SIZE; row++)
        {
            for (int col = 0; col < MAX_SIZE; col++)
            {
                int base = (row + 1) + (col * 15);
                int num = base + rand.nextInt(11);

                boolean mark =
                        (num % 3 == 0) ||
                                (num % 5 == 0) ||
                                (num % 7 == 0);

                card[row][col] = new BingoCell(num, mark);
            }// end for col
        }// end for row
    }// end generateBingoCard

    // -------------------------------------------------
    public static void displayBingoCard(BingoCell[][] card)
    {
        System.out.println(" B    I    N    G    O");
        System.out.println(" --   --   --   --   --");

        for (int row = 0; row < MAX_SIZE; row++)
        {
            for (int col = 0; col < MAX_SIZE; col++)
            {
                if (card[row][col].isMarked)
                    System.out.print("(");
                else
                    System.out.print(" ");

                // free space
                if (row == 2 && col == 2)
                    System.out.print("**");
                else
                    System.out.printf("%2d", card[row][col].number);

                if (card[row][col].isMarked)
                    System.out.print(") ");
                else
                    System.out.print("  ");
            }// end for col

            System.out.println();
        }// end for row
    }// end displayBingoCard

    // -------------------------------------------------
    public static void checkFirstRow(BingoCell[][] card, long tid)
    {
        int count = 0;

        for (int col = 0; col < MAX_SIZE; col++)
        {
            if (card[0][col].isMarked)
                count++;
        }// end for

        if (count == 5)
            System.out.printf("\n(%d) !!!CONGRATULATIONS!!! There is a Bingo in the first row\n", tid);
        else
            System.out.printf("\n(%d) There is no Bingo in the first row\n", tid);
    }// end checkFirstRow

    // -------------------------------------------------
    public static void checkDiagonalLR(BingoCell[][] card, long tid)
    {
        int count = 0;

        if (card[0][0].isMarked) count++;
        if (card[1][1].isMarked) count++;
        if (card[2][2].isMarked) count++;
        if (card[3][3].isMarked) count++;
        if (card[4][4].isMarked) count++;

        if (count == 5)
            System.out.printf("\n(%d) !!!CONGRATULATIONS!!! There is a Bingo in the LR diagonal\n", tid);
        else
            System.out.printf("\n(%d) There is no Bingo in the LR diagonal\n", tid);
    }// end checkDiagonalLR
}// end class BINGO

