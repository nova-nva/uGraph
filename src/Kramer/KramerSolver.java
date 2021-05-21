package Kramer;

import java.util.Collections;
import java.util.Vector;

public class KramerSolver {
    private int[][] data;
    private int[] supplies, demands;
    private int rows, cols;
    private boolean minimize;

    private int totalAttribute;
    private int[][] solutionValues;
    private Vector<Vector<Integer>> solutionIndex;

    public KramerSolver(int[][] data, int[] supplies, int[] demands, boolean minimize) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
        this.supplies = supplies;
        this.demands = demands;
        this.minimize = minimize;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int[] getSupplies() {
        return supplies;
    }

    public void setSupplies(int[] supplies) {
        this.supplies = supplies;
    }

    public int[] getDemands() {
        return demands;
    }

    public void setDemands(int[] demands) {
        this.demands = demands;
    }

    public boolean isMinimize() {
        return minimize;
    }

    public void setMinimize(boolean minimize) {
        this.minimize = minimize;
    }

    public int getTotalAttribute() {
        return totalAttribute;
    }

    public void setTotalAttribute(int totalAttribute) {
        this.totalAttribute = totalAttribute;
    }

    public int[][] getSolutionValues() {
        return solutionValues;
    }

    public void setSolutionValues(int[][] solutionValues) {
        this.solutionValues = solutionValues;
    }

    public Vector<Vector<Integer>> getSolutionIndex() {
        return solutionIndex;
    }

    public void setSolutionIndex(Vector<Vector<Integer>> solutionIndex) {
        this.solutionIndex = solutionIndex;
    }

    public Vector<Vector<Integer>> solve(){
        // TODO: first solution (solucion noroeste)
        // current solution
        // initialize
        int[][] currentSolution = new int[rows][cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                currentSolution[i][j] = 0;
            }
        }

        // registrar valores de solucion y sus posiciones
        Vector<Vector<Integer>> currentSolutionIndex = new Vector<>();
        int i = 0, j = 0;
        while(true) {
            if (i == rows || j == cols)
                break;
            int selectedAttr = Math.min(supplies[i], demands[j]);
            Vector<Integer> index = new Vector<>();
            index.add(i);
            index.add(j);

            currentSolution[i][j] = selectedAttr;
            currentSolutionIndex.add(new Vector<>(index));

            supplies[i] -= selectedAttr;
            demands[j] -= selectedAttr;
            if (supplies[i] == 0)
                i++;
            else if (demands[j] == 0)
                j++;
        }

        System.out.println("NORTHWEST SOLUTION");
        print(currentSolution, rows, cols);
        System.out.println("Indices: " + currentSolutionIndex);
        System.out.println();

        // Generators
        Vector<Vector<Integer>> generators = getGenerators(data, currentSolutionIndex, rows, cols);
        Vector<Integer> verticalGenerator = generators.get(0);
        Vector<Integer> horizontalGenerator = generators.get(1);

        // create solution matrix
        int[][] solutionMatrix = getSolutionMatrix(verticalGenerator, horizontalGenerator);
        System.out.println("SOLUTION MATRIX");
        print(solutionMatrix, rows, cols);
        System.out.println();

        int[][] finalResult = matrixDiff(solutionMatrix, data, rows, cols);
        System.out.println("ANSWER");
        print(finalResult, rows, cols);
        System.out.println();

        if(!isIterable(finalResult, rows, cols, minimize)){
            System.out.println("Se encontro el resultado final");
            System.out.println("Indices: " + currentSolutionIndex);
            System.out.println("Total Attr: " + getTotalAttribute(currentSolution, data, currentSolutionIndex));
        }
        else{
            System.out.println("No se encontro el resultado final - debe iterarse");
        }
        System.out.println();

