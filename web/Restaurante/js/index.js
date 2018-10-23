//SIstema de desbilitar butoes e seleciona-los
$('#btnRecebido').click(() => {
  $('#btnRecebido').attr('class', 'btn btn-primary');
  $('#btnRecebido').prop("disabled", true);
  $('#btnPreparo').attr('class', 'btn btn-outline-warning');
  $('#btnPreparo').prop('disabled', false);
  $('#btnSucesso').attr('class', 'btn btn-outline-success');
  $('#btnSucesso').prop('disabled', false);
  $('#btnCancelado').attr('class', 'btn btn-outline-danger');
  $('#btnCancelado').prop('disabled', false);
})
$('#btnPreparo').click(() => {
  $('#btnPreparo').attr('class', 'btn btn-warning');
  $('#btnPreparo').prop("disabled", true);
  $('#btnRecebido').attr('class', 'btn btn-outline-primary');
  $('#btnRecebido').prop('disabled', false);
  $('#btnSucesso').attr('class', 'btn btn-outline-success');
  $('#btnSucesso').prop('disabled', false);
  $('#btnCancelado').attr('class', 'btn btn-outline-danger');
  $('#btnCancelado').prop('disabled', false);
})
$('#btnSucesso').click(() => {
  $('#btnSucesso').attr('class', 'btn btn-success');
  $('#btnSucesso').prop("disabled", true);
  $('#btnRecebido').attr('class', 'btn btn-outline-primary');
  $('#btnRecebido').prop('disabled', false);
  $('#btnPreparo').attr('class', 'btn btn-outline-warning');
  $('#btnPreparo').prop('disabled', false);
  $('#btnCancelado').attr('class', 'btn btn-outline-danger');
  $('#btnCancelado').prop('disabled', false);
})
$('#btnCancelado').click(() => {
  $('#btnCancelado').attr('class', 'btn btn-danger');
  $('#btnCancelado').prop("disabled", true);
  $('#btnRecebido').attr('class', 'btn btn-outline-primary');
  $('#btnRecebido').prop('disabled', false);
  $('#btnPreparo').attr('class', 'btn btn-outline-warning');
  $('#btnPreparo').prop('disabled', false);
  $('#btnSucesso').attr('class', 'btn btn-outline-success');
  $('#btnSucesso').prop('disabled', false);
})
//Button que fecha pedido
$('#closeBtn').click(() => {
  if ($('#btnCancelado').prop('disabled') == true || $('#btnSucesso').prop('disabled') == true) {
    $('#closeBtn').closest('.card').remove();
  }else{
    msgError('O pedido deve ser cancelado ou concluido antes de poder ser excluido', 'danger');
    setTimeout(function(){$('#msg').empty()}, 3000);
  }
})
