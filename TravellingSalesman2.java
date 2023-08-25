import java.util.ArrayList;

public class TravellingSalesman {
    private XGrafo xGrafo;
    private ArrayList<ArrayList<Integer>> paths; // Caminho partindo do indice 'i' ('i' eh a cidade de origem)
    private int costs[];
    private ArrayList<ArrayList<Integer>> solutions; // Temporario: Pega todas as possibilidades de caminhos e depois selecionamos o melhor deles
    private int bestSourceCity;

    public TravellingSalesman(XGrafo xGrafo) {
        this.xGrafo = xGrafo;
        solutions = new ArrayList<ArrayList<Integer>>();
        this.paths = new ArrayList<ArrayList<Integer>>();
        this.costs = new int[this.xGrafo.getNumVertices()];
    }

    public void bruteForceSolution() {
        int numberOfCities = this.xGrafo.getNumVertices();

        // Para cada cidade
        for(int i = 0; i < numberOfCities; i++) {
            ArrayList<Integer> al = new ArrayList<Integer>();

            this.bruteForceSolutionComplementation(i, al);

            Pair<Integer, Integer> p = minimumCostPath(this.solutions, i);

            this.paths.add(i, this.solutions.get(p.getFirst()));
            this.costs[i] = p.getSecond();

            this.solutions = new ArrayList<ArrayList<Integer>>();
        }

        bestSourceCity = obterMelhorCaminho();

        int lowestCost = this.costs[bestSourceCity];
        ArrayList<Integer> bestPath = this.paths.get(bestSourceCity);

        this.costs = new int[1];
        this.costs[0] = lowestCost;
        this.paths = new ArrayList<ArrayList<Integer>>();
        this.paths.add(bestPath);
    }

    public void teste(int sourceCity) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        
        this.bruteForceSolutionComplementation(sourceCity, al);

        for(ArrayList<Integer> arrayList : this.solutions) {
            printArrayList(arrayList);
        }

        removerArraylistRepetidos(solutions);
        System.out.println("xx");

        for(ArrayList<Integer> arrayList : this.solutions) {
            printArrayList(arrayList);
        }

        Pair<Integer, Integer> p = minimumCostPath(this.solutions, sourceCity);

        System.out.println(p.getFirst());
        System.out.println(p.getSecond());
        
