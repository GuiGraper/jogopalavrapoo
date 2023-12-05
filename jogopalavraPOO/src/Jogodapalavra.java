import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Execução do java
public class Jogodapalavra {
    public static void main(String[] args) { //método chamado main
        Jogo jogo = new Jogo(); //instância da classe, cria objeto
        jogo.iniciarJogo();
    }
}
// Em seguida as cores, colocado em strings para nao confundir os códigos, (nao consegui fechar a string entao usei código)
class Jogo {
    private Set<String> palavrasValidas;
    private Set<String> palavrasDigitadas;
    private char letraSorteada;
    private String corVerde = "\u001B[32m";  //Apenas cor para salvar o cód. ignore
    private String corVermelha = "\u001B[31m";  //Apenas cor para salvar o cód. ignore
    private String corAmarela = "\u001B[33m";  //Apenas cor para salvar o cód. ignore
    private String corPadrao = "\u001B[0m";  //Apenas cor para salvar o cód. ignore

    public Jogo() {
        palavrasValidas = carregarPalavras();
        palavrasDigitadas = new HashSet<>();
    }

    public void iniciarJogo() {
        // instruções do jogo, texto em azul e letra sorteada em verde
        letraSorteada = sortearLetra();
        System.out.println(corPadrao + "\u001B[34mEste jogo irá sortear uma letra e você precisa informar a maior quantidade de palavras que conhecer com a letra sorteada.\nVOCÊ TEM APENAS 20 SEGUNDOS...\u001B[0m");
        System.out.println("Letra sorteada: " + corVerde + Character.toUpperCase(letraSorteada) + corPadrao);

        // Config do temporizador para acabar a partida com delay de 20s
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameOver();
            }
        }, 20000); //  20 segundos de jogo

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Entrada da palavra pelo usuário
            System.out.print("Digite uma palavra válida com a letra " + corVerde + Character.toUpperCase(letraSorteada) + corPadrao + " que possua pelo menos 4 letras: ");
            String palavra = scanner.nextLine().toLowerCase();

            // Valida a palavra digirtada
            if (validarPalavra(palavra) && palavra.charAt(0) == letraSorteada && !palavrasDigitadas.contains(palavra)) {
                palavrasDigitadas.add(palavra);
            } else {
                // feedback de erro, caso a palavra nao esteja na lista ou seja repetida ou seja com outra letra
                System.out.println(corVermelha + "!!!   Esta palavra não existe, está repetida ou não começa com a letra sorteada   !!!" + corPadrao);
            }
        }
    }

    // Aqui carrega a lista em TXT das palavras
    private Set<String> carregarPalavras() {
        Set<String> palavras = new HashSet<>();
        try {
            Scanner scanner = new Scanner(new File("palavrasdojogo.txt"));
            while (scanner.hasNext()) {
                palavras.add(scanner.next().toLowerCase());
     //           System.err.println("to aqui");

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Sem arquivo.");
        }
        return palavras;
    }

    // Em seguida sortea uma letra aleatoria
    private char sortearLetra() {
        Random random = new Random(); //pega randomicamente no alfabeto abauxo
        char[] letras = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return letras[random.nextInt(letras.length)];
    }

    // Aqui verifica o tamnaho e caracteres  da palavraa
    private boolean validarPalavra(String palavra) {
        return palavra.length() >= 4 && palavra.matches("[a-z]+") && palavrasValidas.contains(palavra);
    }


    // Em seguida encerramento com informações do Feedback com a letra escolhida
    private void gameOver() {
        System.out.println("\n\n\u001B[31mGame Over!\nEstes são seus resultados:\u001B[0m");
        System.out.println("\u001B[34mSua pontuação foi: \u001B[0m" + "\u001B[32m" +palavrasDigitadas.size() +"  palavras corretas!");
        System.out.println("\u001B[34mPalavras validadas: \u001B[0m" + "\u001B[33m"+ palavrasDigitadas);
        System.exit(0); //fecha
    }
}