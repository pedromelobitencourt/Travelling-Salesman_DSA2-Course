/*
* Referências: Cormen
*/
import java.util.Arrays;

public class XGrafo {
   @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(mat);
        result = prime * result + numVertices;
        result = prime * result + Arrays.hashCode(pos);
        result = prime * result + (directed ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        XGrafo other = (XGrafo) obj;
        if (!Arrays.deepEquals(mat, other.mat))
            return false;
        if (numVertices != other.numVertices)
            return false;
        if (!Arrays.equals(pos, other.pos))
            return false;
        if (directed != other.directed)
            return false;
        return true;
    }

private int mat[][]; // Matriz que contém os pesos
   private int numVertices; // Numero de vertices no grafo
   private int pos[]; // Posição atual ao se percorrer os adjs de um vértice v
   private boolean directed; // O grafo eh direcionado ou nao

   public static class Aresta {
       private int v1, v2, peso;

       public Aresta(int v1, int v2, int peso) {
           this.v1 = v1;
           this.v2 = v2;
           this.peso = peso;
       }

       public Aresta(int v1, int v2) {
           this.v1 = v1;
           this.v2 = v2;
           this.peso = 1;
       }

       public int getPeso() {
           return this.peso;
       }

       public int getV1() {
           return this.v1;
       }

       public int getV2() {
           return this.v2;
       }
   }

   public XGrafo(int numVertices, boolean directed) {
       this.mat = new int[numVertices][numVertices]; // Indice começa em 0
       this.numVertices = numVertices;
       this.pos = new int[numVertices];
       this.directed = directed;

       for(int i = 0; i < this.numVertices; i++) {
           for(int j = 0; j < this.numVertices; j++) {
               this.mat[i][j] = 0;
           }
           this.pos[i] = -1;
       }
   }

   public void inserirAresta(int v1, int v2, int peso) {
       this.insertEdge(v1, v2, peso);
   }

   private void insertEdge(int v1, int v2, int peso) {
       if(!this.directed) { // Se nao for direcionado, colocamos o 'peso' em dois lugares na matriz, isto eh, em (v1, v2) e em (v2, v1)
           this.mat[v1][v2] = peso;
           this.mat[v2][v1] = peso;
       }
       else // Se for direcionado, colocamos o 'peso' em apenas um lugar na matriz, isto eh, em (v1, v2)
           this.mat[v1][v2] = peso;
   }

   // Vai existir aresta se o peso na aresta (v1, v2) for maior que 0, pois, se for nulo, nao tera ligacao entre os vertices
   public boolean existeAresta(int v1, int v2) {
       return (this.mat[v1][v2] > 0);
   }

   public Aresta primeiroListaAdj(int v) { // Retorna a primeira aresta que o vértice tem ou null caso não tenha nenhuma aresta conectada
       this.pos[v] = -1;
       return this.proxAdj(v);
   }

   public Aresta proxAdj(int v) { // Retorna a primeira aresta que o vértice tem ou null caso não tenha nenhuma aresta conectada
       this.pos[v]++;

       while((this.pos[v] < this.numVertices) && (this.mat[v][this.pos[v]] == 0)) // Procurando proxima aresta valida, ou seja, aquela que tem peso maior que nulo
           this.pos[v]++; // Alem disso, 'pos[v]' nao pode ser maior ou igual que o numero de vertices (igual porque o index começa em 0)

       if(this.pos[v] == this.numVertices)
           return null;
       else
           return new Aresta(v, this.pos[v], this.mat[v][this.pos[v]]);
   }

   public Aresta retirarAresta(int v1, int v2) {
       return this.removeEdge(v1, v2);
   }

   private Aresta removeEdge(int v1, int v2) {
       if(this.mat[v1][v2] == 0) // Se ja nao havia uma aresta contendo os dois vertices, entao nao ha o que fazer e retorna null
           return null;

       Aresta aresta = new Aresta(v1, v2, this.mat[v1][v2]); // Faz uma nova aresta contendo os vertices e o peso da aresta de tais vertices
       this.mat[v1][v2] = 0; // Ja que removemos a aresta, deixamos o valor do peso '0'
       return aresta;
   }


   // Imprimindo o grafo pela matriz de adjacencia
   public void imprime() {
       for(int i = 0; i < this.numVertices; i++) {
           for(int j = 0; j < this.numVertices; j++)
               System.out.print(this.mat[i][j] + "  ");
           System.out.println();
       }
   }

   @Override
   public String toString() {
       String s = "";

       for(int i = 0; i < this.numVertices; i++) {
           for(int j = 0; j < this.numVertices; j++)
               s += this.mat[i][j] + "  ";
           s += "\n";
       }
       return s;
   }

   public int getNumVertices() {
       return this.numVertices;
   }

   public int[][] getMat() {
       return this.mat;
   }
}
