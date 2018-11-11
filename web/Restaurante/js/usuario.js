//Cadastrar Usuario
$('#btnSubmitUsuario').click(() => {
  if ($("#cadastrarPratoForm")[0].checkValidity()) {
    if ($('#senhaInput').val() == $('#confSenhaInput').val()) {
      //AJAX
      $.ajax({
        url: 'http://127.0.0.1:7200/cadastrarRestauranteUsuario',
        type: "POST",
        data: ({
          nomeUsuario: $('#nomeInput').val(),
          email: $('#emailInput').val(),
          senha: $('#senhaInput').val(),
          nome: $('#nomeRestauranteInput').val(),
          cnpj: $('#cnpjInput').val(),
          numero: $('#numeroInput').val(),
          rua: $('#ruaInput').val(),
          bairro: $('#bairroInput').val(),
          cep: $('#cepInput').val(),
          cidade: $('#cidadeInput').val(),
          uf: $('#ufInput').val(),
          numMesas: $('#numMesasInput').val(),
          telefone: $('#telefoneInput').val()
        }),
        success: function(response) {
          if (response.status == 1) {
            let restaurante = {
              nome: $('#nomeRestauranteInput').val(),
              cnpj: $('#cnpjInput').val(),
              numero: $('#numeroInput').val(),
              rua: $('#ruaInput').val(),
              bairro: $('#bairroInput').val(),
              cep: $('#cepInput').val(),
              cidade: $('#cidadeInput').val(),
              uf: $('#ufInput').val(),
              numMesas: $('#numMesasInput').val(),
              telefone: $('#telefoneInput').val()
            }
            let usuario = {
              status: true,
              nome: $('#nomeInput').val(),
              email: $('#emailInput').val(),
              senha: $('#senhaInput').val(),
              restaurante: restaurante
            };
            let mensagem = {
              status: true,
              type: "success",
              message: "Cadastro realizado com sucesso"
            };
            localStorage.setItem('usuario', JSON.stringify(usuario));
            sessionStorage.setItem('mensagem', JSON.stringify(mensagem));
            window.location.href = "../index.html";
          }else {
            msgError(response.message);
            console.log(response);
          }
        },
        error: function(event) {
          msgError("Erro ao cadastrar usuário");
          console.log(event);
        }
      })
    } else {
      msgError("As senhas não coincidem");
    }
  } else {
    msgError("Preencha os campos corretamente");
  }
})
