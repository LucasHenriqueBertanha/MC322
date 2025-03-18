public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;

    public Robo(String nome, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    public void mover(int deltaX, int deltaY) {
        posicaoX += deltaX;
        posicaoY += deltaY;
    }

    public void exibirPosicao() {
        System.out.printf("\nO robô %s está em (%d, %d).\n", nome, posicaoX, posicaoY);
    }

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }
}