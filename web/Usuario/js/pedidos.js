$("#btnCancelado").click(() => {
    $("#btnCancelado").css("box-shadow", "0 0 0 .2rem rgba(220, 53,69,.5)");
    $("#btnCancelado").attr('disabled', true);
    $("#btnCancelado").html("Pedido cancelado");
    $('#btnSucesso').hide();
});
