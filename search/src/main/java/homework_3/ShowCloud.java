package homework_3;

import java.awt.Color;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import com.github.davidmoten.guavamini.Lists;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;

public class ShowCloud {

    public static void Showcloud( List<String> s) {
       
        
        Color[] colors = new Color[10];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(
                    (new Double(Math.random() * 128)).intValue() + 128,
                    (new Double(Math.random() * 128)).intValue() + 128,
                    (new Double(Math.random() * 128)).intValue() + 128);
        }
       
        
       
        WordCloudBuilder.buildWordCouldByWords(400,400,4,20,10,s,new Color(-1),"data.png",colors);
      
    }

}











