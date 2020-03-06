/* 
 * The MIT License
 *
 * Copyright 2018 brunomnsilva@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package javafxgraph_v2.graphview;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurve;

/**
 * A draggable graph edge representation in the form of a cubic curve (b-spline).
 * 
 * @author brunomnsilva
 */
public class GraphEdge extends CubicCurve {

    Set<Arrow> arrows = new HashSet<>();
    
    public GraphEdge() {
        enableDrag();
        enableListeners();
    }

    /**
     * GraphEdge constructor that receives initial values for the cubic curve.
     * @param startX start coordinate x
     * @param startY start coordinate y
     * @param controlX1 cubic curve control point x1
     * @param controlY1 cubic curve control point y1
     * @param controlX2 cubic curve control point x2
     * @param controlY2 cubic curve control point y2
     * @param endX end coordinate x
     * @param endY end coordinate y
     */
    public GraphEdge(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY) {
        super(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);

        enableDrag();
        enableListeners();
    }
 
    /**
     * Adds an arrow to the cubic curve, if it does not already exist.
     * @param a the arrow
     */
    public void addArrow(Arrow a) {
        arrows.add(a);
    }
    
    private void updateArrows() {
        for (Arrow arrow : arrows) {
            arrow.update();
        }
    }
    
    private void enableListeners() {
        this.startXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                updateArrows();
            }
        });
        this.startYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                updateArrows();
            }
        });
        this.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                updateArrows();
            }
        });
        this.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                updateArrows();
            }
        });
    }
    
    private void enableDrag() {
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x1 = getControlX1() - mouseEvent.getX();
                dragDelta.y1 = getControlY1() - mouseEvent.getY();
                dragDelta.x2 = getControlX2() - mouseEvent.getX();
                dragDelta.y2 = getControlY2() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double newX = mouseEvent.getX() + dragDelta.x1;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setControlX1(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y1;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setControlY1(newY);
                }
                
                newX = mouseEvent.getX() + dragDelta.x2;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setControlX2(newX);
                }
                newY = mouseEvent.getY() + dragDelta.y2;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setControlY2(newY);
                }
                
                updateArrows();
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            }
        });
    }

    // records relative x and y co-ordinates.
    private class Delta {

        double x1, y1, x2, y2;
    }
}
