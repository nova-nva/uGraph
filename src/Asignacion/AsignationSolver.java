package Asignacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class AsignationSolver {
    private int[][] matrix;
    private int dimension;

    public AsignationSolver(int[][] matrix) {
        this.matrix = matrix;
        dimension = matrix.length;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    private Vector<Vector<Vector<Integer>>> simpleSearch(int[][] data){
        // find posibilities (horizontal only)
        Vector<Vector<Integer>> singlePosibilities = new Vector<>();
        Vector<Vector<Vector<Integer>>> singlePosibilitiesIndex = new Vector<>();
        boolean flag;
        for(int i = 0; i<dimension; i++){
            Vector<Integer> option  = new Vector<>();
            Vector<Vector<Integer>> optionIndex = new Vector<>();
            flag = false;
            for(int j = 0; j<dimension; j++){
                Vector<Integer> index = new Vector<>();
                index.add(i);
                index.add(j);
                if(data[i][j] != 0){
                    flag = true;
                    option.add(data[i][j]);
                    optionIndex.add(new Vector<>(index));
                }
                if ((data[i][j] == 0 && flag) || (flag && j == dimension-1)){ // si se cambia la dimension cambiar el parametro a vertical
                    singlePosibilities.add(new Vector<>(option));
                    singlePosibilitiesIndex.add(new Vector<>(optionIndex));
                    option.clear();
                    optionIndex.clear();
                    flag = false;
                }
                index.clear();
            }
        }
        System.out.println("Total de posibilidades: " + singlePosibilities.size());
        System.out.println("Horizontal Posibilites: ");
        for(int i = 0; i<singlePosibilities.size(); i++){
            System.out.println(singlePosibilities.get(i));
        }
        System.out.println("Posibilites' Indexes:");
        for(int i = 0; i<singlePosibilitiesIndex.size(); i++){
            System.out.println(singlePosibilitiesIndex.get(i));
        }
        System.out.println();
        return singlePosibilitiesIndex;
    }

    private int[][] maximize(int[][] data){
        // max per columns
        int[] maxColumn = new int[dimension];
        for(int i = 0; i<dimension; i++){
            maxColumn[i] = -1000000;
        }

        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                if(data[j][i] > maxColumn[i]){
                    maxColumn[i] = data[j][i];
                }
            }
        }

        /*
        for(int i = 0; i<dimension; i++){
            System.out.print(maxColumn[i] + " ");
        }
        System.out.println();
         */

        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                data[j][i] -= maxColumn[i];
            }
        }

        // max per rows
        int[] maxRow = new int[dimension];
        for(int i = 0; i<dimension; i++){
            maxRow[i] = -1000000;
        }

        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                if(data[i][j] > maxRow[i]){
                    maxRow[i] = data[i][j];
                }
            }
        }

        /*
        for(int i = 0; i<dimension; i++){
            System.out.print(maxRow[i] + " ");
        }
        System.out.println();
         */

        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                data[i][j] -= maxRow[i];
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }

        return data;
    }

    public Vector<Vector<Integer>> solve(){
        // initialize matrixes by cloning original matrix
        int dimension = getDimension();
        int[][] posibility = new int[dimension][dimension];
        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                posibility[i][j] = matrix[i][j];
            }
        }

        // search for solutions
        int solutions = 0;
        Vector<Vector<Integer>> index = new Vector<>();
        boolean[][] currentDataAccess = new boolean[dimension][dimension];

        // initialize booleans
        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                currentDataAccess[i][j] = true;
            }
        }

        solutions = 0;
        posibility = maximize(posibility);

        // count solutions
        for (int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                if(!currentDataAccess[i][j])
                    continue;

                Vector<Integer> currentIndex = new Vector<>();
                currentIndex.add(i);
                currentIndex.add(j);

                if(posibility[i][j] == 0){
                    solutions++;
                    index.add(new Vector<>(currentIndex));

                    // kill other options in the row
                    for(int x = 0; x<dimension; x++){
                        currentDataAccess[i][x] = false;
                    }

                    // kill other options in the row
                    for(int x = 0; x<dimension; x++){
                        currentDataAccess[x][j] = false;
                    }
                }
                currentIndex.clear();
            }
        }

        System.out.println(index);
        System.out.println("Solutions: " + solutions);
        System.out.println("Dimension: " + dimension);

        // si no son iguales, se iteran
        while(solutions < dimension){
            // iteration process
            Vector<Vector<Vector<Integer>>> singlePosibilitiesIndex = simpleSearch(posibility);
            int maxPosibiltySize = 0;
            for(int i = 0; i<singlePosibilitiesIndex.size(); i++){
                if(maxPosibiltySize < singlePosibilitiesIndex.get(i).size()){
                    maxPosibiltySize = singlePosibilitiesIndex.get(i).size();
                }
            }
            System.out.println("Max size para la iteracion: " + maxPosibiltySize);

            // select our iterative section
            Vector<Vector<Integer>> currentIteration = new Vector<>();
            for(int i = 0; i<singlePosibilitiesIndex.size(); i++){
                if(maxPosibiltySize == singlePosibilitiesIndex.get(i).size()){
                    currentIteration = singlePosibilitiesIndex.get(i);
                    break;
                }
            }
            System.out.println("Se va a iterar: " + currentIteration);
            Vector<Integer> currentIterationRows = new Vector<>();
            Vector<Integer> currentIterationColumns = new Vector<>();
            for (int i = 0; i<currentIteration.size(); i++){
                currentIterationRows.add(currentIteration.get(i).get(0));
                currentIterationColumns.add(currentIteration.get(i).get(1));
            }
            System.out.println("Filas: " + currentIterationRows);
            System.out.println("Columnas: " + currentIterationColumns);

            // transform our matrix with new operations
            ArrayList<Integer> iteratedSection = new ArrayList<>();
            for(int i = 0; i<currentIteration.size(); i++){
                iteratedSection.add(posibility[currentIteration.get(i).get(0)][currentIteration.get(i).get(1)]);
            }
            System.out.println(iteratedSection);
            int max = Collections.max(iteratedSection);
            int diag = -max;

            for (int i = 0; i<dimension; i++){
                for (int j = 0; j<dimension; j++){
                    if(currentIterationRows.contains(i)){ // si esta en la misma fila...
                        if(currentIterationColumns.contains(j)){ // y esta en la misma columna
                            // estamos en la sub matriz iterada
                            posibility[i][j] -= max;
                        }
                    }
                    else{
                        if(!currentIterationColumns.contains(j)){ // si no contiene las columnas victima
                            // estamos en la diagonal
                            posibility[i][j] -= diag;
                        }
                    }
                }
            }

            for (int i = 0; i<dimension; i++){
                for (int j = 0; j<dimension; j++){
                    System.out.print(posibility[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

            // TODO: se vuelve a maximizar
            currentDataAccess = new boolean[dimension][dimension];
            for(int i = 0; i<dimension; i++){
                for(int j = 0; j<dimension; j++){
                    currentDataAccess[i][j] = true;
                }
            }

            // find solution - option 1
            solutions = 0;
            index = new Vector<>();
            posibility = maximize(posibility);

            for (int i = 0; i<dimension; i++){
                for(int j = 0; j<dimension; j++){
                    if(!currentDataAccess[i][j])
                        continue;

                    Vector<Integer> currentIndex = new Vector<>();
                    currentIndex.add(i);
                    currentIndex.add(j);

                    if(posibility[i][j] == 0){
                        solutions++;
                        index.add(new Vector<>(currentIndex));

                        // kill other options in the row
                        for(int x = 0; x<dimension; x++){
                            currentDataAccess[i][x] = false;
                        }

                        // kill other options in the row
                        for(int x = 0; x<dimension; x++){
                            currentDataAccess[x][j] = false;
                        }
                    }
                    currentIndex.clear();
                }
            }

            System.out.println("1st Solution Option");
            System.out.println(index);
            System.out.println("Solutions: " + solutions);
            System.out.println("Dimension: " + dimension);
            if(solutions == dimension)
                break;


            // find solution - option 2
            currentDataAccess = new boolean[dimension][dimension];
            for(int i = 0; i<dimension; i++){
                for(int j = 0; j<dimension; j++){
                    currentDataAccess[i][j] = true;
                }
            }

            solutions = 0;
            index = new Vector<>();

            for (int i = dimension-1; i>=0; i--){
                for(int j = dimension-1; j>=0; j--){
                    if(!currentDataAccess[i][j])
                        continue;

                    Vector<Integer> currentIndex = new Vector<>();
                    currentIndex.add(i);
                    currentIndex.add(j);

                    if(posibility[i][j] == 0){
                        solutions++;
                        index.add(new Vector<>(currentIndex));

                        // kill other options in the row
                        for(int x = 0; x<dimension; x++){
                            currentDataAccess[i][x] = false;
                        }

                        // kill other options in the row
                        for(int x = 0; x<dimension; x++){
                            currentDataAccess[x][j] = false;
                        }
                    }
                    currentIndex.clear();
                }
            }

            System.out.println("2nd Solution Option");
            System.out.println(index);
            System.out.println("Solutions: " + solutions);
            System.out.println("Dimension: " + dimension);
            if(solutions == dimension)
                break;
        }
        return index;
    }
}
