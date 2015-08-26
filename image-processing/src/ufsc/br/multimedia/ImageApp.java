package ufsc.br.multimedia;

import java.awt.image.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageApp {

  // Leitura da imagem
  public static BufferedImage loadImage(String surl) {
    BufferedImage bimg = null;
    try {
      URL url = new URL(surl);
      bimg = ImageIO.read(url);
      // bimg = ImageIO.read(new File("D:/Temp/mundo.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bimg;
  }

  public void apresentaImagem(JFrame frame, BufferedImage img) {
    frame.setBounds(0, 0, img.getWidth(), img.getHeight());
    JImagePanel panel = new JImagePanel(img, 0, 0);
    frame.add(panel);
    frame.setVisible(true);
  }

  public static BufferedImage criaImagemRGB() {
    BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = img.getRaster();

    for (int h = 0; h < 200; h++) {
      for (int w = 0; w < 200; w++) {
        raster.setSample(w, h, 0, 220);
        raster.setSample(w, h, 1, 219);
        raster.setSample(w, h, 2, 97);
      }
    }
    return img;
  }

  public static BufferedImage criaImagemTonalidadeCinza(BufferedImage img) {
    int largura = img.getWidth();
    int altura = img.getHeight();

    BufferedImage img2 = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);
    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[ww][hh];

    int temp = 0, r = 0, g = 0, b = 0;
    double y = 0;
    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        temp = img.getRGB(i, j);
        r = ((temp & 0x00FF0000) >>> 16);
        g = ((temp & 0x0000FF00) >>> 8);
        b = (temp & 0x000000FF);
        y = (0.3 * r + 0.59 * g + 0.11 * b);
        raster.setSample(i, j, 0, y);
      }
    }

    return img2;
  }

  public static BufferedImage criaImagemBinaria(BufferedImage img) {
    int largura = img.getWidth();
    int altura = img.getHeight();

    BufferedImage img2 = new BufferedImage(largura, altura,
        BufferedImage.TYPE_BYTE_BINARY);
    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[ww][hh];

    int temp = 0, r = 0, g = 0, b = 0, y = 0;
    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        temp = img.getRGB(i, j);
        r = ((temp & 0x00FF0000) >>> 16);
        g = ((temp & 0x0000FF00) >>> 8);
        b = (temp & 0x000000FF);
        y = (int) (0.3 * r + 0.59 * g + 0.11 * b);
        if (y < 127) {
          raster.setSample(i, j, 0, 0);
        } else {
          raster.setSample(i, j, 0, 1);
        }
      }
    }

    return img2;
  }

  //@formatter:off
  /**
   * The AffineTransform class represents a 2D affine transform that performs a
   * linear mapping from 2D coordinates to other 2D coordinates that preserves
   * the “straightness” and “parallelness” of lines. Affine transformations can
   * be constructed using sequences of translations, scales, flips, rotations,
   * and shears.
   * 
   * Such a coordinate transformation can be represented by a 3 row by 3 column
   * matrix with an implied last row of [ 0 0 1 ]. This matrix transforms source
   * coordinates (x,y) into destination coordinates (x’,y’) by considering them
   * to be a column vector and multiplying the coordinate vector by the matrix
   * according to the following process:
   * 
   * [ x']   [  m00  m01  m02  ] [ x ]   [ m00x + m01y + m02 ]
   * [ y'] = [  m10  m11  m12  ] [ y ] = [ m10x + m11y + m12 ]
   * [ 1 ]   [   0    0    1   ] [ 1 ]   [         1         ]
   * 
   * @param Buffered Image[largura][altura]
   * @return Buffered Image[(largura >>> 1) & 0xfffffffe][(altura >>> 1) & 0xfffffffe]
   */
  //@formatter:on

  public static BufferedImage criaImagemRedimencionada(BufferedImage img) {
    int largura = img.getWidth();
    int novaLargura = (largura >>> 1) & 0xfffffffe; /*
                                                     * deslocamento para direita
                                                     * (/2), transforma em nro
                                                     * par
                                                     */
    int altura = img.getHeight();
    int novaAltura = (altura >>> 1) & 0xfffffffe; /*
                                                   * deslocamento para direita
                                                   * (/2), transforma em nro par
                                                   */

    BufferedImage img2 = new BufferedImage(novaLargura, novaAltura,
        BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[hh][ww];

    int x = 0, y = 0;

    for (int i = 0; i < novaLargura; i++) {
      x = i << 1; /* deslocamento para esquerda (x2) */
      if (i > novaLargura) {
        break; /* i > nova largura quebra fluxo */
      }
      for (int j = 0; j < novaAltura; j++) {
        y = j << 1; /* deslocamento para esquerda (x2) */
        if (j > novaAltura) {
          break; /* j > nova altura quebra fluxo */
        }
        int temp = img.getRGB(x, y);
        int r = (int) ((temp & 0x00FF0000) >>> 16);
        int g = (int) ((temp & 0x0000FF00) >>> 8);
        int b = (int) (temp & 0x000000FF);
        raster.setSample(i, j, 0, r);
        raster.setSample(i, j, 1, g);
        raster.setSample(i, j, 2, b);
      }
    }

    return img2;
  }

  public static BufferedImage criaFiltroAzul(BufferedImage img) {
    int largura = img.getWidth();
    int altura = img.getHeight();

    BufferedImage img2 = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[hh][ww];

    int temp = 0, b = 0;
    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        temp = img.getRGB(i, j);
        b = (temp & 0x000000FF);
        raster.setSample(i, j, 0, b); // Componente Vermelho
      }
    }

    return img2;
  }

  public static BufferedImage criaFiltroVerde(BufferedImage img) {
    int largura = img.getWidth();
    int altura = img.getHeight();

    BufferedImage img2 = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[hh][ww];

    int temp = 0, g = 0;
    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        temp = img.getRGB(i, j);
        g = ((temp & 0x0000FF00) >>> 8);
        raster.setSample(i, j, 0, g);
      }
    }

    return img2;
  }

  public static BufferedImage criaFiltroVermelho(BufferedImage img) {
    int largura = img.getWidth();
    int altura = img.getHeight();

    BufferedImage img2 = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

    WritableRaster raster = img2.getRaster();

    // int[][] pixels = new int[hh][ww];

    int temp = 0, r = 0;
    for (int i = 0; i < largura; i++) {
      for (int j = 0; j < altura; j++) {
        temp = img.getRGB(i, j);
        r = ((temp & 0x00FF0000) >>> 16);
        raster.setSample(i, j, 0, r);
      }
    }

    return img2;
  }

  public static BufferedImage criaImagemCinza() {
    BufferedImage img = new BufferedImage(256, 256,
        BufferedImage.TYPE_BYTE_GRAY);
    WritableRaster raster = img.getRaster();
    for (int h = 0; h < 256; h++) {
      for (int w = 0; w < 256; w++) {
        raster.setSample(w, h, 0, h);
      }
    }
    return img;
  }

  public static BufferedImage criaImagemBinaria() {
    BufferedImage img = new BufferedImage(256, 256,
        BufferedImage.TYPE_BYTE_BINARY);
    WritableRaster raster = img.getRaster();
    for (int h = 0; h < 256; h++) {
      for (int w = 0; w < 256; w++) {
        if (((h / 50) + (w / 50)) % 2 == 0) {
          raster.setSample(w, h, 0, 0);
        } else {
          raster.setSample(w, h, 0, 1);
        }
      }
    }
    return img;
  }

  public static void imprimePixeis(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        int rgb = bufferedImage.getRGB(w, h);
        int r = (int) ((rgb & 0x00FF0000) >>> 16);
        int g = (int) ((rgb & 0x0000FF00) >>> 8);
        int b = (int) (rgb & 0x000000FF);
        System.out.print("at (" + w + "," + h + "): ");
        System.out.println(r + "," + g + "," + b);
      }
    }
  }

  public static void main(String[] args) {
    ImageApp ia = new ImageApp();

    // BufferedImage imgJPEG = loadImage("http://www.inf.ufsc.br/~willrich/INE5431/RGB.jpg");

    BufferedImage imgJPEG = loadImage("http://www.schmoker.org/BirdPics/Photos/Warblers/MAWA2.jpg");
    BufferedImage imgTonalidadeCinzenta = criaImagemTonalidadeCinza(imgJPEG);
    BufferedImage imgBinaria = criaImagemBinaria(imgJPEG);
    BufferedImage imgRedimencionada = criaImagemRedimencionada(imgJPEG);
    BufferedImage filtroVermelho = criaFiltroVermelho(imgJPEG);
    BufferedImage filtroVerde = criaFiltroVerde(imgJPEG);
    BufferedImage filtroAzul = criaFiltroAzul(imgJPEG);

    ia.apresentaImagem(new JFrame("ORIGINAL"), imgJPEG);
    ia.apresentaImagem(new JFrame("TONALIDADE_CINZENTA"), imgTonalidadeCinzenta);
    ia.apresentaImagem(new JFrame("BINARIA"), imgBinaria);
    ia.apresentaImagem(new JFrame("REDIMENCIONADA"), imgRedimencionada);
    ia.apresentaImagem(new JFrame("FILTRO VERMELHO"), filtroVermelho);
    ia.apresentaImagem(new JFrame("FILTRO VERDE"), filtroVerde);
    ia.apresentaImagem(new JFrame("FILTRO AZUL"), filtroAzul);

    // imprimePixeis(imgJPEG);
    // imprimePixeis(splitedImageRed);
    // imprimePixeis(splitedImageGreen);
    // imprimePixeis(splitedImageBlue);
    // imprimePixeis(imgTonalidadeCinzenta);
    // imprimePixeis(imgRedimen);
    // imprimePixeis(imgBinaria);
  }
}
