class Filosofas extends Thread {
    private int numeris, gyvTrukme;
    public Filosofas(int numeris, int gyvTrukme){
        this.numeris = numeris;
        this.gyvTrukme = gyvTrukme;
    }
    
    private void valgyti() {
    }
    private void mastyti() {}
    
    @Override
    public void run(){
        for (int i = 0; i < gyvTrukme; i++){
            try {
                Valgykla.sakute[numeris].acquire();
                System.out.println("Filosofas nr." + numeris + "\n");
                Valgykla.sakute[(numeris + 1) % Valgykla.filosN].acquire();
                System.out.println("Filosofas nr." + (numeris + 1) % Valgykla.filosN + "\n");
                valgyti();
                Valgykla.sakute[numeris].release();
                System.out.println("Filosofas nr." + numeris + "\n");
                Valgykla.sakute[(numeris + 1) % Valgykla.filosN].release();
                System.out.println("Filosofas nr." + (numeris + 1) % Valgykla.filosN + "\n");
                mastyti();
            } catch (InterruptedException e) {}
        }
    }
}

public class Valgykla {

    public static int filosN = 5, gyvT = 500;
    public static Semaphore[] sakute = new Semaphore[filosN];
    public static void main(String[] args) {
        Filosofas[] filos = new Filosofas[filosN];
        for (int i = 0; i < filosN; i++)
            filos[i] =  new Filosofas(i, gyvT);
        for (int i = 0; i < filosN; i++)
            sakute[i] = new Semaphore(1);
        for (int i = 0; i < filosN; i++)
            filos[i].start();
    }
    
}
