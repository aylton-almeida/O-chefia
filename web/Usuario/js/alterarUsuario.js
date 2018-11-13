//Alterar Usuario
$('#btnSubmitUsuario').click(() => {
  if ($("#alterarUsuarioForm")[0].checkValidity()) {
    //AJAX
    $.ajax({
      url: 'http://127.0.0.1:7200/alterarUsuario',
      type: "POST",
      data: ({
        nome: $('#nomeInput').val(),
        email: user.email,
        senhaAtual: $('#senhaInput').val(),
        senhaNova: $('#novaSenhaInput').val(),
        cpf: $('#cpfInput').val(),
        telefone: $('#telefoneInput').val()
      }),
      success: function(response) {
        if (response.status == 1) {
          let usuario = {
            status: true,
            nome: $('#nomeInput').val(),
            email: user.email,
            senha: $('#novaSenhaInput').val(),
            cpf: $('#cpfInput').val(),
            telefone: $('#telefoneInput').val()
          };
          let mensagem = {
            status: true,
            type: "success",
            message: "Alteração realizada com sucesso"
          };
          sessionStorage.setItem('usuario', JSON.stringify(usuario));
          sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
          window.location.href = "../index.html";
        } else {
          msgError(response.message);
          console.log(response);
        }
      },
      error: function(event) {
        msgError("Erro ao alterar usuário")
        console.log(event);
      }
    })
  } else {
    msgError("Preencha os campos corretamente");
  }
})

$('#btnSairUsuario').click(() =>{
  sessionStorage.setItem('usuario', null);
  $(location).attr('href', '../index.html');
})

//Deletar usuario
$('#btnDeletar').click(()=>{
  //AJAX
  $.ajax({
    url: 'http://127.0.0.1:7200/deletarUsuario',
    type: "POST",
    data: ({
      email: user.email
    }),
    success: function(response) {
      if (response.status == 1) {
        let mensagem = {
          status: true,
          type: "success",
          message: "Usuário deletado com sucesso"
        };
        sessionStorage.setItem('usuario', null);
        sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
        window.location.href = "../index.html";
      } else {
        msgError(response.message);
        console.log(response);
      }
    },
    error: function(event) {
      msgError("Erro ao deletar usuário")
      console.log(event);
    }
  })
})
