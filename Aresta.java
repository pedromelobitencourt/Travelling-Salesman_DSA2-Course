public class Aresta {
    private int in; // Primeiro vertice, ou seja, em uma aresta (u, v), o 'in' seria o 'u'
    private int out; // Segundo vertice, ou seja, em uma aresta (u, v), o 'out' seria o 'v'

    public Aresta() {
        this.in = 0;
        this.out = 0;
    }

    public Aresta(int in, int out) {
        this.in = in;
        this.out = out;
    }

    public Aresta(Aresta aresta) {
        this.in = aresta.getIn();
        this.out = aresta.getOut();
    }

    public int getIn() {
        return this.in;
    }

    public int getOut() {
        return this.out;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public void setOut(int out) {
        this.out = out;
    }

    // Determinando a maneira de imprimir um objeto Aresta
    @Override
    public String toString() {
        String s = "(" + this.in + ", " + this.out + ")";
        return s;
    }
}
