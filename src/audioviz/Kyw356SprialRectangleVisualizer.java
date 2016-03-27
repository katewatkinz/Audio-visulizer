/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;


import com.sun.javafx.geom.Line2D;
import com.sun.prism.Graphics;
import java.awt.Graphics2D;
import static java.lang.Integer.min;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
/**
 *
 * @author Katelyn
 */
public class Kyw356SprialRectangleVisualizer implements Visualizer 
{
    private String name = "Kyw356 Sprial Rectangle Visualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    private String vizPaneInitialStyle = "";
    
    private Double bandHeightPercentage = 1.3;
    private Double minRecRadius = 1.0;  // 10.0
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private Double startHue = 260.0;
    
    
    double translationX = 200;
    double translationY = 200;
    
    private Rectangle[] squares;
    
    public Kyw356SprialRectangleVisualizer() 
    {
        
    }
    
    @Override
    public String getName() 
    {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) 
    {
        end();
        
        vizPaneInitialStyle = vizPane.getStyle();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        squares = new Rectangle[numBands];
        //double  centerX = width/2;
        //double centerY = height/2;
        double angle = 0.0;
        double angleInc = 2 * Math.PI / numBands;
        double radian = 1.0;
        double radianInc = 2*Math.PI/numBands;
        for (int i = 0; i < numBands; i++) 
        {
            Rectangle square = new Rectangle();
            square.setX(Math.cos(angle)*30 * radian/*(.7 + ((numBands-i)/numBands)*.3)*/);
            square.setY(Math.sin(angle)*30 * radian/*(.7 + ((numBands-i)/numBands)*.3 )*/);
            
            radian += radianInc;
            
            angle += angleInc;
            
            square.setTranslateX(translationX);
            square.setTranslateY(translationY);
            square.setHeight(bandWidth / 2 + bandWidth * i);
            square.setWidth((height / 2));
            square.setHeight((bandWidth / 2));
            square.setWidth(minRecRadius);
            square.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(square);
            squares[i] = square;
        }

    }
    
    @Override
    public void end() {
         if (squares != null) {
            for (int i = 0; i < squares.length; i++) 
            {
                vizPane.getChildren().remove(squares[i]);
            }
            squares = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
        } 
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) 
    {
        
        if (squares == null) 
        {
            return;
        }
        
        Integer num = min(squares.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) 
        {
             
            squares[i].setTranslateX(translationX);
            squares[i].setTranslateY(translationY);
            
            squares[i].setHeight((((60.0 + magnitudes[i])/90.0) * halfBandHeight)+1.5);
            squares[i].setWidth((((60.0 + magnitudes[i])/90.0) * halfBandHeight)+1.5 );
            
            squares[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
        }
        
    vizPane.setStyle("-fx-background-color: black" );
}
}