        int counter = 2;
        // iterate if necessary
        while(isIterable(finalResult, rows, cols, minimize)){
            // iterate
            // crear otro vector para que la funcion no afecte al actual en caso de ser inconsistente
            int[][] newPossibleSolution = powerIterate(finalResult, cloneMatrix(currentSolution, rows, cols), new Vector<>(currentSolutionIndex), rows, cols, minimize);
            if(newPossibleSolution == null)
                break;
            currentSolution = newPossibleSolution;

            // registrar valores de solucion y sus posiciones
            currentSolutionIndex = new Vector<>();
            for(int l = 0; l<rows; l++){
                for(int m = 0; m<cols; m++){
                    if(currentSolution[l][m] != 0){
                        Vector<Integer> index = new Vector<>();
                        index.add(l);
                        index.add(m);
                        currentSolutionIndex.add(index);
                    }
                }
            }

            System.out.println();
            System.out.println("SOLUTION " + counter + "...");
            print(currentSolution, rows, cols);
            System.out.println("Indices: " + currentSolutionIndex);
            System.out.println();

            // Generators
            generators = getGenerators(data, currentSolutionIndex, rows, cols);
            verticalGenerator = generators.get(0);
            horizontalGenerator = generators.get(1);
            System.out.println(verticalGenerator);
            System.out.println(horizontalGenerator);

            // create solution matrix
            solutionMatrix = getSolutionMatrix(verticalGenerator, horizontalGenerator);
            System.out.println("SOLUTION MATRIX");
            print(solutionMatrix, rows, cols);
            System.out.println();

            finalResult = matrixDiff(solutionMatrix, data, rows, cols);
            System.out.println("ANSWER");
            print(finalResult, rows, cols);
            System.out.println();

            counter++;
        }

        // Print Results
        System.out.println("Se encontro el resultado final");
        System.out.println("Indices: " + currentSolutionIndex);
        print(currentSolution, rows, cols);
        System.out.println("Total Attr: " + getTotalAttribute(currentSolution, data, currentSolutionIndex));

        // Set Values
        totalAttribute = getTotalAttribute(currentSolution, data, currentSolutionIndex);
        solutionValues = currentSolution;
        solutionIndex = currentSolutionIndex;

