# Fluxo de Trabalho do Time

Projeto: Sistema de Controle de Aquisições (GCS - Trabalho 1)

## Integrantes

| Nome completo                  | Usuário no GitHub |
|---------------                 |-------------------|
| Eduardo Dressler da Silva      | dressler33        |
| Arthur Pulz Conzatti           | arthhurpc         |
| Mariana Sager de Macedo        | mnasager         |

## Fluxo adotado: GitHub Flow

Escolhemos o GitHub Flow porque é simples, combina com um time pequeno de 3 pessoas e
mantém a `main` sempre estável. Ele tem poucas regras e todas são fáceis de verificar
no histórico do repositório.

## Regras

### 1. Branch principal
- A `main` contém sempre código que compila e roda.
- Ninguém faz push direto na `main`. A proteção de branch do GitHub exige Pull Request.

### 2. Branches de trabalho
- Toda funcionalidade ou correção nasce de uma branch criada a partir da `main` atualizada.
- Nomes: `feature/nome-curto` para funcionalidades e `fix/nome-curto` para correções.
  Exemplos: `feature/registrar-pedido`, `fix/calculo-estatisticas`.
- Cada branch trata de uma única tarefa, vinculada a uma Issue.
- Branches têm vida curta: devem ser integradas em poucos dias.

### 3. Commits
- Commits pequenos e frequentes, cada um com uma mudança coerente.
- Mensagem no imperativo, curta e descritiva.
  Exemplos: `Adiciona classe Item`, `Corrige validação do limite por departamento`.
- Cada integrante usa seu próprio nome e e-mail do GitHub (`git config user.name` e
  `git config user.email`), para que o histórico identifique a autoria.

### 4. Pull Requests
- Ao terminar a tarefa, o autor faz push da branch e abre um Pull Request para a `main`.
- O PR deve ter título claro, descrição do que foi feito e referência à Issue
  (por exemplo, `Closes #3`).
- Todo PR precisa da aprovação de pelo menos um colega, que não seja o autor.
- O revisor confere se o código compila, se cumpre o que a Issue pede e se não quebra o
  que já funcionava. Comentários de revisão são resolvidos antes do merge.
- Depois de aprovado, o merge é feito pelo GitHub. Depois do merge, a branch é apagada.

### 5. Antes de começar uma nova tarefa
```
git checkout main
git pull
git checkout -b feature/nome-da-tarefa
```

### 6. Conflitos
- Se houver conflito, quem abriu o PR atualiza a própria branch com a `main`
  (`git pull origin main`), resolve os conflitos localmente, testa e faz push.
- Em caso de dúvida sobre qual versão manter, o autor combina com o colega que mexeu no
  mesmo arquivo.

### 7. Issues e divisão de tarefas
- Cada funcionalidade do sistema tem uma Issue, com um responsável.
- Cada integrante assume pelo menos duas features ou correções de bug, com contribuição
  substancial.

### 8. Prazos
- Nenhum push ou alteração no repositório depois da data de entrega.
- Nos dois últimos dias antes da entrega, só entram correções e documentação, nenhuma
  funcionalidade nova.
