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
}else{
  $('#formLogin').show();
  $('#bemVindo').hide();
}

if (msg != null && msg.status) {
  if (msg.type == "success") {
    msgSuccess(msg.message);
  }else{
    msgError(msg.message)
  }
  sessionStorage.setItem('mensagem', null);
}
