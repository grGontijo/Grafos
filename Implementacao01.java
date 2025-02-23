import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Implementacao01 {

    private static String[] lerCabecalho(BufferedReader br) {
        String linhaLida;
        String[] resp = null;
        try {
            linhaLida = br.readLine();
            resp = linhaLida.split("\\s+");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    private static String inputArquivo(Scanner sc) {
        System.out.print("Nome do arquivo: ");
        String resp = sc.nextLine().trim();
        return resp;
    }

    private static BufferedReader inicializaBuffer(Scanner sc) {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get(inputArquivo(sc)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }

    private static int[] montarArestasGrafo(int E, BufferedReader br) {
        String[] splitter;
        int controle = 0;
        String linhaLida;
        int resp[] = new int[E * 2];
        try {
            while ((linhaLida = br.readLine()) != null) {
                splitter = linhaLida.split("\\s+");
                resp[controle] = Integer.parseInt(splitter[1].trim());
                resp[controle + 1] = Integer.parseInt(splitter[2].trim());
                controle += 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    private static int[] mergeSortVerticeOrigem(int[] array) {
        int n = array.length;

        if (n == 2) { // duas posições = 1 aresta! Meu vetor é um um array de ""tuplas""
            return array;
        }

        int m = n / 2;

        if (m % 2 == 1) { // se m for ímpar, precisa-se somar +1 para garantir que as "tuplas" ficarão
                          // junto dps da divisão.
            m++;
        }

        int[] L = new int[m];
        int[] R = new int[n - m];

        for (int i = 0; i < m; i++) {
            L[i] = array[i];
        }

        int j = 0;
        for (int i = m; i < n; i++) {
            R[j] = array[i];
            j++;
        }

        L = mergeSortVerticeOrigem(L);
        R = mergeSortVerticeOrigem(R);

        int r, l, controle;
        r = l = controle = 0;
        int[] arrayOrdenado = new int[n];

        while (l < L.length || r < R.length) {
            if (l < L.length && r < R.length) {
                if (L[l] <= R[r]) {
                    arrayOrdenado[controle] = L[l];
                    arrayOrdenado[controle + 1] = L[l + 1];
                    l += 2;
                } else {
                    arrayOrdenado[controle] = R[r];
                    arrayOrdenado[controle + 1] = R[r + 1];
                    r += 2;
                }
            } else if (l < L.length) {
                arrayOrdenado[controle] = L[l];
                arrayOrdenado[controle + 1] = L[l + 1];
                l += 2;
            } else {
                arrayOrdenado[controle] = R[r];
                arrayOrdenado[controle + 1] = R[r + 1];
                r += 2;
            }
            controle += 2;
        }
        return arrayOrdenado;
    }

    private static int[] mergeSortVerticeDestino(int[] array) {
        int n = array.length;
        if (n == 2) {
            return array;
        }

        int m = n / 2;

        if (m % 2 == 1) {
            m++;
        }

        int[] L = new int[m];
        int[] R = new int[n - m];

        for (int i = 0; i < m; i++) {
            L[i] = array[i];
        }

        int j = 0;
        for (int i = m; i < n; i++) {
            R[j] = array[i];
            j++;
        }

        L = mergeSortVerticeDestino(L);
        R = mergeSortVerticeDestino(R);

        int r, l, controle;
        r = l = 1;
        controle = 1;

        int[] arrayOrdenado = new int[n];

        while (l < L.length || r < R.length) {
            if (l < L.length && r < R.length) {
                if (L[l] <= R[r]) {
                    arrayOrdenado[controle] = L[l];
                    arrayOrdenado[controle - 1] = L[l - 1];
                    l += 2;
                } else {
                    arrayOrdenado[controle] = R[r];
                    arrayOrdenado[controle - 1] = R[r - 1];
                    r += 2;
                }
            } else if (l < L.length) {
                arrayOrdenado[controle] = L[l];
                arrayOrdenado[controle - 1] = L[l - 1];
                l += 2;
            } else {
                arrayOrdenado[controle] = R[r];
                arrayOrdenado[controle - 1] = R[r - 1];
                r += 2;
            }
            controle += 2;
        }
        return arrayOrdenado;

    }

    private static int hashIndicadorPosicaoInicial(int n) {
        return n + 1;
    }

    private static int reverseHashIndicadorPosicaoInicial(int n) {
        return n - 1;
    }

    private static int hashVertice(int n) {
        return n - 1;
    }

    private static int[] estruturarVerticeSucessor(int[] aresta, int numVertice, int[] grauSaida) {
        int[] resp = new int[numVertice];
        grauSaida[hashVertice(aresta[0])]++;
        resp[hashVertice(aresta[0])] = hashIndicadorPosicaoInicial(1); // esta hash de posição inicial foi feito para
                                                                       // evitar a inicialização de todas posições com
                                                                       // -1 ( sendo -1 = não há sucessores, neste
                                                                       // caso);
        for (int i = 2; i < aresta.length; i += 2) {
            grauSaida[hashVertice(aresta[i])]++;
            if (aresta[i] != aresta[i - 2]) {
                resp[hashVertice(aresta[i])] = hashIndicadorPosicaoInicial(i + 1);
            }
        }
        return resp;
    }

    private static int[] estruturarVerticePredecessor(int[] aresta, int numVertice, int[] grauEntrada) {
        int[] resp = new int[numVertice];
        grauEntrada[hashVertice(1)]++;
        resp[hashVertice(1)] = hashIndicadorPosicaoInicial(0);
        for (int i = 3; i < aresta.length; i += 2) {
            grauEntrada[hashVertice(aresta[i])]++;
            if (aresta[i] != aresta[i - 2]) {
                resp[hashVertice(aresta[i])] = hashIndicadorPosicaoInicial(i - 1);
            }
        }
        return resp;
    }

    private static int inputVertice(Scanner sc) {
        System.out.printf("Vértice: ");
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    private static void printGrauSaida(int v, int[] grauSaida) {
        v = hashVertice(v);
        System.out.println("Grau Saída: " + grauSaida[v]);
    }

    private static void printGrauEntrada(int v, int[] grauEntrada) {
        v = hashVertice(v);
        System.out.println("Grau Entrada: " + grauEntrada[v]);
    }

    private static void printConjuntoSucessores(int[] verticeSucessor, int[] aresta, int vertice, int[] grau) {
        vertice = hashVertice(vertice);
        int primeitaPosicaoSucessor = reverseHashIndicadorPosicaoInicial(verticeSucessor[vertice]);
        int k = 0;
        System.out.print("Conjunto de Sucessores: { ");
        while (k < grau[vertice]) {
            System.out.print(aresta[primeitaPosicaoSucessor]);

            if (k != grau[vertice] - 1) {
                System.out.print(", ");
            }
            primeitaPosicaoSucessor += 2;
            k++;
        }
        System.out.print(" }" + "\n");
    }

    private static void printConjuntoPredecessores(int[] verticePredecessor, int[] aresta, int vertice, int[] grau) {
        vertice = hashVertice(vertice);
        int primeiraPosicaoPredecessor = reverseHashIndicadorPosicaoInicial(verticePredecessor[vertice]);
        int k = 0;
        System.out.print("Conjunto de Predecessores: { ");
        while (k < grau[vertice]) {
            System.out.print(aresta[primeiraPosicaoPredecessor]);

            if (k != grau[vertice] - 1) {
                System.out.print(", ");
            }
            primeiraPosicaoPredecessor += 2;
            k++;
        }
        System.out.print(" }" + "\n");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = inicializaBuffer(sc);
        String[] cabecalho = lerCabecalho(br);
        int numVertice = Integer.parseInt(cabecalho[0]);
        int numAresta = Integer.parseInt(cabecalho[1]);

        int[] arestaOrdenadaOrigem = montarArestasGrafo(numAresta, br);
        arestaOrdenadaOrigem = mergeSortVerticeOrigem(arestaOrdenadaOrigem);
        int[] arestaOrdenadaDestino = mergeSortVerticeDestino(arestaOrdenadaOrigem);

        int[] grauSaida = new int[numVertice];
        int[] verticeSucessor = estruturarVerticeSucessor(arestaOrdenadaOrigem, numVertice, grauSaida);

        int[] grauEntrada = new int[numVertice];
        int[] verticePredecessor = estruturarVerticePredecessor(arestaOrdenadaDestino, numVertice, grauEntrada);

        int vertice = inputVertice(sc);
        printGrauSaida(vertice, grauSaida);
        printGrauEntrada(vertice, grauEntrada);
        printConjuntoSucessores(verticeSucessor, arestaOrdenadaOrigem, vertice, grauSaida);
        printConjuntoPredecessores(verticePredecessor, arestaOrdenadaDestino, vertice, grauEntrada);
    }
}