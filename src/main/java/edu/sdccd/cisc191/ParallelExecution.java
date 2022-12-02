package edu.sdccd.cisc191;

import java.util.Random;

public class ParallelExecution {

    static Random rand = new Random(System.currentTimeMillis());

    static class MultiplicationThread implements Runnable

    {

        int row, col;

        double[][] a, b, c;

        public MultiplicationThread(double a[][], double b[][], double c[][], int row, int col) {

            this.a = a;

            this.b = b;

            this.c = c;

            this.row = row;

            this.col = col;

        }

        @Override

        public void run() {

            int ans = 0;

            for(int i = 0; i < a[row].length; i++)

                ans += a[row][i] * b[i][col];

            c[row][col] = ans;

        }

    }

    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b)

    {

        double c[][] = new double[a.length][b[0].length];

        int rows = c.length , cols = c[0].length;

        for(int row = 0; row < rows; row++)

        {

            for(int col = 0; col < cols; col++)

            {

//create 1 thread for calculating each element in matrix c and start it.. so that

//all threads can run independently

                Thread t = new Thread(new MultiplicationThread(a, b, c, row, col));

                t.start();

            }

        }

        return c;

    }

    private static double[][] generateMatrix(int row, int col)

    {

        double a[][] = new double[row][col];

        for(int i = 0; i < row; i++)

        {

            for(int j = 0; j < col; j++)

                a[i][j] = rand.nextInt(10);// generate a random number 0-9

        }

        return a;

    }

    private static void print(double a[][])

    {

        for(int i = 0; i < a.length; i++)

        {

            System.out.println();

            for(int j = 0; j < a[0].length; j++)

                System.out.printf("%8.1f", a[i][j] );

        }

        System.out.println();

    }

    public static void main(String[] args) {

        int N = 5; //change this to 2000 for a larger matrix

        double a[][] = generateMatrix(N, N);

        double b[][] = generateMatrix(N, N);

        double c[][] = parallelMultiplyMatrix(a, b);

        System.out.println("Matrix A");

        print(a);

        System.out.println("Matrix B");

        print(b);

        System.out.println("Matrix C = A x B");

        print(c);

    }

}