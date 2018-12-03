//Pegar Pedidos
$('#document').ready(atualizarPedidos);
//Página recarrega faz uma nova requisição a cada 10 segundos
setTimeout(atualizarPedidos, 10000);

function atualizarPedidos() {
  $('#containerCards').empty();
  //AJAX
  $.ajax({
    url: 'http://127.0.0.1:7200/recuperaPedidos',
    type: "POST",
    success: function(response) {
      if (response.status == 1) {
        response.array.forEach((element) => {
          //Gerar DOM objects
          let divCard = document.createElement("div");
          divCard.className = "card bg-dark text-light m-3";
          let divHeader = document.createElement("div");
          divHeader.className = "card-header";
          divHeader.innerHTML = "Pedido";
          let buttonSair = document.createElement('btn');
          buttonSair.className = "close text-light";
          $(buttonSair).attr("data-dismiss", "modal");
          $(buttonSair).attr("aria-label", "close");
          buttonSair.innerHTML = "&times";
          buttonSair.id = "closeBtn";
          let divBody = document.createElement('div');
          divBody.className = "card-body";
          let divRow1 = document.createElement('div');
          divRow1.className = "row";
          let hUsuario = document.createElement('h5');
          hUsuario.className = "card-title text-left col";
          hUsuario.innerHTML = element.usuario.nome;
          let ul = document.createElement('ul');
          ul.className = "my-3";
          ul.style = "list-style-type: none";
          element.itens.forEach((element) => {
            let li = document.createElement('li');
            li.className = "card-text text-left";
            li.innerHTML = element.quantidade + "x " + element.prato.nome;
            ul.appendChild(li);
          })
          let divRow2 = document.createElement('div');
          divRow2.className = "row";
          let p = document.createElement('p');
          p.className = "card-title col-3";
          p.innerHTML = "Atualizar status";
          // let divButtons = document.createElement('div');
          // divButtons.className = "offset-4 col-5";
          // let btnRecebido = document.createElement('btn');
          // btnRecebido.id = "btnRecebido";
          // btnRecebido.className = "btn btn-outline-primary mx-1";
          // btnRecebido.innerHTML = "Recebido";
          // let btnPreparo = document.createElement('btn');
          // btnPreparo.id = "btnPreparo";
          // btnPreparo.className = "btn btn-outline-warning mx-1";
          // btnPreparo.innerHTML = "Em preparo";
          // let btnSucesso = document.createElement('btn');
          // btnSucesso.id = "btnSucesso";
          // btnSucesso.className = "btn btn-outline-success mx-1";
          // btnSucesso.innerHTML = "Pronto";
          // let btnCancelado = document.createElement('btn');
          // btnCancelado.id = "btnCancelado";
          // btnCancelado.className = "btn btn-outline-danger mx-1";
          // btnCancelado.innerHTML = "Cancelado";
          let divFooter = document.createElement('div');
          divFooter.className = "card-footer text-muted";
          let pHora = document.createElement('p');
          pHora.innerHTML = "Pedido feito as: " + element.hora;
          //Colocar objetos na pagina
          document.getElementById("containerCards").appendChild(divCard);
          divCard.appendChild(divHeader);
          divHeader.appendChild(buttonSair);
          divCard.appendChild(divBody);
          divBody.appendChild(divRow1);
          divRow1.appendChild(hUsuario);
          divBody.appendChild(ul);
          divBody.appendChild(divRow2);
          divRow2.appendChild(p);
          divRow2.appendChild(divButtons);
          divButtons.appendChild(btnRecebido);
          divButtons.appendChild(btnPreparo);
          divButtons.appendChild(btnSucesso);
          divButtons.appendChild(btnCancelado);
          divCard.appendChild(divFooter);
          divFooter.appendChild(pHora);
          //SIstema de desbilitar butoes e seleciona-los
          $('#btnRecebido').click(() => {
            console.log(element.precoFinal);
            $.ajax({
              url: 'http://127.0.0.1:7200/alteraStatus',
              type: "POST",
              data: ({
                pedido: element.precoFinal,
                estado: 1
              }),
              success: function(e) {
                $('#btnRecebido').attr('class', 'btn btn-primary mx-1');
                $('#btnRecebido').prop("disabled", true);
                $('#btnPreparo').attr('class', 'btn btn-outline-warning mx-1');
                $('#btnPreparo').prop('disabled', false);
                $('#btnSucesso').attr('class', 'btn btn-outline-success mx-1');
                $('#btnSucesso').prop('disabled', false);
                $('#btnCancelado').attr('class', 'btn btn-outline-danger mx-1');
                $('#btnCancelado').prop('disabled', false);
                console.log(e);
              },
              error: function(e) {
                console.log(e);
              }
            })
          })
          $('#btnPreparo').click(() => {
            $.ajax({
              url: 'http://127.0.0.1:7200/alteraStatus',
              type: "POST",
              data: ({
                pedido: element.precoFinal,
                estado: 2
              }),
              success: function() {
                $('#btnPreparo').attr('class', 'btn btn-warning mx-1');
                $('#btnPreparo').prop("disabled", true);
                $('#btnRecebido').attr('class', 'btn btn-outline-primary mx-1');
                $('#btnRecebido').prop('disabled', false);
                $('#btnSucesso').attr('class', 'btn btn-outline-success mx-1');
                $('#btnSucesso').prop('disabled', false);
                $('#btnCancelado').attr('class', 'btn btn-outline-danger mx-1');
                $('#btnCancelado').prop('disabled', false);
              },
              error: function(e) {
                console.log(e);
              }
            })
          })
          $('#btnSucesso').click(() => {
            $.ajax({
              url: 'http://127.0.0.1:7200/alteraStatus',
              type: "POST",
              data: ({
                pedido: element.precoFinal,
                estado: 3
              }),
              success: function() {
                $('#btnSucesso').attr('class', 'btn btn-success mx-1');
                $('#btnSucesso').prop("disabled", true);
                $('#btnRecebido').attr('class', 'btn btn-outline-primary mx-1');
                $('#btnRecebido').prop('disabled', false);
                $('#btnPreparo').attr('class', 'btn btn-outline-warning mx-1');
                $('#btnPreparo').prop('disabled', false);
                $('#btnCancelado').attr('class', 'btn btn-outline-danger mx-1');
                $('#btnCancelado').prop('disabled', false);
              },
              error: function(e) {
                console.log(e);
              }
            })
          })
          $('#btnCancelado').click(() => {
            $.ajax({
              url: 'http://127.0.0.1:7200/alteraStatus',
              type: "POST",
              data: ({
                pedido: element.precoFinal,
                estado: 4
              }),
              success: function() {
                $('#btnCancelado').attr('class', 'btn btn-danger mx-1');
                $('#btnCancelado').prop("disabled", true);
                $('#btnRecebido').attr('class', 'btn btn-outline-primary mx-1');
                $('#btnRecebido').prop('disabled', false);
                $('#btnPreparo').attr('class', 'btn btn-outline-warning mx-1');
                $('#btnPreparo').prop('disabled', false);
                $('#btnSucesso').attr('class', 'btn btn-outline-success mx-1');
                $('#btnSucesso').prop('disabled', false);
              },
              error: function(e) {
                console.log(e);
              }
            })
          })
          //Button que fecha pedido
          $('#closeBtn').click(() => {
            if ($('#btnCancelado').prop('disabled') == true || $('#btnSucesso').prop('disabled') == true) {
              $('#closeBtn').closest('.card').remove();
            } else {
              msgError('O pedido deve ser cancelado ou concluido antes de poder ser excluido', 'danger');
              setTimeout(function() {
                $('#msg').empty()
              }, 3000);
            }
          })
        })
      } else {
        console.log(response.message);
        console.log(response.type);
        console.log(response.stackTrace);
      }
    },
    error: function(event) {
      msgError(event);
    }
  })
  setTimeout(atualizarPedidos, 10000);
}
