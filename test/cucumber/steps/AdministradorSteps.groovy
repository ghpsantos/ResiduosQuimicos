package cucumber.steps

import cucumber.api.PendingException
import pages.CreateLaboratorioPage
import pages.CreateResiduoPage
import pages.IndexLaboratorioPage
import pages.ResumoSistemaPage
import pages.ShowLaboratorioPage
import pages.ShowResiduoPage

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~/^o sistema não possui nenhum laboratório cadastrado$/) { ->
    to IndexLaboratorioPage
    at IndexLaboratorioPage
    assert page.naoPossuiLaboratorio()
}
When(~/^eu vou para a página de resumo do sistema$/) { ->
    to ResumoSistemaPage
    at ResumoSistemaPage
}
Then(~/^eu devo visualizar uma mensagem de erro informando que a UFPE não possui laboratórios$/) { ->
   at ResumoSistemaPage
   assert page.hasErrorMessageLaboratorioNaoCadastrado()
}

Given(~/^eu crei o Laboratório "([^"]*)" no Departamento "([^"]*)" e centro "([^"]*)"$/) { String nomeLab, String nomeDep, String nomeCentro ->
    createLaboratorioAndCheck(nomeLab,nomeDep,nomeCentro)
}
And(~/^eu criei o Laboratório "([^"]*)" no Departamento "([^"]*)" e centro "([^"]*)"$/) { String nomeLab, String nomeDep, String nomeCentro ->
    createLaboratorioAndCheck(nomeLab,nomeDep,nomeCentro)
}

def createLaboratorioAndCheck(String nomeLab, String nomeDep, String nomeCentro){
    to CreateLaboratorioPage
    at CreateLaboratorioPage
    page.createLab(nomeLab, nomeDep, nomeCentro)
    at ShowLaboratorioPage
}

And(~/^eu criei o Residuo "([^"]*)" com peso "([^"]*)" associado ao laboratório "([^"]*)"$/) { String nomeResiduo, String pesoResiduo, String nomeLaboratorio ->
    to CreateResiduoPage
    at CreateResiduoPage
    page.createResiduo(nomeResiduo, pesoResiduo, nomeLaboratorio)
    at ShowResiduoPage
}
Then(~/^eu devo visualizar uma mensagem informando que é necessário fazer uma licitação$/) { ->
   at ResumoSistemaPage
    assert page.hasMessageLicitacaoNecessaria()
}