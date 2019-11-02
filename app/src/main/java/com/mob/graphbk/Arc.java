package com.mob.graphbk;

import android.graphics.Path;

import java.util.Collection;
import java.util.List;

public class Arc {
    private Node debut;
    private Node fin;
    private int color;
    private String label;
    private float [] midPoint;

    public static int ARC_WIDTH= 20;


    public Arc(Node debut, Node fin) {
        this.color = debut.getColor();
        this.debut = debut;
        this.fin = fin;
        this.color = debut.getColor();
        this.label = this.debut.getShortEtiquette()+" --> "+this.fin.getShortEtiquette();
        midPoint = new float[]{0,0};
        setMidPoint();
    }

    public Node getDebut() {
        return debut;
    }

    public Node getFin() {
        return fin;
    }

    public void setFin(Node fin) {
        this.fin = fin;
    }

    /**
     * *
     *
     * @param nwNode Remplace le debut de l'arc par nwNode
     */
    public void setDebut(Node nwNode) {
        debut = nwNode;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float[] getMidPoint() {
        return midPoint;
    }

    public void setMidPoint(float[] midPoint) {
        this.midPoint = midPoint;
    }

    public String toString() {
        return debut.toString() + " -->  " + fin.toString();
    }

    /**
     * Le path de l'arc
     * @return
     */
    public Path getPath(){
        setMidPoint();
        int x1 = this.getDebut().getX();
        int x2 = this.getFin().getX();
        int y1 = this.getDebut().getY();
        int y2 = this.getFin().getY();
        final Path path = new Path();
        path.moveTo(x1, y1);
        path.cubicTo(x1, y1, midPoint[0], midPoint[1], x2, y2);
        return path;
    }

    public void setMidPoint (){
        int x1 = this.getDebut().getX();
        int x2 = this.getFin().getX();
        int y1 = this.getDebut().getY();
        int y2 = this.getFin().getY();
        int midX = x1 + ((x2 - x1) / 2);
        int midY = y1 + ((y2 - y1) / 2);
        float xDiff = midX - x1;
        float yDiff = midY - y1;
        double angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);
        int curveRadius = -360;
        float pointX = (float) (midX + curveRadius * Math.cos(angleRadians));
        float pointY = (float) (midY + curveRadius * Math.sin(angleRadians));
        midPoint[0] = pointX;
        midPoint[1] = pointY;
    }
}