        // return indexes
        return currentSolutionIndex;
    }

    private int[][] cloneMatrix(int[][] original, int rows, int cols){
        int[][] cloned = new int[rows][cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                cloned[i][j] = original[i][j];
            }
        }
        return cloned;
    }

    private int[][] powerIterate(int[][] lastAnswer, int[][] originalLastSolution, Vector<Vector<Integer>> originalSolutionIndex, int rows, int cols, boolean minimize){
        // TODO: get new solution - parse X
        // encontrar numero por encima de la solucion
        int x = 0;
        Vector<Integer> xIndex = new Vector<>();
        boolean poligonalConsistency = true;

        // objetos para el registro y descarte de todas las soluciones posibles
        Vector<Vector<Integer>> blackList = new Vector<>();
        int maxPossibilitiesLength = originalSolutionIndex.size();

        Vector<Vector<Integer>> solutionIndex = null;
        int[][] lastSolution = null;
        do{
            if(blackList.size() == maxPossibilitiesLength)
                break;

            solutionIndex = new Vector<>(originalSolutionIndex);
            lastSolution = cloneMatrix(originalLastSolution, rows, cols);

            if(minimize) {
                // hallar el menor para que sea el x TODO: el x no es el menor!
                // checkar que la x elegida no forme parte de una columna gigante de mas de dos filas
                // hallar el mayor para que sea la opcion a cerear
                int xMayor = -1000000000;
                int iMayor = -1000000000;
                int jMayor = -1000000000;

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (lastAnswer[i][j] > 0) {
                            if (lastAnswer[i][j] > xMayor) {
                                if(blackList.size() == 0) {
                                    xMayor = lastAnswer[i][j];
                                    iMayor = i;
                                    jMayor = j;
                                }
                                else{
                                    boolean blacked = false;
                                    for(int efe = 0; efe<blackList.size(); efe++){
                                        if(i == blackList.get(efe).get(0) && j == blackList.get(efe).get(1))
                                            blacked = true;
                                    }
                                    if(!blacked){
                                        xMayor = lastAnswer[i][j];
                                        iMayor = i;
                                        jMayor = j;
                                    }
                                }
                            }
                        }
                    }
                }

                if(iMayor == -1000000000)
                    break;

                // TODO: obtener la posicion del mayor/menor elemento para hacerlo desaparecer
                xIndex.add(iMayor);
                xIndex.add(jMayor);
                solutionIndex.add(xIndex); // X se agrega como una solucion mas al problema
                System.out.println("X index: " + xIndex);
            }
            else{
                // si estamos maximizando...
                // hallar el menor para que sea el x TODO: el x no es el menor!
                // checkar que la x elegida no forme parte de una columna gigante de mas de dos filas
                // hallar el mayor para que sea la opcion a cerear
                int xMenor = 1000000000;
                int iMayor = 1000000000;
                int jMayor = 1000000000;

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (lastAnswer[i][j] < 0) {
                            if (lastAnswer[i][j] < xMenor) {
                                if(blackList.size() == 0) {
                                    xMenor = lastAnswer[i][j];
                                    iMayor = i;
                                    jMayor = j;
                                }
                                else{
                                    boolean blacked = false;
                                    for(int efe = 0; efe<blackList.size(); efe++){
                                        if(i == blackList.get(efe).get(0) && j == blackList.get(efe).get(1))
                                            blacked = true;
                                    }
                                    if(!blacked){
                                        xMenor = lastAnswer[i][j];
                                        iMayor = i;
                                        jMayor = j;
                                    }
                                }
                            }
                        }
                    }
                }
                if(iMayor == 1000000000)
                    break;

                // TODO: obtener la posicion del mayor/menor elemento para hacerlo desaparecer
                xIndex.clear();
                xIndex.add(iMayor);
                xIndex.add(jMayor);
                solutionIndex.add(xIndex); // X se agrega como una solucion mas al problema
                System.out.println("X index: " + xIndex);
            }

            //  TODO: encontrar X
            Vector<Integer> posiblesX = new Vector<>();
            // create boolean matrix
            boolean[][] booleanIteration = new boolean[rows][cols];
            for(int i = 0; i<rows; i++){
                for(int j = 0; j<cols; j++){
                    if(i == xIndex.get(0) && j == xIndex.get(1)){
                        booleanIteration[i][j] = true;
                        continue;
                    }
                    booleanIteration[i][j] = false;
                }
            }
            print(booleanIteration, rows, cols);

            // igualar las ecuaciones matriciales
            // aqui nos aseguramos que cada true tenga dos Trues colindantes a sus lados
            boolean end = false; // general flag
            boolean sign = false; // false -> - ; true -> +

            System.out.println("Last Solution: ");
            print(lastSolution, rows, cols);

            boolean zeroConsistency = false;
            poligonalConsistency = true;
            while(!end){
                // check boolean consistency
                boolean consistent = true;
                boolean horizontalFlag = true, verticalFlag = true;
                for(int i = 0; i<rows; i++){
                    for(int j = 0; j<cols; j++){
                        if(booleanIteration[i][j]){
                            // si es verdadero hemos encontrado un atributo true, entonces hay que checkar que tenga dos colindantes true
                            // si los tiene, saltamos al siguiente
                            Vector<Integer> position = new Vector<>();
                            position.add(i);
                            position.add(j);
                            Vector<Vector<Integer>> fila = new Vector<>();
                            Vector<Vector<Integer>> columna = new Vector<>();

                            System.out.println("Comprobando " + position + "...");
                            // buscamos los atributos de su fila y columna
                            for(int a = 0; a<solutionIndex.size(); a++){
                                if(solutionIndex.get(a).get(0) == position.get(0))
                                    fila.add(solutionIndex.get(a));
                                if(solutionIndex.get(a).get(1) == position.get(1))
                                    columna.add(solutionIndex.get(a));
                            }
                            System.out.println(fila);
                            System.out.println(columna);

                            // TODO: fila check
                            boolean posibleFila = false;

                            // si en la fila existe otro bit encendido aparte del actual, podemos saltar el proceso de encendido ya que no es necesario
                            for(int a = 0; a<fila.size(); a++){
                                if(position.get(1) != fila.get(a).get(1)) {
                                    if (booleanIteration[fila.get(a).get(0)][fila.get(a).get(1)]) {
                                        posibleFila = true;
                                        break;
                                    }
                                }
                            }

                            if(!posibleFila){
                                // si luego de checar la fila aun no encontramos nada, entonces debemos encender uno nosotros
                                horizontalFlag = false;
                                consistent = false;

                                // se encendera una opcion si y solo si tiene otra opcion colindante tanto horizontal como verticalmente
                                for(int a = 0; a<fila.size(); a++){
                                    System.out.println("ando en... " + fila.get(a));
                                    if(position.get(1) != fila.get(a).get(1)) {
                                        // si la posicion es diferente a la actual, podemos intentar
                                        Vector<Vector<Integer>> currentFila = new Vector<>();
                                        Vector<Vector<Integer>> currentColumna = new Vector<>();

                                        // buscamos los atributos de su fila y columna
                                        System.out.println("Opcion " + fila.get(a).get(0) + "-"+ fila.get(a).get(1) + "...");
                                        for(int z = 0; z<solutionIndex.size(); z++){
                                            if(solutionIndex.get(z).get(0) == fila.get(a).get(0))
                                                currentFila.add(solutionIndex.get(z));
                                            if(solutionIndex.get(z).get(1) == fila.get(a).get(1))
                                                currentColumna.add(solutionIndex.get(z));
                                        }

                                        System.out.println(currentFila);
                                        System.out.println(currentColumna);

                                        if(currentFila.size() > 1 && currentColumna.size() > 1){
                                            // si es mayor a uno, existen las dos opciones. podemos encender este valor
                                            booleanIteration[fila.get(a).get(0)][fila.get(a).get(1)] = true;
                                            posibleFila = true;
                                            System.out.println("Acabamos de encender " + fila.get(a) + " papaaa, solucionazaa ----- signo: " + sign);
                                            if(!sign){
                                                posiblesX.add(lastSolution[fila.get(a).get(0)][fila.get(a).get(1)]);
                                            }
                                            break;
                                        }
                                        // caso contrario, se sigue iterando hasta encontrar solucion
                                    }
                                }
                            }

                            if(!posibleFila){
                                // si a pesar de esto no hay una opcion encendida o a encender, es gg
                                System.out.println("Se cago we, no hay opciones posibles en la fila");
                                int[][] f = new int[rows][cols];
                                poligonalConsistency = false;
                                break;
                                // return f;
                            }

                            // TODO: columna check
                            boolean posibleColumna = false;

                            // si en la columna existe otro bit encendido aparte del actual, podemos saltar el proceso de encendido ya que no es necesario
                            for(int a = 0; a<columna.size(); a++){
                                if(position.get(0) != columna.get(a).get(0)) {
                                    if (booleanIteration[columna.get(a).get(0)][columna.get(a).get(1)]) {
                                        posibleColumna = true;
                                        break;
                                    }
                                }
                            }

                            if(!posibleColumna){
                                // si luego de checar la columna aun no encontramos nada, entonces debemos encender uno nosotros
                                verticalFlag = false;
                                consistent = false;

                                // se encendera una opcion si y solo si tiene otra opcion colindante tanto horizontal como verticalmente
                                for(int a = 0; a<columna.size(); a++){
                                    System.out.println("ando en... " + columna.get(a));
                                    if(position.get(0) != columna.get(a).get(0)) {
                                        // si la posicion es diferente a la actual, podemos intentar
                                        Vector<Vector<Integer>> currentFila = new Vector<>();
                                        Vector<Vector<Integer>> currentColumna = new Vector<>();

                                        // buscamos los atributos de su fila y columna
                                        System.out.println("Opcion " + columna.get(a).get(0) + "-"+ columna.get(a).get(1) + "...");
                                        for(int z = 0; z<solutionIndex.size(); z++){
                                            if(solutionIndex.get(z).get(0) == columna.get(a).get(0))
                                                currentFila.add(solutionIndex.get(z));
                                            if(solutionIndex.get(z).get(1) == columna.get(a).get(1))
                                                currentColumna.add(solutionIndex.get(z));
                                        }

                                        System.out.println(currentFila);
                                        System.out.println(currentColumna);

                                        if(currentFila.size() > 1 && currentColumna.size() > 1){
                                            // si es mayor a uno, existen las dos opciones. podemos encender este valor
                                            booleanIteration[columna.get(a).get(0)][columna.get(a).get(1)] = true;
                                            posibleColumna = true;
                                            System.out.println("Acabamos de encender " + columna.get(a) + " papaaa, solucionazaa ----- signo: " + sign);
                                            if(!sign){
                                                posiblesX.add(lastSolution[columna.get(a).get(0)][columna.get(a).get(1)]);
                                            }
                                            break;
                                        }
                                        // caso contrario, se sigue iterando hasta encontrar solucion
                                    }
                                }
                            }

                            if(!posibleColumna){
                                // si a pesar de esto no hay una opcion encendida o a encender, es gg
                                System.out.println("Se cago we, no hay opciones posibles en la columna");
                                int[][] f = new int[rows][cols];
                                poligonalConsistency = false;
                                // return f;
                                break;
                            }

                        }
                        if(!poligonalConsistency)
                            break;
                        if(!horizontalFlag || !verticalFlag) {
                            sign = !sign;
                            break;
                        }
                    }
                    if(!poligonalConsistency)
                        break;
                    if(!horizontalFlag || !verticalFlag)
                        break;
                }
                if(!poligonalConsistency)
                    end = true;
                if (consistent)
                    end = true;
                System.out.println();
            }
            x = Collections.min(posiblesX);
            System.out.println("X: " + x);

            if(!poligonalConsistency){
                Vector<Integer> blackIndex = new Vector<>();
                blackIndex.add(xIndex.get(0));
                blackIndex.add(xIndex.get(1));
                blackList.add(blackIndex);
                System.out.println("Lista negra: " + blackList);
            }

        }while(!poligonalConsistency || blackList.size() == maxPossibilitiesLength);
        if (!poligonalConsistency){
            return null;
        }

        // TODO: operar los datos con la X recien hallada
        // create boolean matrix
        boolean[][] booleanIteration = new boolean[rows][cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if(i == xIndex.get(0) && j == xIndex.get(1)){
                    booleanIteration[i][j] = true;
                    continue;
                }
                booleanIteration[i][j] = false;
            }
        }
        print(booleanIteration, rows, cols);

        // igualar las ecuaciones matriciales
        // aqui nos aseguramos que cada true tenga dos Trues colindantes a sus lados
        boolean end = false; // general flag
        boolean sign = false; // false -> - ; true -> +

        System.out.println("Last Solution: ");
        print(lastSolution, rows, cols);

        boolean zeroConsistency = false;
        poligonalConsistency = true;
        while(!end){
            // check boolean consistency
            boolean consistent = true;
            boolean horizontalFlag = true, verticalFlag = true;
            for(int i = 0; i<rows; i++){
                for(int j = 0; j<cols; j++){
                    if(booleanIteration[i][j]){
                        // si es verdadero hemos encontrado un atributo true, entonces hay que checkar que tenga dos colindantes true
                        // si los tiene, saltamos al siguiente
                        Vector<Integer> position = new Vector<>();
                        position.add(i);
                        position.add(j);
                        Vector<Vector<Integer>> fila = new Vector<>();
                        Vector<Vector<Integer>> columna = new Vector<>();

                        System.out.println("Comprobando " + position + "...");
                        // buscamos los atributos de su fila y columna
                        for(int a = 0; a<solutionIndex.size(); a++){
                            if(solutionIndex.get(a).get(0) == position.get(0))
                                fila.add(solutionIndex.get(a));
                            if(solutionIndex.get(a).get(1) == position.get(1))
                                columna.add(solutionIndex.get(a));
                        }
                        System.out.println(fila);
                        System.out.println(columna);

                        // TODO: fila check
                        boolean posibleFila = false;

                        // si en la fila existe otro bit encendido aparte del actual, podemos saltar el proceso de encendido ya que no es necesario
                        for(int a = 0; a<fila.size(); a++){
                            if(position.get(1) != fila.get(a).get(1)) {
                                if (booleanIteration[fila.get(a).get(0)][fila.get(a).get(1)]) {
                                    posibleFila = true;
                                    break;
                                }
                            }
                        }

                        if(!posibleFila){
                            // si luego de checar la fila aun no encontramos nada, entonces debemos encender uno nosotros
                            horizontalFlag = false;
                            consistent = false;

                            // se encendera una opcion si y solo si tiene otra opcion colindante tanto horizontal como verticalmente
                            for(int a = 0; a<fila.size(); a++){
                                System.out.println("ando en... " + fila.get(a));
                                if(position.get(1) != fila.get(a).get(1)) {
                                    // si la posicion es diferente a la actual, podemos intentar
                                    Vector<Vector<Integer>> currentFila = new Vector<>();
                                    Vector<Vector<Integer>> currentColumna = new Vector<>();

                                    // buscamos los atributos de su fila y columna
                                    System.out.println("Opcion " + fila.get(a).get(0) + "-"+ fila.get(a).get(1) + "...");
                                    for(int z = 0; z<solutionIndex.size(); z++){
                                        if(solutionIndex.get(z).get(0) == fila.get(a).get(0))
                                            currentFila.add(solutionIndex.get(z));
                                        if(solutionIndex.get(z).get(1) == fila.get(a).get(1))
                                            currentColumna.add(solutionIndex.get(z));
                                    }

                                    System.out.println(currentFila);
                                    System.out.println(currentColumna);

                                    if(currentFila.size() > 1 && currentColumna.size() > 1){
                                        // si es mayor a uno, existen las dos opciones. podemos encender este valor
                                        booleanIteration[fila.get(a).get(0)][fila.get(a).get(1)] = true;
                                        posibleFila = true;
                                        System.out.println("Acabamos de encender " + fila.get(a) + " papaaa, solucionazaa ----- signo: " + sign);
                                        if(sign) {
                                            lastSolution[fila.get(a).get(0)][fila.get(a).get(1)] += x;
                                            if(lastSolution[fila.get(a).get(0)][fila.get(a).get(1)] == 0)
                                                zeroConsistency = true;
                                        }
                                        else {
                                            // System.out.println("Signo menos perrraaaaaaaaaas");
                                            // System.out.println("Operacion: " + lastSolution[fila.get(a).get(0)][fila.get(a).get(1)] +" - " +x);
                                            lastSolution[fila.get(a).get(0)][fila.get(a).get(1)] -= x;
                                            if(lastSolution[fila.get(a).get(0)][fila.get(a).get(1)] == 0)
                                                zeroConsistency = true;
                                            // System.out.println("Resultado: " + lastSolution[fila.get(a).get(0)][fila.get(a).get(1)]);

                                        }
                                        break;
                                    }
                                    // caso contrario, se sigue iterando hasta encontrar solucion
                                }
                            }
                        }

                        if(!posibleFila){
                            // si a pesar de esto no hay una opcion encendida o a encender, es gg
                            System.out.println("Se cago we, no hay opciones posibles en la fila");
                            int[][] f = new int[rows][cols];
                            poligonalConsistency = false;
                            // return f;
                        }

                        // TODO: columna check
                        boolean posibleColumna = false;

                        // si en la columna existe otro bit encendido aparte del actual, podemos saltar el proceso de encendido ya que no es necesario
                        for(int a = 0; a<columna.size(); a++){
                            if(position.get(0) != columna.get(a).get(0)) {
                                if (booleanIteration[columna.get(a).get(0)][columna.get(a).get(1)]) {
                                    posibleColumna = true;
                                    break;
                                }
                            }
                        }

                        if(!posibleColumna){
                            // si luego de checar la columna aun no encontramos nada, entonces debemos encender uno nosotros
                            verticalFlag = false;
                            consistent = false;

                            // se encendera una opcion si y solo si tiene otra opcion colindante tanto horizontal como verticalmente
                            for(int a = 0; a<columna.size(); a++){
                                System.out.println("ando en... " + columna.get(a));
                                if(position.get(0) != columna.get(a).get(0)) {
                                    // si la posicion es diferente a la actual, podemos intentar
                                    Vector<Vector<Integer>> currentFila = new Vector<>();
                                    Vector<Vector<Integer>> currentColumna = new Vector<>();

                                    // buscamos los atributos de su fila y columna
                                    System.out.println("Opcion " + columna.get(a).get(0) + "-"+ columna.get(a).get(1) + "...");
                                    for(int z = 0; z<solutionIndex.size(); z++){
                                        if(solutionIndex.get(z).get(0) == columna.get(a).get(0))
                                            currentFila.add(solutionIndex.get(z));
                                        if(solutionIndex.get(z).get(1) == columna.get(a).get(1))
                                            currentColumna.add(solutionIndex.get(z));
                                    }

                                    System.out.println(currentFila);
                                    System.out.println(currentColumna);

                                    if(currentFila.size() > 1 && currentColumna.size() > 1){
                                        // si es mayor a uno, existen las dos opciones. podemos encender este valor
                                        booleanIteration[columna.get(a).get(0)][columna.get(a).get(1)] = true;
                                        posibleColumna = true;
                                        System.out.println("Acabamos de encender " + columna.get(a) + " papaaa, solucionazaa ----- signo: " + sign);
                                        if(sign) {
                                            lastSolution[columna.get(a).get(0)][columna.get(a).get(1)] += x;
                                            if(lastSolution[columna.get(a).get(0)][columna.get(a).get(1)] == 0)
                                                zeroConsistency = true;
                                        }
                                        else {
                                            // System.out.println("Signo menos perrraaaaaaaaaas");
                                            // System.out.println("Operacion: " + lastSolution[columna.get(a).get(0)][columna.get(a).get(1)] +" - " +x);
                                            lastSolution[columna.get(a).get(0)][columna.get(a).get(1)] -= x;
                                            if(lastSolution[columna.get(a).get(0)][columna.get(a).get(1)] == 0)
                                                zeroConsistency = true;
                                            // System.out.println("Resultado: " + lastSolution[columna.get(a).get(0)][columna.get(a).get(1)]);
                                        }
                                        break;
                                    }
                                    // caso contrario, se sigue iterando hasta encontrar solucion
                                }
                            }
                        }

                        if(!posibleColumna){
                            // si a pesar de esto no hay una opcion encendida o a encender, es gg
                            System.out.println("Se cago we, no hay opciones posibles en la columna");
                            int[][] f = new int[rows][cols];
                            poligonalConsistency = false;
                            // return f;
                        }

                    }
                    if(!horizontalFlag || !verticalFlag) {
                        sign = !sign;
                        break;
                    }
                }
                if(!horizontalFlag || !verticalFlag)
                    break;
            }
            if (consistent)
                end = true;
            System.out.println();
        }
        // finalmente, reemplazamos x donde se debe
        if(!zeroConsistency) {
            System.out.println("No tiene consistencia de ceros");
            return null;
        }
        if(!poligonalConsistency) {
            System.out.println("No tiene consistencia poligonal");
            return null;
        }
        lastSolution[xIndex.get(0)][xIndex.get(1)] = x;
        print(booleanIteration, rows, cols);
        print(lastSolution, rows, cols);
        return lastSolution;
    }

    private int getTotalAttribute(int[][] solution, int[][] generalData, Vector<Vector<Integer>> indexes){
        int total = 0;
        for(int i = 0; i<indexes.size(); i++){
            total += (solution[indexes.get(i).get(0)][indexes.get(i).get(1)] * generalData[indexes.get(i).get(0)][indexes.get(i).get(1)]);
        }
        return total;
    }

    private boolean isIterable(int[][] matrix, int rows, int cols, boolean minimize){
        if(minimize) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (matrix[i][j] > 0)
                        return true;
                }
            }
            return false;
        }
        else{
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (matrix[i][j] < 0)
                        return true;
                }
            }
            return false;
        }
    }

    private int[][] getSolutionMatrix(Vector<Integer> verticalGenerator, Vector<Integer> horizontalGenerator){
        int[][] container = new int[verticalGenerator.size()][horizontalGenerator.size()];
        for(int i = 0; i<verticalGenerator.size(); i++){
            for(int j = 0; j<horizontalGenerator.size(); j++){
                container[i][j] = verticalGenerator.get(i) + horizontalGenerator.get(j);
            }
        }
        return container;
    }

    private int[][] matrixDiff (int[][] first, int[][] second, int rows, int cols){
        // restar first - second
        int[][] solution = new int[rows][cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                solution[i][j] = first[i][j] - second[i][j];
            }
        }
        return solution;
    }

    private Vector<Vector<Integer>> getGenerators(int[][] data, Vector<Vector<Integer>> solutionIndex, int rows, int cols){
        // get solutions from general matrix
        Vector<Integer> currentSolution = new Vector<>();
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                for(int sIndex = 0; sIndex<solutionIndex.size(); sIndex++){
                    if(i == solutionIndex.get(sIndex).get(0) && j == solutionIndex.get(sIndex).get(1)){
                        currentSolution.add(data[i][j]);
                        break;
                    }
                }
            }
        }
        System.out.println(currentSolution);

        // init control variables
        int i = 0, j = 0; // i[0] -> menor valor
        boolean avance = true; // false -> avanzar por j; true -> avanzar por i
        int cambioRumbo = 0;

        Vector<Vector<Integer>> generators = new Vector<>();
        generators.add(new Vector<>()); // [0] -> verticales
        generators.add(new Vector<>()); // [1] -> horizontales

        // ciclo 2.0
        while (i != rows || j != cols) {
            // Encontrar Generadores
            if(i == 0 && j == 0){
                // si es el primer generador de todos, se le asigna el menor numero de todos
                generators.get(0).add(Collections.min(currentSolution));
                cambioRumbo = 2;
            }
            else {
                // si no es el primero, se inicia el proceso general para extraer un generador
                if(avance){
                    // case: encontrar generador en recorrido vertical (i)
                    for (int x = 0; x < solutionIndex.size(); x++) {
                        if (solutionIndex.get(x).get(0) == i) {
                            generators.get(0).add(currentSolution.get(x));
                            cambioRumbo++;
                            break;
                        }
                    }
                }
                else{
                    // case: encontrar generador en recorrido horizontal (j)
                    for (int x = 0; x < solutionIndex.size(); x++) {
                        if (solutionIndex.get(x).get(1) == j) {
                            generators.get(1).add(currentSolution.get(x));
                            cambioRumbo++;
                            break;
                        }
                    }
                }
            }

            // Restar Valores
            if(avance){
                // case: restar generador con toda la fila a la que pertenece
                for (int x = 0; x < solutionIndex.size(); x++) {
                    if (solutionIndex.get(x).get(0) != i)
                        continue;
                    currentSolution.set(x, currentSolution.get(x) - generators.get(0).get(i));
                }
            }
            else{
                // case: restar generador con toda la columna a la que pertenece
                for (int x = 0; x < solutionIndex.size(); x++) {
                    if (solutionIndex.get(x).get(1) != j)
                        continue;
                    currentSolution.set(x, currentSolution.get(x) - generators.get(1).get(j));
                }
            }

            // Incrementar indices - cambiar de rumbo
            if (avance) {
                i++;
            }
            else {
                j++;
            }
            if(i == 1) {
                // case: nivelar los generadores opuestos al primer generador
                if (cambioRumbo == 2) {
                    avance = !avance;
                    cambioRumbo = 0;
                }
            }
            else{
                // case: generadores normales
                if (cambioRumbo == 1) {
                    avance = !avance;
                    cambioRumbo = 0;
                }
            }
            System.out.println("Generators: " + generators);
            System.out.println("Solution: " + currentSolution);
        }
        boolean direction = true; // true -> horizontal; false -> vertical
        while(!validateGenerators(currentSolution)){
            System.out.println("Los generadores no son correctos");
            generators = fixGenerators(generators, currentSolution, solutionIndex);
            direction = !direction;
        }
        return generators;
    }

    private Vector<Vector<Integer>> fixGenerators(Vector<Vector<Integer>> generators, Vector<Integer> currentSolution, Vector<Vector<Integer>> solutionIndex){
        boolean direction = true; // true -> horizontal; false -> vertical

        // clonamos los datos para mantener los iniciales intactos
        Vector<Vector<Integer>> generatorsClone = new Vector<>(generators);
        Vector<Integer> solutionClone = new Vector<>(currentSolution);

        for(int i = 0; i<currentSolution.size(); i++){
            if(currentSolution.get(i) != 0){
                // tomamos los sub indices
                int x = solutionIndex.get(i).get(0);
                int y = solutionIndex.get(i).get(1);

                // tomamos el dato a operar
                int dato = currentSolution.get(i);

                // controlador de correcciones
                boolean end = false;

                while(!end) {
                    end = true;
                    if (direction) {
                        // horizontal
                        // obtenemos de los generadores verticales el de nuestra fila
                        generatorsClone.get(0).set(x, generatorsClone.get(0).get(x) + dato);

                        // operar todos los datos de la misma fila
                        int newDato = 0, newX = 0, newY = 0;
                        for (int a = 0; a < currentSolution.size(); a++) {
                            if (solutionIndex.get(a).get(0) == x) {
                                currentSolution.set(a, currentSolution.get(a) - dato);
                                if (currentSolution.get(a) != 0) {
                                    // comprobamos el dato recien cambiado
                                    // si alguno de los datos operados no se iguala a cero, aun no terminamos
                                    System.out.println("no terminamos we");
                                    newX = solutionIndex.get(a).get(0);
                                    newY = solutionIndex.get(a).get(1);
                                    newDato = currentSolution.get(a);
                                    end = false;
                                }
                            }
                        }
                        dato = newDato;
                        x = newX;
                        y = newY;
                    } else {
                        // vertical
                        // obtenemos de los generadores horizontales de nuestra columna
                        generatorsClone.get(1).set(y, generatorsClone.get(1).get(y) + dato);

                        // operar todos los datos de la misma columna
                        int newDato = 0, newX = 0, newY = 0;
                        for (int a = 0; a < currentSolution.size(); a++) {
                            if (solutionIndex.get(a).get(1) == y) {
                                currentSolution.set(a, currentSolution.get(a) - dato);
                                if (currentSolution.get(a) != 0) {
                                    // comprobamos el dato recien cambiado
                                    // si alguno de los datos operados no se iguala a cero, aun no terminamos
                                    newX = solutionIndex.get(a).get(0);
                                    newY = solutionIndex.get(a).get(1);
                                    newDato = currentSolution.get(a);
                                    end = false;
                                }
                            }
                        }
                        dato = newDato;
                        x = newX;
                        y = newY;
                    }
                    direction = !direction;
                    System.out.println("Generadores corregidos: " + generators);
                    System.out.println("Solucion corregida: " + currentSolution);
                }
            }
        }
        return generators;
    }

    private boolean validateGenerators(Vector<Integer> solution){
        for (int number: solution) {
            if(number != 0)
                return false;
        }
        return true;
    }

    private void print(int[][] data, int rows, int cols){
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private void print(boolean[][] data, int rows, int cols){
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
