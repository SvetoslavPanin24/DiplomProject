package Biometrics;/*########################################################################

  The contents of this file are subject to the Mozilla Public License
  Version 1.0(the "License");   You  may  NOT  use this file except in
  compliance with the License. You may obtain a copy of the License at
                http:// www.mozilla.org/MPL/
  Software distributed under the License is distributed on an "AS IS"
  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
  the License for the specific language governing rights and limitations
  under the License.

  The Initial Developer of the Original Code is Scott Robert Johnston.

  Copyright(C) 2002-2003. All Rights Reserved.

  Authors: Scott Robert Johnston
           
  This file contains functions that manipulate , extract features and match the 
  fingerprint image

########################################################################*/



import java.awt.image.BufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
//must remove latter
import javax.swing.JOptionPane;

public class CFingerPrintGraphics {
    
    public int FP_IMAGE_WIDTH = 323;
    public int FP_IMAGE_HEIGHT = 352;
 
    
    public CFingerPrintGraphics() 
    {
    }
    
    public CFingerPrintGraphics(int width , int height )
    {
    FP_IMAGE_WIDTH = width;
    FP_IMAGE_HEIGHT = height;
    }
    
    public BufferedImage BinerizeImage(BufferedImage m_image , int max , int min)
  {
    BufferedImage m_ImageBuffer = new BufferedImage(FP_IMAGE_WIDTH,FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
    for (int i = 0; i<= FP_IMAGE_WIDTH - 1;i++)
    {
      for (int j = 0;j<= FP_IMAGE_HEIGHT - 1;j++)
      {
        Color c = new Color(m_image.getRGB(i,j));
        if ((c.getBlue()  <= max) &&(c.getBlue()  >= min)&& (c.getRed()  <= max)&&(c.getRed()  >= min) && (c.getGreen()  <= max)&&(c.getGreen()  >= min))
          m_ImageBuffer.setRGB(i,j,0);
        else
          m_ImageBuffer.setRGB(i,j,Color.white.getRGB()  );
      }
    }
    return m_ImageBuffer;
  }

 public BufferedImage getGreyFingerPrintImage(BufferedImage m_original_image)
   {
       BufferedImage m_result_image = new BufferedImage(FP_IMAGE_WIDTH,FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
     float mask[] = {  -1f/5,1f/5,-1f/5
                        ,1f/5,9f/5,1f/5
                        ,-1f/5,1f/5,-1f/5};

    //     float mask[] = {
    //       5f/100,10f/100,5f/100
    //      ,10f/100,40f/100,10f/100
    //      ,5f/100,10f/100,5f/100
     //                 };



      //float mask[] = {  -1f/4,0,0,0,0,0,0
      //                  ,0,-1f/2,0,0,0,0,0
      //                  ,0,0,-1f/4,0,0,0,0
      //                  ,0,0,0,1,0,0,0
      //                  ,0,0,0,0,0,0,0
      //                  ,0,0,0,0,0,0,0
      //                  ,0,0,0,0,0,0,0

     // };

    //     float mask[] = {  1f/16,0f,-1f/16
    //                      ,2f/16,0f,-2f/16
    //                      ,1f/16,0f,-1f/16
    //
    //  };

       /*
       int d = 530;
           float mask[] = {  0,1f/d/d/d,1f/d,1f/d,1f/d,1f/d,1f/d,1f/d,0
                            ,1f/d,5f/d,5f/d,5f/d,5f/d,5f/d,5f/d,5f/d,1f/d
                            ,1f/d,5f/d,11f/d,11f/d,11f/d,11f/d,11f/d,5f/d,1f/d
                            ,1f/d,5f/d,11f/d,22f/d,22f/d,22f/d,11f/d,5f/d,1f/d
                            ,1f/d,5f/d,11f/d,22f/d,32f/d,22f/d,11f/d,5f/d,1f/d
                            ,1f/d,5f/d,11f/d,22f/d,22f/d,22f/d,11f/d,5f/d,1f/d
                            ,1f/d,5f/d,11f/d,11f/d,11f/d,11f/d,11f/d,5f/d,1f/d
                            ,1f/d,5f/d,5f/d,5f/d,5f/d,5f/d,5f/d,5f/d,1f/d
                            ,0,1f/d,1f/d,1f/d,1f/d,1f/d,1f/d,1f/d,0

           };
  */
       
       Kernel k = new Kernel(3,3, mask);                
       BufferedImageOp con = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP,null);
       con.filter(m_original_image , m_result_image);

       
      /*
         for (int i = 0; i<= FP_IMAGE_WIDTH-1;i++)
         {
            for (int j = 0;j<= FP_IMAGE_HEIGHT-1;j++)
            {
        Color c = new Color(m_result_image.getRGB(i,j));
                if ((c.getBlue()  == 0) && (c.getRed()  == 0) && (c.getGreen()  == 0))
                {
                  m_result_image.setRGB(i,j,Color.blue.getRGB());
                }
                else
                {
                  m_result_image.setRGB(i,j,Color.white.getRGB());
                }

            }//end for j
        }//end for i
  */

    return m_result_image;
   }//getGreyFingerPrintImage
      
}
