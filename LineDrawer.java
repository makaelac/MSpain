import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;
/**
 * Let user draw lines on graphics pane with mouse
 *
 * @author (makaela)
 * @version (14/3/24)
 */
public class LineDrawer
{
    // instance variables
    private double startX, startY; // field to remember pressed position
    private Color currentColor = Color.black; // current color start at black
    private boolean circle = true; // variable to change from circle to rectangle
    private double finalX, finalY;
    private boolean line = true;
    private boolean draw = true;
    private String currentText = (" ");

    /**
     * Constructor for objects of class LineDrawer
     */
    public LineDrawer()
    {
        // initialise instance variables
        UI.setLineWidth(10);
        UI.setFontSize(30);
        UI.setMouseListener(this::doMouse);
        UI.addButton("Random Colour", this::randomColour);
        UI.addButton("Choose Colour", this::doChooseColour); // callback to choose color
        UI.addButton("Rect or Circ", this::changeShape); // callback to change from a rect to a circle
        UI.addButton("Line or Fill", this::changeLine); // callback to change from line to fill
        UI.addSlider("Width", 1, 20, 10, this::widthSlider);
        UI.addButton("Colour or Text", this::changeText);
        UI.addTextField("Text" , this::addText);
        UI.addSlider("Font Size", 10, 50, 30, this::fontSlider);
        UI.addButton("Quit", UI::quit);
    }
    
    public void fontSlider(double font) {
        UI.setFontSize(font);
    }
    
    public void addText(String text) {
        currentText = text;
    }
    
    /**
     * callback to change from drawing to adding text
     */
    public void changeText() {
        if (draw == true) {
            draw = false;
        } else if (draw == false) {
            draw = true;
        }
    }
    
    /**
     * callback for slider to change width
     */
    public void widthSlider(double width) {
        UI.setLineWidth(width);
    }
    
    /**
     * callback to change from a line to fill
     */
    public void changeLine() {
        if (line == true) {
            line = false;
        } else if (line == false) {
            line = true;
        }
        System.out.println(line);
    }
    
    /**
     * callback to change from a rect to a circle
     */
    public void changeShape() {
        if (circle == true) {
            circle = false;
        } else if (circle == false) {
            circle = true;
        }
    }
    
    /**
     * callback to choose color
     */
    
    public void doChooseColour() {
        // open a color pane for the user
        this.currentColor = JColorChooser.showDialog(null, "Choose Color", this.currentColor);
        UI.setColor(this.currentColor);
    }
    
    /**
     * Call back random colour changer
     */
    public void randomColour() {
        Color col = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        UI.setColor(col);
    }
    /**
     *  Call back method for mouse
     *  Draws a line
     */
    public void doMouse(String action, double x, double y) {
        if (draw == true) {
            // if user wants to draw instead of type
            if (action.equals("pressed")){
                // store the pressed button x and y coordinates
                this.startX = x;
                this.startY = y;

            } else if (action.equals("released"))  {
                if (line == true) {
                    // draw line
                    UI.drawLine(this.startX, this.startY, x, y);
                } else if (line == false) {
                    // draw rectangle if fill button is clicked
                    this.finalX = x;
                    this.finalY = y;
                    UI.fillRect(this.startX, this.startY, this.finalX-this.startX, this.finalY-this.startY);
                }
            } else if (action.equals("clicked")) {
                if (circle == true) {    // draw a circle on click
                    UI.fillOval(x-50/2, y-50/2, 50, 50);
                } else if (circle == false) {
                    // draw rectangle if rect button clicked
                    UI.fillRect(x-15, y-25, 30, 50);
                }
            }
        } else if (draw == false) {
            // user wants to type text
            if (action.equals("pressed")) {
                // add text where mouse is clicked
                this.startX = x;
                this.startY = y;
                UI.drawString(currentText, x, y);
            }
        }
    }
}
