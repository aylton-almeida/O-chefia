//Mensagens de alerta pagina
function msgError(msg){
  $('#msg').html('<div class="alert alert-danger fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}
function msgSuccess(msg){
  $('#msg').html('<div class="alert alert-success fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}
//Mensagens de alerta modal
function msgErrorModal(msg){
  $('#msgModal').html('<div class="alert alert-danger fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}
function msgSuccessModal(msg){
  $('#msgModal').html('<div class="alert alert-success fade show">' + msg + '<button type="button" class="close" data-dismiss="alert" aria-label="Close" id="msgCross"><span aria-hidden="true">&times;</span></button></div>');
}

//Definir navegador
function FactoryXMLHttpRequest() {

  if (window.XMLHttpRequest) {
    return new XMLHttpRequest(); // Opera 8.0+, Firefox, Chrome, Safari
  } else if (window.XDomainRequest) {
    return new XDomainRequest(); // Antigo Safari
  } else if (window.ActiveXObject) {
    var msxmls = new Array( // Internet Explorer
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
      } catch (e) {}
    }
  } else throw new Error("Could not instantiate XMLHttpRequest");
}
