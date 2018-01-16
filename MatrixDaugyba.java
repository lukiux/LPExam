class Matrix extends Thread {
    private int row;
    private int col;
    private int [][] A;
    private int [][] B;
    private int [][] C;
    
    public Matrix(int row, int col, int [][] A, int [][] B, int [][] C){
        this.row = row;
        this.col = col;
        this.A = A;
        this.B = B;
        this.C = C;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < B.length; i++)
            C[row][col] += A[row][i] * B[i][col];
    }
}

public class MatricosDaugyba {

    public static int [][] A = {{1,4}, {2,5}, {3,6}};
    public static int [][] B = {{8,7,6}, {5,4,3}};
    public static int [][] C = new int [3][3];
    public static Matrix [][] Threads = new Matrix[3][3];
    public static void main(String[] args) throws InterruptedException {
        
        for (int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                Threads[i][j] = new Matrix(i,j,A,B,C);
                Threads[i][j].start();
                Threads[i][j].join();
            }
        }
        
        System.out.println("Elements of Matrix C:");
        for (int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                System.out.println("["+i+","+j+"] = " + C[i][j]);
            }
        } 
    }
    
}
