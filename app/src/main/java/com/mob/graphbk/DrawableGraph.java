package com.mob.graphbk;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

    public class DrawableGraph extends Drawable {

        private static final int LABEL_TEXT_SIZE = 30;
        private Paint nodePaint;
        private Paint arcPaint;
        private Paint whitePaint;
        private Paint arcEtiqBackPaint;
        private Paint paint;
        private Paint nodeEtiqPaint;
        private Paint arcEtiqPaint;
        private Canvas canvas = new Canvas();

        private Path linePath = new Path();
        Graph graph;
        private Arc tempArc = null; // arc temporaire pour suivre les mouvements

        public Arc getTempArc() {
            return tempArc;
        }

        public void setTempArc(Arc tempArc) {
            this.tempArc = tempArc;
        }

        public void setTempArcNull() {
            this.tempArc = null;
        }

        public DrawableGraph(Graph graph) {
            this.graph = graph;

            graph.addRandomArcs();

            nodePaint = new Paint();
            nodePaint.setColor(Color.DKGRAY);

            arcPaint = new Paint();
            arcPaint.setStyle(Paint.Style.STROKE);
            arcPaint.setStrokeWidth(Arc.ARC_WIDTH);
            arcPaint.setColor(Color.RED);


            whitePaint = new Paint();
            whitePaint.setStyle(Paint.Style.STROKE);
            whitePaint.setStrokeWidth(Arc.ARC_WIDTH+5);


            arcEtiqPaint = new Paint();
            arcEtiqPaint.setColor(Color.WHITE);
            arcEtiqPaint.setTextSize(LABEL_TEXT_SIZE);


            paint = new Paint();
            paint.setColor(Color.RED);

            nodeEtiqPaint = new Paint();
            nodeEtiqPaint.setColor(Color.WHITE);
            nodeEtiqPaint.setTextSize(LABEL_TEXT_SIZE);



            arcEtiqBackPaint = new Paint();
            arcEtiqBackPaint.setColor(Color.GRAY);

        }

        public void drawNode(Node node) {

            nodePaint.setColor(node.getColor());
            RectF r=new RectF(node.getX(),node.getY(),node.getX(),node.getY());
            canvas.drawRect(r,nodePaint);

            int xPos = node.getX() - (int) (nodeEtiqPaint.measureText(node.getEtiquette()) / 2);
            int yPos = (int) (node.getY() - ((nodeEtiqPaint.descent() + nodeEtiqPaint.ascent()) / 2));
            canvas.drawText(node.getEtiquette(), xPos, yPos, nodeEtiqPaint);
        }
        @Override
        public void draw(@NonNull Canvas canvas) {
            this.canvas = canvas;

            drawArcs();

            drawNodes();
        }

        private void drawNodes() {
            int ic = 0;
            for (Node node : graph.getNodes()) {
                drawNode(node);
                ic++;
            }
            if (ic == 0) {
                canvas.drawCircle(0, 0, 500, arcPaint);
            }
        }
        //tous les arcs
        public void drawArcs() {
            for (Arc arcs : graph.getArcs()) {
                drawArc(arcs);
            }
            if (this.tempArc != null) {
                drawArc(tempArc);
            }
        }

        public void drawArc(Arc arc) {

            arcPaint.setColor(arc.getColor());
            canvas.drawPath(arc.getPath(), whitePaint);
            canvas.drawPath(arc.getPath(), arcPaint);

            /**
             * Afficher l'étiquette
             */
            float[] midPoint = {0f, 0f};
            float[] tangent = {0f, 0f};
            PathMeasure pm = new PathMeasure(arc.getPath(), false);
            pm.getPosTan(pm.getLength() * 0.50f, midPoint, tangent);

            int xPos = (int) midPoint[0] - (int) (nodeEtiqPaint.measureText(arc.getLabel()) / 2);
            int yPos = (int) (midPoint[1] - ((nodeEtiqPaint.descent() + nodeEtiqPaint.ascent()) / 2));
            xPos +=50;
            float w = arcEtiqPaint.measureText(arc.getLabel())/2;
            float textSize = arcEtiqPaint.getTextSize();
            arcEtiqPaint.setTextAlign(Paint.Align.CENTER);
            arcEtiqBackPaint.setColor(arc.getDebut().getColor());
            canvas.drawRect(xPos-w, yPos - textSize, xPos + w, yPos, arcEtiqBackPaint);
            canvas.drawText(arc.getLabel(), xPos, yPos, arcEtiqPaint);
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int i) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.UNKNOWN;
        }



}
