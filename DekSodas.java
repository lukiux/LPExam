class VartuProc implements CSProcess {
    private final ChannelOutputInt v;
    private final int kiekKartu, kiekLeisti;
    public VartuProc(ChannelOutputInt v, int kiekKartu, int kiekLeisti) {
        this.v = v;
        this.kiekKartu = kiekKartu;
        this.kiekLeisti = kiekLeisti;
    }
    
    @Override
    public void run(){
        for (int i = 0; i < kiekKartu; i++)
            v.write(kiekLeisti);
        v.write(-1);
    }
}

class valdProc implements CSProcess {
    ChannelInputInt vartai;
    int N; int bendrasKiekis;
    public valdProc(ChannelInputInt vartai, int N){
        this.vartai = vartai;
        this.N = N;
    }
    
    @Override
    public void run() {
        int uzdaryta = 0; bendrasKiekis = 0;
        while (uzdaryta < N) {
            int lank = vartai.read();
            if (lank < 0) uzdaryta++;
            else bendrasKiekis += lank;
        }
        System.out.println(bendrasKiekis);
    }
}

public class DekSodas {
    
    final static int N = 2;
    valdProc valdymas;
    VartuProc[] vartProcesai = new VartuProc[N];
    Parallel visi = new Parallel();
    Any2OneChannelInt vartai = Channel.any2oneInt();
    DekSodas(){
        valdymas = new valdProc(vartai.in(), N);
        for (int i = 0; i < N; i++)
            vartProcesai[i] = new VartuProc(vartai.out(),2,10);
    }
    
    public void vykdyti(){
        visi.addProcess(valdymas);
        visi.addProcess(vartProcesai);
        visi.run();
    }
    public static void main(String[] args) {
        new DekSodas().vykdyti();
    }
    
}
