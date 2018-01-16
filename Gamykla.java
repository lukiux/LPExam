class Gamintojas implements Runnable {
    private int kiekGaminti;
    public Gamintojas(int kiekGaminti) {
        this.kiekGaminti = kiekGaminti;
    }
    
    @Override
    public void run() {
        int gaminys = 10;
        for (int i = 0; i < kiekGaminti; i++) {
            gaminys += 1;
            Gamykla.buferis.iterpti(gaminys);
        }
    }
}

class Vartotojas implements Runnable {
    private int kiekVartoti;
    public Vartotojas(int kiekVartoti) {
        this.kiekVartoti = kiekVartoti;
    }
    
    @Override
    public void run(){
        int gaminys;
        for (int i = 0; i < kiekVartoti; i++)
            gaminys = Gamykla.buferis.paimti();
    }
}

class RibotasBuferis {
    private final int buferioDydis = 5;
    private int[] buferis;
    private int i, is;
    private int yra;
    
    public RibotasBuferis() {
        i = 0; is = 0; yra = 0;
        buferis = new int[buferioDydis];
    }
    
    public synchronized void iterpti(int daiktas){
        try{
            while (yra == buferioDydis)
                wait();            
        } catch (InterruptedException e) {}
        buferis[i] = daiktas;
        i = (i + 1) % buferioDydis;
        yra++;
        notifyAll();
    }
    
    public synchronized int paimti() {
        int daiktas = 0;
        try{
            while (yra == 0)
                wait();
        } catch (InterruptedException e) {}
        daiktas = buferis[is];
        buferis[is] = -999;
        is = (is + 1) % buferioDydis;
        yra--;
        notifyAll();
        return daiktas;
    }
}

public class Gamykla {

    public static RibotasBuferis buferis = new RibotasBuferis();
    public static void main(String[] args) {
        Thread gam = new Thread(new Gamintojas(10));
        Thread vart = new Thread(new Vartotojas(10));
        vart.start();
        gam.start();
    }
    
}
