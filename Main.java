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

    private static BufferedReader inicializaBuffer(BufferedReader br, Scanner sc) {
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

    public static int[] mergeSortVerticeOrigem(int[] array) {
        int n = array.length;

        if (n == 2) { // lembrando que n=2 é mesmo de 1 aresta!
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        br = inicializaBuffer(br, sc);

        String[] cabecalho = lerCabecalho(br);

        for (String vertice : cabecalho) {
            System.out.println(vertice);
        }
        int numVertice = Integer.parseInt(cabecalho[0]);
        int numAresta = Integer.parseInt(cabecalho[1]);
        int[] aresta = montarArestasGrafo(numAresta, br);
        for (int i = 0; i < aresta.length; i += 2) {
            System.out.println("(" + aresta[i] + " , " + aresta[i + 1] + ")");
        }
        aresta = mergeSortVerticeOrigem(aresta);
        System.out.println("---------------------------------");
        for (int i = 0; i < aresta.length; i += 2) {
            System.out.println("(" + aresta[i] + " , " + aresta[i + 1] + ")");
        }
    }
}