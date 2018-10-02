public class Cliente {
    private String nome, senha, email, cpf, telefone;

    public Cliente(String nome, String senha, String email, String cpf, String telefone) throws ExceptionCliente {
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setCpf(cpf);
        setTelefone(telefone);
    }

    public Cliente(String nome, String senha, String email) throws ExceptionCliente {
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setCpf(null);
        setTelefone(null);
    }

    public String getNome() {
        return nome;
    }

    //Coloca um nome valido
    public void setNome(String nome) throws ExceptionCliente {
        if (nome != null && nome != "")
            this.nome = nome;
        else
            throw new ExceptionCliente("Nome invalido");
    }

    public String getEmail() {
        return email;
    }

    //Coloca um email valido
    public void setEmail(String email) throws ExceptionCliente {
        if (email != null && email != "")
            this.email = email;
        else
            throw new ExceptionCliente("Email invalido");
    }

    public String getCpf() {
        return cpf;
    }

    //Coloca um cpf valido
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    //Coloca um telefone valido
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //Coloca uma senha não nula ou vazia e maior de 8 caracteres
    private void setSenha(String senha) throws ExceptionCliente {
        if (senha != null && senha != "" && senha.length() >= 8)
            this.senha = senha;
        else
            throw new ExceptionCliente("Senha invalida");
    }

    //Altera a senha quando a senha atual passada for igual a senha do cliente
    public void alteraSenha(String senhaNova, String senhaAtual) throws ExceptionCliente {
        if (senhaAtual == this.senha)
            setSenha(senhaNova);
        else
            throw new ExceptionCliente("Senha atual invalida");
    }
}
