import java.util.Random;

public class Main {
    public static void main(String args[]) {
        TextFileHandler.openFile("4_Cities.txt");
        TextFileHandler.readRecords();

        XGrafo grafo = TextFileHandler.getGrafo();
        System.out.println(grafo);

        TravellingSalesman tsm = new TravellingSalesman(grafo);
        tsm.heuristicSolution();
        tsm.printSolution();

        /*XGrafo xGrafo1 = new XGrafo(4, false);

        xGrafo1.inserirAresta(0, 1, 2);
        xGrafo1.inserirAresta(0, 2, 5);
        xGrafo1.inserirAresta(0, 3, 7);

        xGrafo1.inserirAresta(1, 2, 8);
        xGrafo1.inserirAresta(1, 3, 3);

        xGrafo1.inserirAresta(2, 3, 1);

        System.out.println(xGrafo1);
        gerarEntradas(3);

        TravellingSalesman tsm = new TravellingSalesman(xGrafo1);
        tsm.bruteForceSolution();
        tsm.printSolution();

        int n = 15;
        for(int i = 2; i < n; i++) {
            XGrafo grafo = gerarEntradas(i);
            System.out.println("Para " + i + " cidades:");
            System.out.println(grafo);
            TravellingSalesman travellingSalesman = new TravellingSalesman(grafo);
            travellingSalesman.bruteForceSolution();
            travellingSalesman.printSolution();
            System.out.println();
            System.out.println("===============================================================");
        } */

        /* ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        arrayList1.add(1);
        arrayList1.add(3);
        arrayList1.add(8);
        arrayList1.add(9);
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(1);
        arrayList2.add(2);
        arrayList2.add(8);
        arrayList2.add(9);

        System.out.println(arrayList1.equals(arrayList2));

        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        a.add(arrayList1);
        a.add(arrayList2);
        System.out.println(a.size()); */
    }

    public static XGrafo gerarEntradas(int numberOfCities) {
        if(numberOfCities < 2)
            return null;

        XGrafo xGrafo = new XGrafo(numberOfCities, false);
        Random random = new Random();
        int distanceBound = 999;

        for(int i = 0; i < numberOfCities; i++) {
            for(int j = i + 1; j < numberOfCities; j++) {
                xGrafo.inserirAresta(i, j, random.nextInt(distanceBound));
            }
        }
        return xGrafo;
    }

}
