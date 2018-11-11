//Mensagens de alerta pagina
function msgError(msg) {
  $('#msg').html('<div class="alert alert-danger fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}

function msgSuccess(msg) {
  $('#msg').html('<div class="alert alert-success fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}
//Mensagens de alerta modal
function msgErrorModal(msg) {
  $('#msgModal').html('<div class="alert alert-danger fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}

function msgSuccessModal(msg) {
  $('#msgModal').html('<div class="alert alert-success fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}

var user = JSON.parse(sessionStorage.getItem('usuario'));
var msg = JSON.parse(sessionStorage.getItem('mensagem'));

if (user != null && user.status) {
  $('#formLogin').hide();
  $('#bemVindo').show();
  $('#bemVindo').html("Bem vindo, " + user.nome);
} else {
  $('#formLogin').show();
  $('#bemVindo').hide();
}

if (msg != null && msg.status) {
  if (msg.type == "success") {
    msgSuccess(msg.message);
  } else {
    msgError(msg.message)
  }
  sessionStorage.setItem('mensagem', null);
}

//Login Usuario
$('#btnLogin').click(() => {
  if ($("#formLogin")[0].checkValidity()) {
    //AJAX
    $.ajax({
      url: 'http://127.0.0.1:7200/loginUsuario',
      type: "POST",
      data: ({
        email: $('#emailInput').val(),
        senha: $('#senhaInput').val()
      }),
      success: function(response) {
        if (response.status == 1) {
          let usuario = {
            status: true,
            nome: response.usuario.nome,
            email: response.usuario.email,
            senha: response.usuario.senha,
            cpf: response.usuario.cpf,
            telefone: response.usuario.telefone
          };
          let mensagem = {
            status: true,
            type: "success",
            message: "Login efetuado com sucesso"
          };
          sessionStorage.setItem('usuario', JSON.stringify(usuario));
          sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
          window.location.reload();
        } else {
          let mensagem = {
            status: true,
            type: "error",
            message: response.message
          };
          sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
        }
      },
      error: function(event) {
        let mensagem = {
          status: true,
          type: "error",
          message: "Erro ao fazer login"
        };
        sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
        console.log(event);
      }
    })
  } else {
    let mensagem = {
      status: true,
      type: "error",
      message: "Peencha os campos corretamente"
    };
    sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
  }
})
