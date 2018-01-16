class Filtras implements CSProcess {
    private final ChannelInputInt isKaires;
    private final ChannelOutputInt desinen;
    private final ChannelOutputInt zemyn;
    
    public Filtras(ChannelInputInt isKaires, ChannelOutputInt desinen, ChannelOutputInt zemyn){
        this.isKaires = isKaires;
        this.desinen = desinen;
        this.zemyn = zemyn;
    }
    
    @Override
    public void run() {
        int n, m;
        n = isKaires.read(); zemyn.write(n);
        for (;;){
            m = isKaires.read();
            if ((m % n) != 0)
                desinen.write(m);
        }
    }
}

class SkaiciuGeneratorius implements CSProcess {
    private final ChannelOutputInt desinen;
    
    public SkaiciuGeneratorius(ChannelOutputInt desinen)
    {
        this.desinen = desinen;
    }
    
    @Override
    public void run() {
        for (int sk = 2; ; sk++)
            desinen.write(sk);
    }
}

class Spausdintuvas implements CSProcess {
    private final ChannelInputInt[] isVirsiaus;
    private int p, N;
    
    public Spausdintuvas(ChannelInputInt[] isVirsiaus, int N){
        this.isVirsiaus = isVirsiaus;
        this.N = N;
    }
    
    public void run() {
        for (int i = 0; i < N; i++)
            p = isVirsiaus[i].read();
    }
}

class Surinkejas implements CSProcess {
    private final ChannelInputInt isKaires;
    
    public Surinkejas(ChannelInputInt isKaires) {
        this.isKaires = isKaires;
    }
    
    @Override
    public void run() {
        int sk = 0;
        for (;;)
            sk = isKaires.read();
    }
}

public class PirminiaiSkaiciai {

    public static final int N = 4;
    One2OneChannelInt[] desinen = new One2OneChannelInt[N + 1];
    One2OneChannelInt[] zemyn = new One2OneChannelInt[N];
    One2OneChannelInt[] zemynIn = new One2OneChannelInt[N];
    private final Parallel rasti = new Parallel();
    
    PirminiaiSkaiciai() {
        for (int i = 0; i < N + 1; ++i)
            desinen[i] = Channel.one2oneInt();
        for (int i = 0; i < N; ++i)
            AltingChannelInputInt[] zemynIn = new AltingChannelInputInt[N];
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
