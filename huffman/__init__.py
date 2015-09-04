# -*- coding: utf-8 -*-

__author__ = 'lucastonussi'

import sys
import getopt

from huffman import Huffman

def init(texto):
    huffman = Huffman(texto)
    return huffman.mostrarTabelas()

def writef(nomeArquivo, resultadosCripto):
    try:
        file = open(nomeArquivo, "w")

    except IOError:
        print("There was an error writing to", nomeArquivo)
        sys.exit()

    file.write(str(resultadosCripto))
    file.write("\n")
    file.close()


def qualificaTextoPlano(arquivoTextoPlano):
    plaintext = ''
    for line in arquivoTextoPlano.readlines():
        if len(line) > 1 and line != '\n':
            plaintext += str(line)
    return plaintext


def readf(nomeArquivo):
    try:
        arquivo = open(nomeArquivo, "r")
        plaintext = qualificaTextoPlano(arquivo)
    except IOError:
        print("There was an error reading file")
        sys.exit()
    arquivo.close()
    return plaintext


def main(argv):
    inputfile = ''
    nomeArquivo = ''

    try:
        opts, args = getopt.getopt(argv, "h:i:k:o:", ["ifile=", "ofile="])
    except getopt.GetoptError:
        print('python3 __init__.py -i <inputfile> -o <nomeArquivo>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('Exemplo :: python3 __init__.py -i <inputfile> -o <nomeArquivo>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-k", "--palavaChave"):
            palavaChave = arg
        elif opt in ("-o", "--ofile"):
            nomeArquivo = arg

    print('Texto plano de entrada: ', inputfile)
    print('Codificando...')

    textoPlano = readf(inputfile)
    resultadosCripto = init(textoPlano)

    print('Seus resultdos em: "', nomeArquivo)

    writef(nomeArquivo, resultadosCripto)


if __name__ == "__main__":
    main(sys.argv[1:])
