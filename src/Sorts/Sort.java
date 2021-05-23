package Sorts;

import java.util.Vector;

public class Sort {
    Vector<Integer> data;

    public Sort(Vector<Integer> data) {
        this.data = data;
        System.out.println("Sorter data: " + data);
    }

    public String plainSortedData(){
        String cad = "";
        int charCounter = 0;
        for(int i = 0; i<data.size(); i++){
            if(i == data.size()-1){
                cad += data.get(i);
                charCounter += String.valueOf(data.get(i)).length();
            }
            else {
                cad += data.get(i) + ", ";
                charCounter += String.valueOf(data.get(i)).length() + 2;
            }
            if(charCounter >= 50){
                cad += "\n";
                charCounter = 0;
            }
        }
        return cad;
    }

    public void selectionSort(){
        Vector<Integer> currentData = new Vector<>(data);
        int aux = 0;
        while(aux < currentData.size()){
            int menor = 100000000, index = 0;
            for(int i = aux; i<currentData.size(); i++){
                if(currentData.get(i) < menor){
                    menor = currentData.get(i);
                    index = i;
                }
            }
            int swapA = currentData.get(aux);
            int swapB = currentData.get(index);
            currentData.set(aux, swapB);
            currentData.set(index, swapA);
            System.out.println(currentData);
            aux++;
        }
    }

    public void selectionSort(boolean reverse){
        Vector<Integer> currentData = new Vector<>(data);
        int aux = 0;
        while(aux < currentData.size()){
            int mayor = -100000000, index = 0;
            for(int i = aux; i<currentData.size(); i++){
                if(currentData.get(i) > mayor){
                    mayor = currentData.get(i);
                    index = i;
                }
            }
            int swapA = currentData.get(aux);
            int swapB = currentData.get(index);
            currentData.set(aux, swapB);
            currentData.set(index, swapA);
            System.out.println(currentData);
            aux++;
        }
    }

    public void insertionSort(){
        Vector<Integer> currentData = new Vector<>(data);
        int aux = 0;
        while(aux+1 < currentData.size()){
            int unsortedArrayData = currentData.get(aux + 1);
            int index = aux + 1;
            for(int i = aux; i>=0; i--){
                // System.out.println("Checkando... " + aux);
                // System.out.println("Unsorted array data: " + unsortedArrayData + "[" + index + "]");
                // System.out.println("Sorted array data: " + currentData.get(i) + "[" + i + "]");
                if (currentData.get(i) > unsortedArrayData){
                    int swapA = currentData.get(i);
                    int swapB = currentData.get(index);
                    // System.out.println("Cambiando... " + swapA + " <-> " + swapB);
                    currentData.set(i, swapB);
                    currentData.set(index, swapA);
                    index--;
                }
                else{
                    break;
                }
            }
            aux++;
            System.out.println(currentData);
        }
    }

    public void insertionSort(boolean reverse){
        Vector<Integer> currentData = new Vector<>(data);
        int aux = 0;
        while(aux+1 < currentData.size()){
            int unsortedArrayData = currentData.get(aux + 1);
            int index = aux + 1;
            for(int i = aux; i>=0; i--){
                // System.out.println("Checkando... " + aux);
                // System.out.println("Unsorted array data: " + unsortedArrayData + "[" + index + "]");
                // System.out.println("Sorted array data: " + currentData.get(i) + "[" + i + "]");
                if (currentData.get(i) < unsortedArrayData){
                    int swapA = currentData.get(i);
                    int swapB = currentData.get(index);
                    // System.out.println("Cambiando... " + swapA + " <-> " + swapB);
                    currentData.set(i, swapB);
                    currentData.set(index, swapA);
                    index--;
                }
                else{
                    break;
                }
            }
            aux++;
            System.out.println(currentData);
        }
    }

    public void shellSort(){
        Vector<Integer> currentData = new Vector<>(data);
        for(int salto = currentData.size()/2; salto > 0; salto /= 2){
            for(int i = salto; i<currentData.size(); i++){
                int aux = currentData.get(i);
                int j;
                for(j = i; j >= salto && currentData.get(j - salto) > aux; j -= salto){
                    currentData.set(j, currentData.get(j-salto));
                }
                currentData.set(j, aux);
            }
        }
        System.out.println(currentData);
    }

    public void shellSort(boolean reverse){
        Vector<Integer> currentData = new Vector<>(data);
        for(int salto = currentData.size()/2; salto > 0; salto /= 2){
            for(int i = salto; i<currentData.size(); i++){
                int aux = currentData.get(i);
                int j;
                for(j = i; j >= salto && currentData.get(j - salto) < aux; j -= salto){
                    currentData.set(j, currentData.get(j-salto));
                }
                currentData.set(j, aux);
            }
        }
        System.out.println(currentData);
    }

    public void mergeSort(Vector<Integer> currentData, int left, int right, boolean reverse){
        if(left >= right){
            return;
        }
        int m = left + (right - left) / 2;
        mergeSort(currentData, left, m, reverse);
        mergeSort(currentData, m + 1, right, reverse);
        merge(currentData, left, m, right, reverse);
        System.out.println(currentData);
    }

    private void merge(Vector<Integer> currentData, int left, int m, int right, boolean reverse){
        int i, j, k;
        int n1 = m - left + 1;
        int n2 = right - m;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for(i = 0; i<n1; i++){
            L[i] = currentData.get(left + i);
        }
        for(j = 0; j<n2; j++){
            R[j] = currentData.get(m + 1 + j);
        }

        i = 0; j = 0; k = left;
        while(i < n1 && j < n2){
            if(reverse) {
                if (L[i] >= R[j]) {
                    currentData.set(k, L[i]);
                    i++;
                } else {
                    currentData.set(k, R[j]);
                    j++;
                }
            }
            else{
                if (L[i] <= R[j]) {
                    currentData.set(k, L[i]);
                    i++;
                } else {
                    currentData.set(k, R[j]);
                    j++;
                }
            }
            k++;
        }

        while(i < n1){
            currentData.set(k, L[i]);
            i++;
            k++;
        }

        while(j < n2){
            currentData.set(k, R[j]);
            j++;
            k++;
        }
    }
}
