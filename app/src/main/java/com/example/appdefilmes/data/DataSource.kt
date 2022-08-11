package com.example.appdefilmes.data


import com.example.appdefilmes.model.apiKey

val itensGenero: List<String> = listOf("Feminino", "Masculino", "Outro", "Não quero informar")

val itensEstadosBrasileiros: List<String> = listOf("Acre (AC)", "Alagoas (AL)","Amapá (AP)","Amazonas (AM)","Bahia (BA)","Ceará (CE)", "Distrito Federal (DF)","Espírito Santo (ES)", "Goiás (GO)",
    "Maranhão (MA)","Mato Grosso (MT)","Mato Grosso do Sul (MS)","Minas Gerais (MG)","Pará (PA)","Paraíba (PB)","Paraná (PR)","Pernambuco (PE)",
    "Piauí (PI)","Rio de Janeiro (RJ)","Rio Grande do Norte (RN)","Rio Grande do Sul (RS)","Rondônia (RO)","Roraima (RR)","Santa Catarina (SC)","São Paulo (SP)",
    "Sergipe (SE)","Tocantins (TO)")

const val baseUrl: String = "https://api.themoviedb.org"
const val chaveAPI: String = apiKey
const val idioma: String = "pt-BR"
const val regiao: String = "BR"
const val qtdePagina: Int = 1

const val baseUrlImagem = "https://image.tmdb.org/t/p/w500"
const val layoutInicio: Int = 1
const val layoutMinhaLista: Int = 2
const val layoutAssistaTambem: Int = 3

const val erroEmailExistente: Int = 1
const val erroCadastro: Int = 2
const val sucessoCadastro: Int = 3

val enderecoEmail: Array<String> = arrayOf("suporte@globoplay.com")
const val assuntoEmail = "Comentário sobre o Globoplay"
const val corpoEmail = "Escreva aqui seu comentário, dúvida ou sugestão"
