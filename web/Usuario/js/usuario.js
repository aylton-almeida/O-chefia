//Cadastrar Usuario
$('#btnSubmitUsuario').click(() => {
  if ($("#cadastrarPratoForm")[0].checkValidity()) {
    if ($('#senhaInput').val() == $('#confSenhaInput').val()) {
      //AJAX
      $.ajax({
        url: 'http://127.0.0.1:7200/cadastrarUsuario',
        type: "POST",
        data: ({
          nome: $('#nomeInput').val(),
          email: $('#emailInput').val(),
          senha: $('#senhaInput').val(),
          cpf: $('#cpfInput').val(),
          telefone: $('#telefoneInput').val()
        }),
        success: function(response) {
          if (response.status == 1) {
            let usuario = {
              status: true,
              nome: $('#nomeInput').val(),
              email: $('#emailInput').val(),
              senha: $('#senhaInput').val(),
              cpf: $('#cpfInput').val(),
              telefone: $('#telefoneInput').val()
            };
            let mensagem = {
              status: true,
              type: "success",
              message: "Cadastro realizado com sucesso"
            };
            sessionStorage.setItem('usuario', JSON.stringify(usuario));
            sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
            window.location.href = "../index.html";
          }else {
            msgError(response.message);
          }
        },
        error: function(event) {
          msgError("Erro ao cadastrar usuário")
          console.log(event);
        }
      })
    } else {
      msgError("As senhas não coincidem");
    }
  } else {
    msgError("Preencha os campos corretamente");
  }
})
