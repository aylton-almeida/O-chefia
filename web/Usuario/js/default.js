//Mensagens de alerta
function msgError(msg){
  $('#msg').html('<div class="alert alert-danger fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}
function msgSuccess(msg){
  $('#msg').html('<div class="alert alert-success fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}

//Cadastrar Usuario
$('#btnSubmitUsuario').click(() => {
  if($("#cadastrarUsuarioForm")[0].checkValidity()) {
    //AJAX
  //   let xmlhttp = FactoryXMLHttpRequest();
  //   let formData = new FormData();
  //   formData.append
  //
  //   xmlhttp.onreadystatechange = function() {
  //     if (xmlhttp.readyState == 4) {
  //       let jsonObj = JSON.parse(xmlhttp.responseText);
  //       msgSuccess(jsonObj.status);
  //       $('#modalCadastroPratos').modal('hide');
  //       $('#nomeInput').val("");
  //       $('#precoInput').val("");
  //       $('#ingredientesInput').val("");
  //     }
  //   }
  //
  //   if (xmlhttp) {
  //     xmlhttp.open('get', 'http://127.0.0.1:7200/cadastrarUsuario', true);
  //     xmlhttp.send(formData);
  //   }
  // }else{
  //   msgErrorModal("Preencha os campos corretamente");
  // }

  //AJAX
$.ajax({
    url: 'http://127.0.0.1:7200/cadastrarUsuario',
    type: "POST",
    data: ({
        nome: $('#nomeInput').val(),
        assunto: $('#senhaInput').val(),
    }),
    success: function () {
        alert("Email enviado!")
    },
    error: function (event) {
        console.log(event);
    }
})