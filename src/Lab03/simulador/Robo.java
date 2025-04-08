package simulador;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private Ambiente ambiente;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        setNome(nome);
        setDirecao("Norte");
        setX(posicaoX);
        setY(posicaoY);
        setAmbiente(ambiente);
        ambiente.adicionarRobo(this);       // Adiciona o robo no ambiente logo que é criado

        System.out.printf("Robo '%s' criado\n", nome);
    }

    public void info() {
        System.out.printf("Robo '%s' esta na posicao (%d, %d) apontado na direcao %s.\n\n", getNome(), getX(), getY(), direcao);
    }

    /**
     * Move o robo no nas direcoes horizontal e vertical conforme definido
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    public void mover(int deltaX, int deltaY) {
        int novoX = posicaoX + deltaX;
        int novoY = posicaoY + deltaY;

        System.out.printf("Tentando mover o robo '%s' em %d no eixo x e em %d no y.\n", nome, deltaX, deltaY);

        // Checa se o robo nao esta saindo dos limites do ambiente
        if (getAmbiente().dentroDosLimites(novoX, novoY)) {
            // Checa se nao ha obstaculos nos 2 caminhos até o ponto final
            if (checarObstaculoCaminho(deltaX, deltaY)) {
                posicaoX = novoX;
                posicaoY = novoY;
                System.out.printf("Movimentado com sucesso.\n");
                this.exibirPosicao();
            } 
            else 
                System.out.printf("Ha obstaculos impedindo o movimento de '%s'.\n\n", nome);
        } 
        // Nao atualiza posicao caso tenha saindo dos limites
        else 
            System.out.printf("'%s' nao tem permissai para sair do ambiente.\n\n", nome);
    }

    /**
     * Checa se ha algum obstaculo impedindo a movimentacao definida, sendo essa movimentacao explicada no README 
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     * @return true ou false dependendo se ha ou nao obstaculos
     */
    public Boolean checarObstaculoCaminho(int deltaX, int deltaY) {
        boolean caminhoCima = true, caminhoBaixo = true;

        // Checa se a linha reta da componente horizontal do movimento, partindo da posicao atual do robo 
        // ou partindo da posicao do robo após andar toda sua componente vertical, contem algum obstaculo;
        // O loop para se ambos os caminhos tiverem um obstaculo;
        if (deltaX > 0) {
            for (int a = 0; (caminhoBaixo || caminhoCima) && a < deltaX; a++) {
                if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY]) 
                    caminhoCima = false;

                if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY + deltaY]) 
                    caminhoBaixo = false;
            }
        }
        else {
            for (int b = 0; (caminhoBaixo || caminhoCima) && b > deltaX; b--) {
                if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY]) 
                    caminhoCima = false;

                if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY + deltaY]) 
                    caminhoBaixo = false;
            }
        }

        // Checa se a linha reta da componente vertical do movimento, partindo da posicao atual do robo 
        // ou partindo da posicao do robo após andar toda sua componente horizontal, contem algum obstaculo;
        if (deltaY > 0) {
            for (int c = 0; (caminhoBaixo || caminhoCima) && c < deltaY; c++) {
                if (ambiente.obstaculosMatriz[posicaoX][posicaoY + c])
                    caminhoBaixo = false;
                
                if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + c])
                    caminhoCima = false;
            }
        }
        else {
            for (int d = 0; (caminhoBaixo || caminhoCima) && d > deltaY; d--) {
                if (ambiente.obstaculosMatriz[posicaoX][posicaoY + d])
                    caminhoBaixo = false;
                
                if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + d])
                    caminhoCima = false;
            }
        }

        return caminhoBaixo || caminhoCima;
    }

    public boolean identificarObstaculo() {
        boolean temObstaculo = false;

        // Checa se ha algum obstaculo nas 4 adjacentes ao robo
        if(!(getAmbiente().dentroDosLimites(posicaoX + 1, posicaoY) && getAmbiente().dentroDosLimites(posicaoX - 1, posicaoY)
            || getAmbiente().dentroDosLimites(posicaoX, posicaoY + 1) || getAmbiente().dentroDosLimites(posicaoX, posicaoY - 1)))
            temObstaculo = true;
        else if (ambiente.obstaculosMatriz[posicaoX + 1][posicaoY] || ambiente.obstaculosMatriz[posicaoX - 1][posicaoY]
        || ambiente.obstaculosMatriz[posicaoX][posicaoY + 1] || ambiente.obstaculosMatriz[posicaoX][posicaoY - 1])
            temObstaculo = true;
        
        return temObstaculo;
    }

    public boolean podeMover(int deltaX, int deltaY) {
        return getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY) && checarObstaculoCaminho(deltaX, deltaY);
    }

    public void exibirPosicao() {
        System.out.printf("O robo '%s' esta em (%d, %d) na direcao %s.\n\n", nome, posicaoX, posicaoY, direcao);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDirecao(String drc) {
        if (drc == "Norte" || drc == "Sul" || drc == "Leste" || drc == "Oeste")
            direcao = drc;
        else
            direcao = "Norte";
    }

    protected void setX(int x) {
        posicaoX = x;
    }

    protected void setY(int y) {
        posicaoY = y;
    }

    protected void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public String getNome() {
        return nome;
    }
    
    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }

    public String getDirecao() {
        return direcao;
    }

    public Ambiente getAmbiente(){
        return ambiente;
    }
}