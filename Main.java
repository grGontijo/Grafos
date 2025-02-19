import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Main {

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
                if (L[l] < R[r]) {
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

    private static int hashVerticeArraySucessores(int n) {
        return n + 1;
    }

    private static int reverseHashArraySucessores(int n) {
        return n - 1;
    }

    private static int hashVertice(int n) {
        return n - 1;
    }

    private static int[] estruturarVerticeSucessor(int[] aresta, int numVertice, int[] grauSaida) {
        // lembrar de fazer a hash vertice dps
        int[] resp = new int[numVertice];
        grauSaida[hashVertice(aresta[0])]++;
        for (int i = 2; i < aresta.length; i += 2) {
            grauSaida[hashVertice(aresta[i])]++;
            if (aresta[i] != aresta[i - 2]) {
                resp[hashVertice(aresta[i - 2])] = hashVerticeArraySucessores(i - 2);
            }
        }
        return resp;
    }

    private static int inputVertice(Scanner sc) {
        System.out.printf("Vértice: ");
        int v = hashVertice(sc.nextInt());
        sc.nextLine();
        return v;
    }

    private static void printGrauSaida(int v, int[] grauSaida) {
        System.out.println("Grau Saída: " + grauSaida[v]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = inicializaBuffer(sc);
        String[] cabecalho = lerCabecalho(br);
        int numVertice = Integer.parseInt(cabecalho[0]);
        int numAresta = Integer.parseInt(cabecalho[1]);
        int[] aresta = montarArestasGrafo(numAresta, br);
        aresta = mergeSortVerticeOrigem(aresta);
        int[] grauSaida = new int[numVertice];
        int[] verticeSucessor = estruturarVerticeSucessor(aresta, numVertice, grauSaida);
        int vertice = inputVertice(sc);
        printGrauSaida(vertice, grauSaida);
    }
}