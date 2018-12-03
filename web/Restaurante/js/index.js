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
          p.innerHTML = "Total do pedido: " + element.precoFinal;
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
          divCard.appendChild(divFooter);
          divFooter.appendChild(pHora);
          //Button que fecha pedido
          $('#closeBtn').click(() => {
            $('#closeBtn').closest('.card').remove();
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
