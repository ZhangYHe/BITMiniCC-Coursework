import java.util.Scanner;

public class MatrixMul {

    public static void main(String[] args) {

        int Matrix_A[][] = new int[100][100];
        int Matrix_B[][] = new int[100][100];
        int Matrix_C[][] = new int[100][100];

        int x_A,y_A,x_B,y_B;

        System.out.println("Please enter x_A,y_A,x_B,y_B");

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String ss[] = s.split(" ");
        x_A=Integer.parseInt(ss[0]);
        y_A=Integer.parseInt(ss[1]);
        x_B=Integer.parseInt(ss[2]);
        y_B=Integer.parseInt(ss[3]);


        System.out.println("Please enter Matrix_A");
        for(int i = 0;i<x_A;i++)
        {
            Scanner sc_A = new Scanner(System.in);
            String s_A = sc_A.nextLine();
            String ss_A[] = s_A.split(" ");
            for(int j = 0;j<y_A;j++)
            {
                Matrix_A[i][j] = Integer.parseInt(ss_A[j]);
            }
        }

        System.out.println("Please enter Matrix_B");
        for(int i = 0;i<x_B;i++)
        {
            Scanner sc_B = new Scanner(System.in);
            String s_B = sc_B.nextLine();
            String ss_B[] = s_B.split(" ");
            for(int j = 0;j<y_B;j++)
            {
                Matrix_B[i][j] = Integer.parseInt(ss_B[j]);
            }
        }

        long start_time = System.currentTimeMillis();
        for(int i = 0;i<x_A;i++)
        {
            for(int j = 0;j<y_B;j++)
            {
                Matrix_C[i][j]=0;
                for(int k = 0;k<y_A;k++)
                {
                    Matrix_C[i][j] += Matrix_A[i][k]*Matrix_B[k][j];
                }
            }
        }

        long end_time = System.currentTimeMillis();
        System.out.println("time: " + (end_time-start_time) + " ms");

        System.out.println("Matrix_C");
        for(int i = 0;i<x_A;i++)
        {
            for(int j = 0;j<y_B;j++)
            {
                System.out.printf("%d",Matrix_C[i][j]);
                if(j == y_B-1)
                {
                    System.out.println();
                }
                else
                {
                    System.out.print(",");
                }
            }
        }
    }
}