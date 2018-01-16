class Rasytojas implements Runnable {
    private int kiekRasyti;
    private int laiskas = 10;
    public Rasytojas(int kiekRasyti){
        this.kiekRasyti = kiekRasyti;
    }
    
    @Override
    public void run(){
        for (int i = 0; i < kiekRasyti; i++) {
            laiskas += 1;
            Pastas.pD.padeti(laiskas);
        }
    }
}

class Skaitytojas extends Thread {
    private int kiekSkaityti, nr;
    private int laiskas = 0;
    public Skaitytojas(int kiekSkaityti, int nr){
        this.kiekSkaityti = kiekSkaityti;
        this.nr = nr;
    }
    
    @Override
    public void run(){
        for (int i = 0; i < kiekSkaityti; i++)
            laiskas = Pastas.pD.paimti(nr);  
        System.out.println(laiskas);
    }
}

class PastoDezute {
    private int laiskas, kiekSkaito;
    private volatile boolean gRasyti;
    private volatile boolean gSkaityti[];
    public PastoDezute(int kiekSkaito) {
        this.kiekSkaito = kiekSkaito;
        gSkaityti = new boolean[kiekSkaito];
        laiskas = 0; gRasyti = true;
        for (int i = 0; i < kiekSkaito; i++)
            gSkaityti[i] = false;
    }
    
    public synchronized void padeti(int naujas){
        try{
            while (!gRasyti) wait();
        } catch (InterruptedException e){}
        
        laiskas = naujas;
        gRasyti = false;
        for (int i = 0; i < kiekSkaito; i++)
            gSkaityti[i] = true;
        notifyAll();
    }
    
    public synchronized int paimti(int k) {
        int naujasLaiskas = 0;
        try{
            while (!gSkaityti[k]) wait();
        } catch (InterruptedException e) {}
        
        naujasLaiskas = laiskas;
        gSkaityti[k] = false;
        gRasyti = true;
        for (int i = 0; i < kiekSkaito; i++)
            gRasyti = gRasyti && (!gSkaityti[i]);
        notifyAll();
        return naujasLaiskas;
    }
}

public class Pastas {
    
    public static int kiekSkaito = 5;
    public static PastoDezute pD = new PastoDezute(kiekSkaito);
    public static void main(String[] args) {
        Thread ras = new Thread(new Rasytojas(20));
        Skaitytojas sk[] = new Skaitytojas[kiekSkaito];
        for (int i = 0; i < kiekSkaito; i++)
            sk[i] = new Skaitytojas(20, i);
        for (int i = 0; i < kiekSkaito; i++)
            sk[i].start();
        ras.start();
    }
    
}
