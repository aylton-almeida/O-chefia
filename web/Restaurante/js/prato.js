//Click button cancelar modal
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
  if ($("#cadastrarPratoForm")[0].checkValidity()) {
    //AJAX
    var xmlhttp = FactoryXMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
      if (xmlhttp.readyState == 4) {
        console.log("ok");
      }
    }

    if (xmlhttp) {
      xmlhttp.open('get', 'http://127.0.0.1:7200', true);
      xmlhttp.send();
    }
  }
})
