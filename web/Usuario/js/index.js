//Pegar restaurantes
$('#document').ready(() => {
  //AJAX
  $.ajax({
    url: 'http://127.0.0.1:7200/recuperarRestaurantes',
    type: "POST",
    success: function(response) {
      if (response.status == 1) {
        let restauranteArray = response.array;
        restauranteArray.forEach((element) => {
          //Gerar DOM objects
          let divCard = document.createElement("div");
          divCard.className = "card bg-dark text-light m-3";
          let divHeader = document.createElement("div");
          divHeader.className = "card-header";
          divHeader.innerHTML = element.nome;
          let divBody = document.createElement("div");
          divBody.className = "card-body";
          let divRow = document.createElement("div");
          divRow.className = "row";
          let divUl = document.createElement("div");
          divUl.className = "my-4 col-4";
          let ul = document.createElement("ul");
          ul.style = "list-style-type: none";
          let liEndereco = document.createElement("l1");
          liEndereco.innerHTML = element.endereco.rua + ", " + element.endereco.numero + " - " + element.endereco.bairro;
          let liTelefone = document.createElement("li");
          liTelefone.innerHTML = element.telefone;
          let div = document.createElement("div");
          div.className = "my-4 col-4 align-self-center";
          let div2 = document.createElement("div");
          div2.className = "col align-self-center";
          let a = document.createElement("a");
          let button = document.createElement('btn');
          button.id = "btnCardapio1";
          button.className = "btn btn-orange";
          button.innerHTML = "Cardápio";
          document.getElementById("restauranteContainer").appendChild(divCard);
          divCard.appendChild(divHeader);
          divCard.appendChild(divBody);
          divBody.appendChild(divRow);
          divRow.appendChild(divUl);
          divUl.appendChild(ul);
          ul.appendChild(liEndereco);
          ul.appendChild(liTelefone);
          divUl.appendChild(div);
          div.appendChild(div2);
          div2.appendChild(a);
          a.appendChild(button);
          $(a).click(() => {
            element.cardapio.forEach((element) => {
              let divPrato = document.createElement('div');
              divPrato.className = "prato container-flex";
              let divRowPrato1 = document.createElement('div');
              divRowPrato1.className = "row";
              let h5Prato = document.createElement('h5');
              h5Prato.className = "col";
              h5Prato.innerHTML = element.nome;
              let divRowPrato2 = document.createElement('div');
              divRowPrato2.className = "row";
              let pPreco = document.createElement('p');
              pPreco.className = "col";
              pPreco.innerHTML = "R$" + element.preco;
              let pIngredientes = document.createElement('p');
              pIngredientes.className = "col";
              pIngredientes.innerHTML = element.ingredientes;
              let divRowPrato3 = document.createElement('div');
              divRowPrato3.className = "row";
              let counter = 0;
              let buttonMenos = document.createElement('btn');
              buttonMenos.className = "btn btn-orange btn-block col-5";
              buttonMenos.innerHTML = "-";
              let indicador = document.createElement('p');
              indicador.id = element.nome;
              indicador.className = "text-center indicador col-2"
              indicador.innerHTML = counter;
              let buttonMais = document.createElement('btn');
              buttonMais = document.createElement('btn');
              buttonMais.className = "btn btn-orange btn-block col-5";
              buttonMais.innerHTML = "+";
              $(buttonMenos).click(() => {
                if (counter > 0) {
                  counter--;
                  indicador.innerHTML = counter;
                }
              })
              $(buttonMais).click(() => {
                counter++;
                indicador.innerHTML = counter;
              })
              divPrato.appendChild(divRowPrato1);
              divRowPrato1.appendChild(h5Prato);
              divPrato.appendChild(divRowPrato2);
              divRowPrato2.appendChild(pPreco);
              divRowPrato2.appendChild(pIngredientes);
              divPrato.appendChild(divRowPrato3);
              divRowPrato3.appendChild(buttonMenos);
              divRowPrato3.appendChild(indicador);
              divRowPrato3.appendChild(buttonMais);
              switch (element.tipo) {
                case 1:
                  $('#cardOne').show();
                  document.getElementById('cardBodyOne').appendChild(divPrato);
                  document.getElementById('cardBodyOne').appendChild(document.createElement('hr'));
                  break;
                case 2:
                  $('#cardTwo').show();
                  document.getElementById('cardBodyTwo').appendChild(divPrato);
                  document.getElementById('cardBodyTwo').appendChild(document.createElement('hr'));
                  break;
                case 3:
                  $('#cardThree').show();
                  document.getElementById('cardBodyThree').appendChild(divPrato);
                  document.getElementById('cardBodyThree').appendChild(document.createElement('hr'));
                  break;
                default:
                  document.getElementById('cardBodyOne').appendChild(divPrato);
                  document.getElementById('cardBodyOne').appendChild(document.createElement('hr'));
                  break;
              }
            });
            $('#exampleModalCenter').modal('toggle');
            $('#exampleModalCenter').on('hide.bs.modal', () => {
              document.getElementById('cardBodyOne').innerHTML = "";
              $('#cardOne').hide();
              document.getElementById('cardBodyTwo').innerHTML = "";
              $('#cardTwo').hide();
              document.getElementById('cardBodyThree').innerHTML = "";
              $('#cardThree').hide();
            })
          })
          //Fazer pedido
          $("#btnPedido").click(() => {
            var pedido = [];
            let indicadores = $('.indicador');
            let j = 0;
            for (var i = 0; i < indicadores.length; i++) {
              if (parseInt(indicadores[i].innerHTML) > 0) {
                pedido.push({
                  prato: indicadores[i].id,
                  quantidade: indicadores[i].innerHTML,
                })
              }
            }
            console.log(JSON.parse(sessionStorage.getItem('usuario')).cpf);
            //Ajax
            $.ajax({
              url: 'http://127.0.0.1:7200/fazerPedido',
              type: "POST",
              data: ({
                pedido: JSON.stringify(pedido),
                usuario: JSON.parse(sessionStorage.getItem('usuario')).email,
                restaurante: element.cnpj
              }),
              success: function(response) {
                console.log(response);
              },
              error: function(event) {
                console.log(event);
              }
            })
          })
        })
      } else {
        console.log(response.status);
        console.log(response);
        console.log(response.stackTrace);
        console.log(response.message);
      }
    },
    error: function(event) {
      console.log(event);
    }
  })
})
