//Click button cancelar modal
$('#btnCancelarModal').click(() => {
  $('#nomeInput').val("");
  $('#precoInput').val("");
  $('#ingredientesInput').val("");
})

//Cadastrar Pratos
$('#btnSubmitPrato').click(() => {
  if ($("#cadastrarPratoForm")[0].checkValidity()) {
    let tipo = document.getElementsByClassName('tipo').value;
    //AJAX
    $.ajax({
      url: 'http://127.0.0.1:7200/cadastrarPratos',
      type: "POST",
      data: ({
        nome: $('#nomeInput').val(),
        preco: $('#precoInput').val(),
        ingredientes: $('#ingredientesInput').val(),
        tipo: $("input[name=tipo]:checked").val()
      }),
      success: function(response) {
        if (response.status == 1) {
          msgSuccess(response.message);
        } else {
          if (response.status == 0) {
            msgError(response.message);
            console.log(response.type);
            console.log(response.stackTrace);
          }
        }
        $('#modalCadastroPratos').modal('hide');
        $('#nomeInput').val("");
        $('#precoInput').val("");
        $('#ingredientesInput').val("");
      },
      error: function(event) {
        msgError(event);
      }
    })
  } else {
    msgErrorModal("Preencha os campos corretamente");
  }
})

//Pegar Pratos
$("document").ready(() => {
  //AJAX
  $.ajax({
    url: 'http://127.0.0.1:7200/recuperarPratos',
    type: "POST",
    success: function(response) {
      if (response.status == 1) {
        response.obj.forEach((element) => {
          //Gerar DOM objects
          let divCard = document.createElement("div");
          divCard.className = "card text-white bg-dark mb-3 mx-2 col-3";
          let divBody = document.createElement("div");
          divBody.className = "card-body";
          let textTitle = document.createElement("h5");
          textTitle.innerHTML = element.nome;
          textTitle.className = "card-title";
          let textIngredientes = document.createElement("p");
          textIngredientes.innerHTML = element.ingredientes;
          textIngredientes.className = "card-text";
          let textPreco = document.createElement("p");
          textPreco.innerHTML = "R$" + element.preco;
          textPreco.className = "card-text";

          //Colocar objetos na pagina
          document.getElementById("cardsPratos").appendChild(divCard);
          divCard.appendChild(divBody);
          divBody.appendChild(textTitle);
          divBody.appendChild(textIngredientes);
          divBody.appendChild(textPreco);
        })
      } else {
        if (response.status == 0) {
          console.log(response.message);
          console.log(response.type);
          console.log(response.stackTrace);
        }
      }
      $('#modalCadastroPratos').modal('hide');
      $('#nomeInput').val("");
      $('#precoInput').val("");
      $('#ingredientesInput').val("");
    },
    error: function(event) {
      msgError(event);
    }
  })
})
