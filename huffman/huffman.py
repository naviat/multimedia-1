# -*- coding: utf-8 -*-

import re, string
from tree import Tree, NodeId

whitespace = ' \t\n\r\v\f'
ascii_lowercase = 'abcdefghijklmnopqrstuvwxyz'
ascii_uppercase = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
ascii_letters = ascii_lowercase + ascii_uppercase
digits = '0123456789'
hexdigits = digits + 'abcdef' + 'ABCDEF'
octdigits = '01234567'
punctuation = """!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"""
printable = digits + ascii_letters + punctuation + whitespace


def capwords(s, sep=None):
    return (sep or ' ').join(x.capitalize() for x in s.split(sep))


class Huffman:

    def __init__(self, text):

        self.text = text
        self.dicionarioUpper = dict([(asc, 0) for asc in ascii_uppercase])
        self.dicionarioLower = dict([(asc, 0) for asc in ascii_lowercase])

        for el in ascii_uppercase:
            self.dicionarioUpper[el] = len(re.findall(el, self.text))

        for el in ascii_lowercase:
            self.dicionarioLower[el] = len(re.findall(el, self.text))

    def mostrarTabelas(self):

        string = 'Tabela de Freqs (uppercase letters):\n'

        for el in ascii_uppercase:
            string += "\t{chave} -> {valor}\n".format(chave=el, valor=self.dicionarioUpper.get(el))

        string += '\nTabela de Freqs (lowercase letters):\n'

        for el in ascii_lowercase:
            string += "\t{chave} -> {valor}\n".format(chave=el, valor=self.dicionarioLower.get(el))

        return string

    def funcao_tamanho(self):
        while self.alfabeto > 1:
            S0 = self.retira_menor_probabilidade(self.alfabeto)
            S1 = self.retira_menor_probabilidade(self.alfabeto)
            self.X = NodeId()  # O comando novo_nó cria um novo nodo vazio.
            self.X.filho0 = S0
            self.X.filho1 = S1
            self.X.probabilidade = self.S0.probabilidade + self.S1.probabilidade
            self.insere(self.alfabeto, self.X)

        self.X = self.retira_menor_simbolo(self.alfabeto)  # nesse ponto só existe um símbolo.

        for folha in self.X:
            self.codigo[folha] = self.percorre_da_raiz_ate_a_folha(folha)

    def retira_menor_probabilidade(self):
        """ A função retira_menor_probabilidade(alfabeto) retorna o nó ou folha de menor probabilidade no nosso e
        remove este símbolo do repositório."""
        pass

    def percorre_da_raiz_ate_a_folha(self):
        """a função percorre_da_raiz_até_a_folha(folha) percorre a árvore binária da raiz até a folha acumulando os
        valores associados com as arestas em seu valor de retorno."""
        pass

    def insere(self):
        """A função insere(alfabeto, X) insere o símbolo X no nosso repositório"""
        pass
