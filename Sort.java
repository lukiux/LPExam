class BubbleSort implements Runnable {
    
    private int i, j, temp;
    private int[] numbers;
    private int size;
    BubbleSort(int numbers[], int size) {
        this.numbers = numbers;
        this.size = size;
    }
    
    @Override
    public void run(){
        for (i = (size - 1); i >=0; i--)
        {
            for(j = 1; j <= i; j++)
                if (numbers[j-1] > numbers[j])
                {
                    temp = numbers[j - 1];
                    numbers[j - 1] = numbers[j];
                    numbers[j] = temp;
                }
        }
    }
    
    public void print(){
        for(int l = 0; l < size; l++)
            System.out.println(numbers[l] + " ");
    }
}

public class Sort {

    public static int[] numbers = new int[10];
    public static void main(String[] args) {
        
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = random.nextInt(100);
        
        Thread t = new Thread(new BubbleSort(numbers, numbers.length));
        
        
    }
    
}
