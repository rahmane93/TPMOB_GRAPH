package com.mob.graphbk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawableGraph myDraw;
    ImageView supportView;
    MenuItem reset;
    MenuItem others;


    int downx = 0;
    int downy = 0;

    int upx = 0;
    int upy = 0;

    long touchStartTime = 0;


    static {
        graph = new Graph(9);
    }

    protected static Graph graph;

    List<Integer> closestColorsList = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDraw = new DrawableGraph(graph);
        closestColorsList = myDraw.graph.getNodeColors();
        setContentView(R.layout.activity_main);
        supportView = (ImageView) findViewById(R.id.imageView);
        reset=(MenuItem) findViewById(R.id.reset);
        others=(MenuItem) findViewById(R.id.others);

        supportView.setImageDrawable(myDraw);

        supportView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downx = (int) event.getX();
                        downy = (int) event.getY();
                        touchStartTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        myDraw.setTempArcNull();
                        upx = (int) event.getX();
                        upy = (int) event.getY();
                        if ((Math.abs(upx - downx) < 10) || (Math.abs(upy - downy) < 10)) {
                            int number = 1 + myDraw.graph.getNodes().size();
                            Node node = new Node(upx, upy, number + "");
                            if (graph.addNode(node)) {

                            }
                            supportView.invalidate();
                        }
                        Node node1 = graph.selectedNode(downx, downy);
                        Node node2 = graph.selectedNode(upx, upy);
                        if ((node1 != null) && (node2 != null)) {
                            Arc a = new Arc(node1, node2);
                            graph.addArc(a);
                            supportView.invalidate();
                        } else if (node1 != null) {
                            node1.update(upx, upy);
                            supportView.invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                       break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupCheckable(1, true, true);
        return true;
    }




}
