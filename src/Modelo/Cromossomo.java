package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cromossomo {

    // Tamanho do cromosoomo = 4, Ponto maximo 134 = 10000110, DECIMAL = 7
    public static final int TAMANHO = 4;
    int geracao;
    int id;
    String binario;
    Double x;
    Double y;
    Double prob;
    boolean mutacao = false;
    boolean crossover = false;
    int geracaoPai1 = -1, geracaoPai2 = -1, cromossomoPai1 = -1, cromossomoPai2 = -1, pontoDeCorte = 0, pontoDeMutacao = 0;

    public int getPontoDeCorte() {
        return pontoDeCorte;
    }

    public void setPontoDeCorte(int pontoDeCorte) {
        this.pontoDeCorte = pontoDeCorte;
    }

    public int getPontoDeMutacao() {
        return pontoDeMutacao;
    }

    public void setPontoDeMutacao(int pontoDeMutacao) {
        this.pontoDeMutacao = pontoDeMutacao;
    }

    public int getGeracaoPai1() {
        return geracaoPai1;
    }

    public void setGeracaoPai1(int geracaoPai1) {
        this.geracaoPai1 = geracaoPai1;
    }

    public int getGeracaoPai2() {
        return geracaoPai2;
    }

    public void setGeracaoPai2(int geracaoPai2) {
        this.geracaoPai2 = geracaoPai2;
    }

    public int getCromossomoPai1() {
        return cromossomoPai1;
    }

    public void setCromossomoPai1(int cromossomoPai1) {
        this.cromossomoPai1 = cromossomoPai1;
    }

    public int getCromossomoPai2() {
        return cromossomoPai2;
    }

    public void setCromossomoPai2(int cromossomoPai2) {
        this.cromossomoPai2 = cromossomoPai2;
    }

    public boolean isMutacao() {
        return mutacao;
    }

    public void setMutacao(boolean mutacao) {
        this.mutacao = mutacao;
    }

    public boolean isCrossover() {
        return crossover;
    }

    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBinario() {
        return binario;
    }

    public void setBinario(String binario) {
        // Primeiro numero do dinario é o sinal, 1 positivo, 0 negativo
        String sinal = binario.substring(0, 1);
        Integer inteiro;
        Integer decimal = Integer.parseInt(binario.substring(binario.indexOf(".") + 1));

        if (sinal.equals("-")) {
            inteiro = Integer.parseInt(binario.substring(1, binario.indexOf(".")));
            binario = 0 + preencherBinario(Integer.toBinaryString(inteiro)) + "." + preencherBinario(Integer.toBinaryString(decimal));
        } else {
            inteiro = Integer.parseInt(binario.substring(0, binario.indexOf(".")));
            binario = 1 + preencherBinario(Integer.toBinaryString(inteiro)) + "." + preencherBinario(Integer.toBinaryString(decimal));
        }
        this.binario = binario;
    }

    public void setBinarioFilho(String binario) {
        this.binario = binario;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = duasCasas(x);
    }

    public Double getY() {
        return y;
    }

    public void setY(Double x) {
        this.y = duasCasas(f(x));
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double y, Double x) {
        if ((x <= 10) && (x >= -10)) {
            this.prob = duasCasas((y * 100) / 134);
        } else {
            this.prob = 0.1;
        }

    }

    public Double f(Double x) {
        Double y = x * x - 3 * x + 4;
        return y;
    }

    public Double duasCasas(Double n) {
        String num = String.valueOf(n);
        int casas = 2;
        num = num.substring(0, String.valueOf(n).indexOf(".") + casas);
        return Double.parseDouble(num);
    }

    public Double numeroAleatorio(int min, int max) {
        Double randomNum = min + (Double) (Math.random() * (max - min));
        return randomNum;
    }

    public ArrayList<Cromossomo> populacaoInicial(ArrayList<Cromossomo> cromossomos, Integer populacao) {
        for (int j = 0; j < populacao; j++) {
            Cromossomo cromossomo = new Cromossomo();
            cromossomo.setGeracao(1);
            cromossomo.setId(j + 1);
            cromossomo.setX(numeroAleatorio(-10, 10));
            cromossomo.setY(cromossomo.getX());
            cromossomo.setBinario(String.valueOf(cromossomo.getX()));
            cromossomo.setProb(cromossomo.getY(), cromossomo.getX());
            cromossomos.add(cromossomo);
        }
        return cromossomos;
    }

    public int maisApto(ArrayList<Cromossomo> cromossomos, int k, int populacao) {
        Double maior = 0.0;
        Double valor;
        int iMaior = 0;
        for (int i = k; i < k + populacao; i++) {
            valor = cromossomos.get(i).getProb();
            if (valor > maior) {
                maior = valor;
                iMaior = i;
            }
            // ////System.out.println("MAIOR: i="+i+" - iMaior: "+iMaior+" - Maior: "+maior+" - Valor: "+valor);
        }
        //////System.out.println("FIM: iMaior: "+iMaior);
        return iMaior;
    }

    public List escolherPais(ArrayList<Cromossomo> cromossomos, int k, int numeroDeElementos) {

        List lista = new ArrayList();
        for (int i = k; i < k + numeroDeElementos; i++) {
            for (int j = 0; j < cromossomos.get(i).getProb().intValue(); j++) {
                lista.add(i);
               // System.out.println("LISTA: "+lista.get(i).toString());
            }
        }

        Collections.shuffle(lista);
        List indexDosPais = new ArrayList();
        for (int i = 0; i < numeroDeElementos; i++) {
            indexDosPais.add(lista.get(i));
            ////System.out.println("Index dos Pais: " + indexDosPais.get(i));
        }

        return indexDosPais;
    }

    public ArrayList<Cromossomo> reproducao(ArrayList<Cromossomo> cromossomos, int populacao, int geracao, int taxaDeCrossover, int taxaDeMutacao, boolean elitismo) {

        int k = 0;
        for (int j = 0; j < geracao - 1; j++) {

            List novaGeracao = escolherPais(cromossomos, k, populacao); //SELECIONA OS PAIS
            ArrayList<Cromossomo> pais = new ArrayList<>();

            //ADICIONA O PAI NO ARRAY PAIS
            for (int i = 0; i < novaGeracao.size(); i++) {

                Cromossomo pai = new Cromossomo();
                pai.setGeracao(cromossomos.get((Integer) novaGeracao.get(i)).getGeracao());
                pai.setId(cromossomos.get((Integer) novaGeracao.get(i)).getId());
                pai.setX(cromossomos.get((Integer) novaGeracao.get(i)).getX());
                pai.setBinario(String.valueOf(pai.getX()));
                pai.setY(pai.getX());
                pai.setProb(pai.getY(), pai.getX());
                pais.add(pai);
            }

            //FAZ CROSSOVER
            ArrayList<Cromossomo> filhosCrossover = Crossover(pais, taxaDeCrossover, cromossomos, elitismo);

            //FAZ MUTACAO
            ArrayList<Cromossomo> filhosMutacao = Mutacao(filhosCrossover, taxaDeMutacao, elitismo);

            for (Cromossomo filho : filhosMutacao) {
                cromossomos.add(filho);
            }
            k = k + populacao;
        }
        return cromossomos;
    }

    public boolean taxaDeCrossoverMutacao(int taxaDeCrossoverMutacao) {
        List lista = new ArrayList();
        for (int i = 0; i < taxaDeCrossoverMutacao; i++) {
            lista.add(true);
        }
        for (int i = 0; i < 100 - taxaDeCrossoverMutacao; i++) {
            lista.add(false);
        }
        Collections.shuffle(lista);

        if (!lista.get(0).equals(true)) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Cromossomo> Crossover(ArrayList<Cromossomo> pais, int taxaDeCrossover, ArrayList<Cromossomo> cromossomos, boolean elitismo) {

        ArrayList<Cromossomo> filhos = new ArrayList<>();

        int geracao = pais.get(0).getGeracao();

        for (int i = 0; i < pais.size() - 1; i = i + 2) {
            Cromossomo gene1, gene2, pai1, pai2;

            String pontoDeCorte = numeroAleatorio(1, TAMANHO).toString(); //ESCOLHA DO PONTO DE CORTE
            pontoDeCorte = pontoDeCorte.substring(0, pontoDeCorte.indexOf("."));
            int iPontoDeCorte = Integer.parseInt(pontoDeCorte);

            gene1 = pais.get(i);
            gene2 = pais.get(i + 1);
            pai1 = pais.get(i); //CLONAMOS PAIS PARA ELITISMO
            pai2 = pais.get(i + 1);

            //System.out.println("1 GERACAO PAI PRE CROSS:" + gene1.getGeracao());
            // System.out.println("2 GERACAO PAI PRE CROSS:" + gene2.getGeracao());
            if (taxaDeCrossoverMutacao(taxaDeCrossover) == true) { //VERIFICAR SE VAI TER CROSSOVER

                String sinal1, sinal2, inteiro1, inteiro2, decimal1, decimal2, calda, cabeca; //TRATAR BINARIO
                sinal1 = gene1.getBinario().substring(0, 1);
                sinal2 = gene2.getBinario().substring(0, 1);
                inteiro1 = gene1.getBinario().substring(1, TAMANHO + 1);
                inteiro2 = gene2.getBinario().substring(1, TAMANHO + 1);
                decimal1 = gene1.getBinario().substring(TAMANHO + 2);
                decimal2 = gene2.getBinario().substring(TAMANHO + 2);

                //INFORMACOES SOBRE O CROSSOVER
                gene1.setCrossover(true);
                gene2.setCrossover(true);
                gene1.setPontoDeCorte(iPontoDeCorte);

                //CROSSOVER INTEIRO 1
                cabeca = inteiro1.substring(0, iPontoDeCorte);
                calda = inteiro1.substring(iPontoDeCorte);
                inteiro1 = cabeca + inteiro2.substring(iPontoDeCorte);

                //CROSSOVER INTEIRO 2
                cabeca = inteiro2.substring(0, iPontoDeCorte);
                inteiro2 = cabeca + calda;

                //CROSSOVER DECIMAL 1
                cabeca = decimal1.substring(0, iPontoDeCorte);
                calda = decimal1.substring(iPontoDeCorte);
                decimal1 = cabeca + decimal2.substring(iPontoDeCorte);

                //CROSSOVER DECIMAL 2
                cabeca = decimal2.substring(0, iPontoDeCorte);
                decimal2 = cabeca + calda;

                //ENVIAR NOVO BINARIO
                gene1.setBinarioFilho(sinal1 + inteiro1 + "." + decimal1);
                gene2.setBinarioFilho(sinal2 + inteiro2 + "." + decimal2);

                //ENVIAR NOVO X
                gene1.setX(binarioParaX(sinal1, inteiro1, decimal1));
                gene2.setX(binarioParaX(sinal2, inteiro2, decimal2));

                //ENVIAR NOVO Y
                gene1.setY(gene1.getX());
                gene2.setY(gene2.getX());

                //ENVIAR NOVA PROBABILIDADE
                gene1.setProb(gene1.getY(), gene1.getX());
                gene2.setProb(gene2.getY(), gene2.getX());

            }

            if (elitismo == true) {
                if (pai1.getProb() >= pai2.getProb()) {
                    if (pai1.getProb() >= gene1.getProb()) {
                        pai1.setId(i + 1);
                        pai1.setGeracao(geracao + 1);
                        filhos.add(pai1);
                    } else {
                        gene1.setId(i + 1);
                        gene1.setGeracao(geracao + 1);
                        filhos.add(gene1);
                    }
                } else if (pai2.getProb() >= gene1.getProb()) {
                    pai2.setId(i + 1);
                    pai2.setGeracao(geracao + 1);
                    filhos.add(pai2);
                } else {
                    gene1.setId(i + 1);
                    gene1.setGeracao(geracao + 1);
                    filhos.add(gene1);
                }
                if (pai1.getProb() >= pai2.getProb()) {
                    if (pai1.getProb() >= gene2.getProb()) {
                        pai1.setId(i + 1);
                        pai1.setGeracao(geracao + 1);
                        filhos.add(pai1);
                    } else {
                        gene2.setId(i + 1);
                        gene2.setGeracao(geracao + 1);
                        filhos.add(gene1);
                    }
                } else if (pai2.getProb() >= gene2.getProb()) {
                    pai2.setId(i + 1);
                    pai2.setGeracao(geracao + 1);
                    filhos.add(pai2);
                } else {
                    gene2.setId(i + 1);
                    gene2.setGeracao(geracao + 1);
                    filhos.add(gene2);
                }

            } else {
                //INFORMAÇÕES SOBRE OS PAIS
                gene1.setGeracaoPai1(pai1.getGeracao());
                gene1.setGeracaoPai2(pai2.getGeracao());
                gene1.setCromossomoPai1(pai1.getId());
                gene1.setCromossomoPai2(pai2.getId());
                gene2.setGeracaoPai1(pai1.getGeracao());
                gene2.setGeracaoPai2(pai2.getGeracao());
                gene2.setCromossomoPai1(pai1.getId());
                gene2.setCromossomoPai2(pai2.getId());

                //INFORMACOES FILHOS
                gene1.setId(i + 1);
                gene2.setId(i + 2);
                gene1.setGeracao(geracao + 1);
                gene2.setGeracao(geracao + 1);
                filhos.add(gene1);
                filhos.add(gene2);
            }

        }
        return filhos;
    }

    public void printLista(int tamanho, List lista) {
        for (int i = 0; i < tamanho; i++) {
            ////System.out.println("For I: " + i + " - " + lista.get(i));
        }
    }

    public Double binarioParaX(String sinal, String inteiro, String decimal) { //CONVERTE BINARIO PARA DECIMAL
        Double x = 0.0;
        Integer inteiro1, decimal1;

        if (sinal.equals("0")) {
            sinal = "-";
        } else {
            sinal = "+";
        }
        inteiro1 = Integer.parseInt(inteiro, 2);
        decimal1 = Integer.parseInt(decimal, 2);

        x = Double.parseDouble(sinal + inteiro1 + "." + decimal1);
        return x;
    }

    public String preencherBinario(String binario) {
        if (binario.length() < TAMANHO) {
            String aux = "0";
            for (int i = 0; i < (TAMANHO - 1) - binario.length(); i++) {
                aux = aux + "0";
            }
            aux = aux + binario;
            return aux;
        } else {
            return binario;
        }
    }

    public ArrayList<Cromossomo> Mutacao(ArrayList<Cromossomo> filhosCrossover, int taxaDeMutacao, boolean elitismo) {

        ArrayList<Cromossomo> filhos = new ArrayList<>();

        for (int i = 0; i < filhosCrossover.size(); i++) {
            Cromossomo gene1, pai;

            String pontoDeMutacao = numeroAleatorio(1, TAMANHO).toString(); //ESCOLHA DO PONTO DE CORTE
            pontoDeMutacao = pontoDeMutacao.substring(0, pontoDeMutacao.indexOf("."));
            int iPontoDeMutacao = Integer.parseInt(pontoDeMutacao);

            gene1 = filhosCrossover.get(i);
            pai = filhosCrossover.get(i); //CLONA PAI PARA ELITISMO

            if (taxaDeCrossoverMutacao(taxaDeMutacao) == true) { //VERIFICAR SE VAI TER MUTACAO
                
                //INFORMACOES SOBRE MUTACAO
                gene1.setMutacao(true);
                gene1.setPontoDeMutacao(iPontoDeMutacao);
                
                String sinal1; //TRATAR BINARIO
                sinal1 = gene1.getBinario().substring(0, 1);
                String inteiro = gene1.getBinario().substring(1, TAMANHO + 1);
                String decimal = gene1.getBinario().substring(TAMANHO + 2);

                char[] inteiroChar = inteiro.toCharArray();
                char[] decimalChar = inteiro.toCharArray();

                //MUTACAO INTEIRO
                if (inteiroChar[iPontoDeMutacao] == '0') {
                    inteiroChar[iPontoDeMutacao] = '1';
                    inteiro = String.valueOf(inteiroChar);
                } else {
                    inteiroChar[iPontoDeMutacao] = '0';
                    inteiro = String.valueOf(inteiroChar);
                }
                //MUTACAO DECIMAL
                if (decimalChar[iPontoDeMutacao] == '0') {
                    decimalChar[iPontoDeMutacao] = '1';
                    decimal = String.valueOf(decimalChar);
                } else {
                    decimalChar[iPontoDeMutacao] = '0';
                    decimal = String.valueOf(decimalChar);
                }

                //ENVIAR NOVO BINARIO
                gene1.setBinarioFilho(sinal1 + inteiro + "." + decimal);

                //ENVIAR NOVO X
                gene1.setX(binarioParaX(sinal1, inteiro.toString(), decimal.toString()));

                //ENVIAR NOVO Y
                gene1.setY(gene1.getX());

                //ENVIAR NOVA PROBABILIDADE
                gene1.setProb(gene1.getY(), gene1.getX());

            }
            if (elitismo == true) {
                if (pai.getProb() > gene1.getProb()) {
                    filhos.add(pai);
                } else {
                    filhos.add(gene1);
                }
            } else {
                filhos.add(gene1);
            }
        }
        return filhos;
    }

    public int encontrarEnderecoPai(int geracao, int id, int populacao) {
        int enderecoDoPai = (geracao - 1) * populacao + (id - 1);
        return enderecoDoPai;
    }

    public Cromossomo encontrarPai(ArrayList<Cromossomo> cromossomos, int enderecoPai) {

        Cromossomo pai = cromossomos.get(enderecoPai);

        return pai;
    }

}