        this.paths.add(sourceCity, this.solutions.get(p.getFirst()));
        this.costs[sourceCity] = p.getSecond();
    }

    private ArrayList<Integer> bruteForceSolutionComplementation(int sourceCity, ArrayList<Integer> al) {
        int numberOfCities = this.xGrafo.getNumVertices();
        int ultimoElementoAdicionado = -1;

        ArrayList<ArrayList<Integer>> allPossibilities = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> al2 = null;

        if(al.size() > 0)
            ultimoElementoAdicionado = al.get(al.size()-1);

        for(int i = 0; i < numberOfCities; i++) {
            al2 = new ArrayList<Integer>();
            al2.addAll(al);
            // Se nao for a cidade de origem, a colecao nao tiver a cidade e tiver ligacao entre o ultimoElementoAdicionado e i
            if((i != sourceCity) && (!al2.contains(i)) && ((al2.size() == 0) || this.xGrafo.getMat()[ultimoElementoAdicionado][i] != 0)) {
                al2.add(i);
                ArrayList<Integer> returned = bruteForceSolutionComplementation(sourceCity, al2);
                allPossibilities.add(returned);
            }
        }
    
        /*// Tirando nulos
        int allPossibilitiesSize = allPossibilities.size();
        for(int i = 0; i < allPossibilitiesSize; i++) {
            if(allPossibilities.get(i) == null) {
                allPossibilities.remove(i);
                i--;
                allPossibilitiesSize--;
            }
        }
        
        // Tirando arrays iguais
        for(int i = 0; i < allPossibilitiesSize; i++) {
            for(int j = i + 1; j < allPossibilitiesSize; j++) {
                if(allPossibilities.get(i).equals(allPossibilities.get(j))) {
                    allPossibilities.remove(j);
                    j--;
                    allPossibilitiesSize--;
                }
            }
        } */

        int which = -1, i = 0;
        for(ArrayList<Integer> arraylist : allPossibilities) {
            i++;
            if(arraylist == null)
                continue;
            which = i - 1;
        }

        if(which > -1)
            this.solutions.add(allPossibilities.get(which));

        

        int length = al2.size();
        if(length > 0) {
            ultimoElementoAdicionado = al2.get(length - 1);
            if((length == numberOfCities - 1) && (this.xGrafo.getMat()[ultimoElementoAdicionado][sourceCity] != 0)) {
                al2.add(sourceCity);
                return al2;
            }
        }

        return null;

        /*for(int i = 0; i < allPossibilitiesSize; i++) {
            ArrayList<Integer> array = allPossibilities.get(i);
            int length = array.size();
            ultimoElementoAdicionado = array.get(length - 1);

            if((length == numberOfCities - 1) && (this.xGrafo.getMat()[ultimoElementoAdicionado][sourceCity] != 0)) {
                array.add(sourceCity);
                return array;
            }
        }

        int option = minimumCostPath(allPossibilities, sourceCity);

        if(option != -1)
            return allPossibilities.get(option);

        return null;*/
    }

    public void printSolution() {
        System.out.println("O menor custo eh: " + this.costs[0] + "\n");

        System.out.println("O menor caminho eh: ");

        System.out.print("A partir da cidade " + bestSourceCity + " vai para cidade ");

        int numberOfCities = this.xGrafo.getNumVertices(), i = 0;
        for(Integer city : this.paths.get(0)) {
            i++;
            System.out.println(city);
            if(i != numberOfCities)
                System.out.print("Em seguida, vá para cidade ");
        }
    }

    // Pegar qual arraylist tem o menor caminho e o menor caminho
    public Pair<Integer, Integer> minimumCostPath(ArrayList<ArrayList<Integer>> al, int sourceCity) {
        int numberOfCities = this.xGrafo.getNumVertices();
        int minCost = Integer.MAX_VALUE;
        int temp = 0;
        int i = -1, returnValue = -1;
        
        for(ArrayList<Integer> values : al) { // Para cada arraylist
            i++;
            if((values == null) || (values.size() < numberOfCities - 1)) {
                continue;
            }
            temp = 0;    
            int previousCity = sourceCity;
            for(Integer value : values) {
                temp += this.xGrafo.getMat()[previousCity][value];
                previousCity = value;
            }
            if(temp < minCost) {
                minCost = temp;
                returnValue = i;
            }
        }
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(returnValue, minCost);
        return pair;
    }

    public boolean checkIfAnArrayListIsInAnArrayListOfArrayList(ArrayList<ArrayList<Integer>> arrayListOfArrayList, ArrayList<Integer> arrayList) {
        //System.out.println("igdsigldgslgldsglglsdglg");
        //System.out.println(arrayListOfArrayList.size());
        if(arrayListOfArrayList.size() == 0)
            return false;
        for(ArrayList<Integer> a : arrayListOfArrayList) {
            printArrayList(a);
            printArrayList(arrayList);
            if(a.equals(arrayList))
                return true;
        }
        //System.out.println("iuhsiahiuhiusahi");
        return false;
    }

    public void printArrayList(ArrayList<Integer> arrayList) {
        for(int value : arrayList) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void removerArraylistRepetidos(ArrayList<ArrayList<Integer>> arrayLists) {
        int size = arrayLists.size();

        for(int i = 0; i < size; i++) {
            ArrayList<Integer> array1 = arrayLists.get(i);
            for(int j = i + 1; j < size; j++) {
                ArrayList<Integer> array2 = arrayLists.get(j);
                if(array1.equals(array2)) {
                    arrayLists.remove(j);
                    j--;
                    size--;
                }
            }
        }
    }

    public int obterMelhorCaminho() {
        int choice = -1;
        int min = Integer.MAX_VALUE;
        int numberOfCities = this.xGrafo.getNumVertices();

        for(int i = 0; i < numberOfCities; i++) {
            if(min > this.costs[i]) {
                min = this.costs[i];
                choice = i;
            }
        }
        return choice;
    }

    public static int factorial(int n) {
        int result = n;

        if(n == 0 || n == 1)
            return 1;
        
        while(n > 1) {
            result *= (n - 1);
            n--;
        }
        return result;
    }
}
