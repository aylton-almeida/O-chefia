//Click button cancelar modal
<<<<<<< HEAD
$('#btnCancelarModal').click(()=>{
   $('#nomeInput').val("");
   $('#precoInput').val("");
   $('#ingredientesInput').val("");
=======
$('#btnCancelarModal').click(() => {
  $('#nomeInput').val("");
  $('#precoInput').val("");
  $('#ingredientesInput').val("");
})

//Definir navegador
function FactoryXMLHttpRequest() {

   if(window.XMLHttpRequest) {
      return new XMLHttpRequest();// Opera 8.0+, Firefox, Chrome, Safari
   }
   else if (window.XDomainRequest) {
      return new XDomainRequest(); // Antigo Safari
   }
   else if(window.ActiveXObject) {
      var msxmls = new Array(// Internet Explorer
                    'Msxml2.XMLHTTP',
                    'Microsoft.XMLHTTP',
                    'Msxml3.XMLHTTP',
                    'Msxml2.XMLHTTP.7.0',
                    'Msxml2.XMLHTTP.6.0',
                    'Msxml2.XMLHTTP.5.0',
                    'Msxml2.XMLHTTP.4.0',
                    'Msxml2.XMLHTTP.3.0');
      for (var i = 0; i < msxmls.length; i++) {
         try {
           return new ActiveXObject(msxmls[i]);
         } catch (e) {
         }
      }
   } else throw new Error("Could not instantiate XMLHttpRequest");
}

//Cadastrar Pratos
$('#btnSubmitPrato').click(() => {
  if($("#cadastrarPratoForm")[0].checkValidity()) {
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
  //     xmlhttp.open('get', 'http://127.0.0.1:7200/cadastrarPratos', true);
  //     xmlhttp.send(formData);
  //   }
  // }else{
  //   msgErrorModal("Preencha os campos corretamente");
  // }

  //AJAX
$.ajax({
    url: 'http://127.0.0.1:7200/cadastrarPratos',
    type: "POST",
    data: ({
        nome: $('#nomeInput').val(),
        assunto: $('#precoInput').val(),
        mensagem: $('#ingredientesInput').val()
    }),
    success: function () {
        alert("Email enviado!")
    },
    error: function (event) {
        console.log(event);
    }
})
>>>>>>> cadastrarPrato
})
